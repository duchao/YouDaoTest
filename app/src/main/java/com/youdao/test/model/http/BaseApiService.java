package com.youdao.test.model.http;

import com.youdao.test.model.bean.UserBean;

import java.net.URLEncoder;
import java.util.Map;

import io.reactivex.Flowable;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * Created by duchao on 2017/10/24.
 */

public interface BaseApiService {

    // 这里encode 必须设置为true 否则\会被编码
    @GET("{path}")
    Flowable<ResponseBody> get(
            @Path(value = "path", encoded = true) String url,
            @QueryMap Map<String, String> maps
    );

    @POST("{path}")
    Flowable<ResponseBody> post(
            @Path(value = "path", encoded = true) String url,
            //  @Header("") String authorization,
            @QueryMap Map<String, String> maps);



//    @POST("{url}")
//    Flowable<BaseResponse> json(
//            @Path("url") String url,
//            @Body RequestBody jsonStr);

//    @Multipart
//    @POST("{url}")
//    Flowable<ResponseBody> upLoadFile(
//            @Path("url") String url,
//            @Part("image\"; filename=\"image.jpg") RequestBody requestBody);
//
//    @POST("{url}")
//    Call<ResponseBody> uploadFiles(
//            @Path("url") String url,
//            @Path("headers") Map<String, String> headers,
//            @Part("filename") String description,
//            @PartMap() Map<String, RequestBody> maps);
//
//    @Streaming
//    @GET
//    Flowable<ResponseBody> downloadFile(@Url String fileUrl);
}
