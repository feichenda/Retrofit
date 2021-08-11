package com.xinke.retrofit;

import java.util.List;
import java.util.Map;


import io.reactivex.rxjava3.core.Observable;
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
    public final static String BaseUrl = "http://mh1210.mynatapp.cc/";
    public final static String ImageBaseUrl = "http://192.168.43.116:8080/";



    @GET("api/getAllUserList")
    Observable<BaseModel<User>> getAllUserList(@Query("deptId") String deptId);
}
