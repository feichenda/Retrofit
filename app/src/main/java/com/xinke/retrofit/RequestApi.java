package com.xinke.retrofit;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RequestApi {
    //与服务器对应的域名，一旦更换服务器只需将此域名对应更换即可
    public final static String BaseUrl = "http://192.168.43.116:8080/ssm/";
    public final static String ImageBaseUrl = "http://192.168.43.116:8080/";

    @GET("api/user/{path}")
    Call<List<User>> selectAllUser(@Path("path") String path, @Url String url);


    @POST("api/user/selectUserById")
    Call<User> selectUserById(@Body User user);

    @POST("api/user/{path}")
    Call<List<User>> selectAllUser1(@Path("path") String path);

    @Multipart
    @POST("api/user/upload")
    Call<BaseModel<String>> upload(@Part MultipartBody.Part uploadFile, @Part("name") RequestBody name);

    @Multipart
    @POST("api/user/upLoadMoreFile")
    Call<BaseModel> uploadMoreFile(@Part MultipartBody.Part[] file, @Part("name") RequestBody name);
}
