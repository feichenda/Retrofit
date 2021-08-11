package com.xinke.retrofit;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.result)
    TextView textView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.gettext)
    public void getText() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestApi.BaseUrl)
                .build();
        RequestApi api = retrofit.create(RequestApi.class);
        api.getAllUserList("1").subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<User>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showToast("start");
                        Log.e("aaa", "aaa");
                    }

                    @Override
                    public void onNext(@NonNull BaseModel<User> userBaseModel) {
                        List<User> users = userBaseModel.getData();
                        textView.setText(users.toString());
                        showToast("next");
                        Log.e("aaa", users.toString());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast("error");
                        Log.e("aaa", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        showToast("complete");
                        Log.e("aaa", "ccc");
                    }
                });
    }

    @OnClick(R.id.posttext)
    public void postText() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestApi.BaseUrl)
                .build();
        RequestApi api = retrofit.create(RequestApi.class);
        api.addUserPosition("MoYi", "1.1", "1.2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showToast("start");
                        Log.e("aaa", "aaa");
                    }

                    @Override
                    public void onNext(@NonNull BaseModel baseModel) {
                        showToast(baseModel.getMsg());
                        Log.e("aaa", baseModel.getMsg());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast("error");
                        Log.e("aaa", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        showToast("complete");
                        Log.e("aaa", "ccc");
                    }
                });
    }

    @OnClick(R.id.postbody)
    public void postBody() {
        OutTask task = new OutTask(new ArrayList<>(),new ArrayList<>(),"2021-7-29 12:00","请及时配送，谢谢！");
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(RequestApi.BaseUrl)
                .build();
        RequestApi api = retrofit.create(RequestApi.class);
        api.createStockOutTask(task)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseModel<OutTask>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        showToast("start");
                        Log.e("aaa", "aaa");
                    }

                    @Override
                    public void onNext(@NonNull BaseModel<OutTask> outTaskBaseModel) {
                        showToast("success");
                        Log.e("aaa", "success");
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        showToast("error");
                        Log.e("aaa", e.toString());
                    }

                    @Override
                    public void onComplete() {
                        showToast("complete");
                        Log.e("aaa", "ccc");
                    }
                });
    }

    private void showToast(CharSequence content) {
        Toast.makeText(this, content, Toast.LENGTH_SHORT).show();
    }
}