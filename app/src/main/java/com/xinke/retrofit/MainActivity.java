package com.xinke.retrofit;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lzp.android.image.dialog.PhotographDialog;
import com.lzp.android.image.util.PhotoUtil;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    @BindView(R.id.account)
    EditText account;
    EditText password;
    Button login;
    @BindView(R.id.upload)
    Button upload;
    ImageView image;
    String[] paths = {"/storage/emulated/0/DCIM/P01018-142732.jpg", "/storage/emulated/0/DCIM/P01018-142724_1.jpg", "/storage/emulated/0/DCIM/P01018-142719.jpg"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        image = findViewById(R.id.image);


        login.setOnClickListener(this);
        upload.setOnClickListener(this);
    }

    @OnClick(R.id.uploadMore)
    public void uplaodMore() {
        Retrofit retrifit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestApi.BaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        RequestApi requestApi = retrifit.create(RequestApi.class);
        MultipartBody.Part[] uploadFile=new MultipartBody.Part[paths.length];
        for (int i = 0; i < paths.length; i++) {
            File file = new File(paths[i]);
            final RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            uploadFile[i]=part;
        }
        RequestBody body = RequestBody.create(null, "李四");
         Call<BaseModel> call = requestApi.uploadMoreFile(uploadFile, body);
        call.enqueue(new Callback<BaseModel>() {
            @Override
            public void onResponse(Call<BaseModel> call, Response<BaseModel> response) {
                ResponseBody errorBody = response.errorBody();
                BaseModel databody = response.body();
                if (errorBody != null) {
                    try {
                        Log.e("erroeMessage", errorBody.string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    int code = databody.getCode();
                    Log.i("Message", code + "");
                    String datas = databody.getData().toString();
                    String[] imagess = datas.split("&");

                }
            }

            @Override
            public void onFailure(Call<BaseModel> call, Throwable t) {
                Log.i("Message", t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            String accountStr = account.getText().toString();
            String pswStr = password.getText().toString();
            sendRequest(accountStr, pswStr);
        } else if (v.getId() == R.id.upload) {
            PhotographDialog photographDialog = new PhotographDialog(this);
            photographDialog.show();
            photographDialog.setOnBtnClickLister(new PhotographDialog.OnBtnClickLister() {
                @Override
                public void shoot() {

                }

                @Override
                public void select() {
                    PhotoUtil.select(MainActivity.this);
                }

                @Override
                public void cancle() {

                }
            });
        }
    }

    private void sendRequest(String accountStr, String pswStr) {

        Retrofit retrifit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestApi.BaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        RequestApi requestApi = retrifit.create(RequestApi.class);
//        Call<List<User>> call = requestApi.selectAllUser1("selectAllUser");
        User user = new User();
        user.setId(3);
        Call<User> call = requestApi.selectUserById(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User users = response.body();
//                for (User u:users) {
                Log.i("user", users.getName());
//                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.i("user", "异常");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PhotoUtil.PHOTO_REQUEST_GALLERY) {
            Uri imageUri = data.getData();
            String path = getRealPathFromURI(imageUri);
            Log.i("path", path);
            uploadFile(path);
        }
    }

    private void uploadFile(String path) {
        Retrofit retrifit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestApi.BaseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        RequestApi requestApi = retrifit.create(RequestApi.class);
        File file = new File(path);
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("uploadFile", file.getName(), requestBody);
        Call<BaseModel<String>> call = requestApi.upload(part, RequestBody.create(null, "张三"));
        call.enqueue(new Callback<BaseModel<String>>() {
            @Override
            public void onResponse(Call<BaseModel<String>> call, Response<BaseModel<String>> response) {
                BaseModel model = response.body();
                if (model.getCode() == 200) {
                    Toast.makeText(MainActivity.this, model.getMessage(), Toast.LENGTH_SHORT).show();
                    Glide.with(MainActivity.this).load(RequestApi.ImageBaseUrl + model.getData()).into(image);
                    Log.e("image", RequestApi.ImageBaseUrl + model.getData());
                }
            }

            @Override
            public void onFailure(Call<BaseModel<String>> call, Throwable t) {
                Log.e("yoloadresult", t.getMessage());
            }
        });
    }

    //根据图片的Uri获取图片路径
    public String getRealPathFromURI(Uri uri) {
        String res = null;
        String[] proj = {MediaStore.Images.ImageColumns.DATA};
        Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.ImageColumns.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }
}
