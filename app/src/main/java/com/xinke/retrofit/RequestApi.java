package com.xinke.retrofit;

import java.util.List;
import java.util.Map;


import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RequestApi {
    //与服务器对应的域名，一旦更换服务器只需将此域名对应更换即可
    public final static String BaseUrl = "http://mh1210.mynatapp.cc/";//你的域名



    @GET("api/getAllUserList")//接口名
    Observable<BaseModel<User>> getAllUserList(@Query("deptId") String deptId);//你的参数，和接口参数保持一致
}
