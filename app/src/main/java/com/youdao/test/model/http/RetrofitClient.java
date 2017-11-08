package com.youdao.test.model.http;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.youdao.test.BuildConfig;
import com.youdao.test.model.http.request.HttpRequest;
import com.youdao.test.utils.RxUtils;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableTransformer;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.*;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by duchao on 2017/10/24.
 */

public class RetrofitClient {

    private static final String RESPONSE_NAME_CODE = "code";

    private static final String RESPONSE_NAME_MSG = "msg";

    private static final String RESPONSE_NAME_DATA = "data";

    private static final int RESPONSE_CODE_OK = 0;

    private static OkHttpClient sOkHttpClient = null;

    private static BaseApiService mApiService = null;

    private RetrofitClient() {
        initOkHttp();
        initApiService();
    }

    private static class SingletonHolder {
        private static RetrofitClient INSTANCE = new RetrofitClient();
    }

    public static RetrofitClient getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private void initApiService() {
        Retrofit apiRetrofit = new Retrofit.Builder()
                .baseUrl(BaseApiService.BASE_URL)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiService = apiRetrofit.create(BaseApiService.class);
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(loggingInterceptor);
        }
        // 设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
//
//        Map<String, String> headerMap = new HashMap<>();
//        headerMap.put("Content-Type", "application/json");
//        headerMap.put("Accept", "application/json");
//        headerMap.put("Accept-Encoding", "gzip");
//        headerMap.put("Accept-Language", "zh-CN");
//        headerMap.put("Connection", "keep-alive");
//
//        BaseInterceptor header = new BaseInterceptor(headerMap);
//        builder.addInterceptor(header);

        // 错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }

    public void post(HttpRequest httpRequest) {
        mApiService.post(httpRequest.getUrl(), httpRequest.getParams())
                .compose(RxUtils.rxScheduler())
                .compose(handleResult(httpRequest))
                .subscribe(httpRequest);
    }

    public void get(HttpRequest httpRequest) {
        mApiService.get(httpRequest.getUrl(), httpRequest.getParams())
                .compose(RxUtils.rxScheduler())
                .compose(handleResult(httpRequest))
                .subscribe(httpRequest);
    }

    public Flowable rxGet(HttpRequest httpRequest) {
        return   mApiService.get(httpRequest.getUrl(), httpRequest.getParams())
                .compose(RxUtils.rxScheduler())
                .compose(handleResult(httpRequest));
    }

    public Flowable rxPost(HttpRequest httpRequest) {
        return   mApiService.get(httpRequest.getUrl(), httpRequest.getParams())
                .compose(RxUtils.rxScheduler())
                .compose(handleResult(httpRequest));
    }

    public void json(HttpRequest httpRequest) {
        RequestBody jsonbody = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(httpRequest.getParams()));
        mApiService.json(httpRequest.getUrl(), jsonbody)
                .compose(RxUtils.rxScheduler())
                .compose(handleResult(httpRequest))
                .subscribe(httpRequest);
    }




    public <T> FlowableTransformer<ResponseBody, T> handleResult(final HttpRequest httpRequest) {
        return new FlowableTransformer<ResponseBody, T>() {
            @Override
            public Flowable<T> apply(@NonNull Flowable<ResponseBody> upstream) {
                return upstream.map(new Function<ResponseBody, T>() {
                    @Override
                    public T apply(@NonNull ResponseBody responseBody) throws Exception {
                        return handleResponseBody(responseBody,httpRequest);
                    }
                }).onErrorResumeNext(new Function<Throwable, Flowable<T>>() {
                    @Override
                    public Flowable<T> apply(@NonNull Throwable throwable) throws Exception {
                        return Flowable.error(ExceptionHandle.handleException(throwable));
                    }
                });
            }
        };
    }

    // 由于泛型擦除，retrofit只能统一返回responsebody的数据，在这里做处理，然后返回对应的数据类型。
    private static <T> T handleResponseBody(ResponseBody responseBody, HttpRequest httpRequest) throws Exception {
        String dataStr = "";
        String responseStr = responseBody.string();
        int code = -1;
        String msg = "";
        JSONObject jsonObject = new JSONObject(responseStr.trim());
        code = jsonObject.optInt(RESPONSE_NAME_CODE);
        msg = jsonObject.optString(RESPONSE_NAME_MSG);
        //这个地方用来判断code
        if (code != RESPONSE_CODE_OK) {
            throw new RuntimeException(String.valueOf(code) + " " + msg);
        }
        dataStr = jsonObject.optString(RESPONSE_NAME_DATA);
        T data = (T) httpRequest.transformData(dataStr);

        // 避免空指针异常
        if (data == null) {
            data = (T) new Object();
        }
        return data;
    }

    //    public FlowableTransformer<ResponseBody, String> handleResult() {
//        return new FlowableTransformer<ResponseBody, String>() {
//            @Override
//            public Flowable<String> apply(@NonNull Flowable<ResponseBody> upstream) {
//                return upstream.map(new Function<ResponseBody, String>() {
//                    @Override
//                    public String apply(@NonNull ResponseBody responseBody) throws Exception {
//                        return handleResponseBody(responseBody);
//                    }
//                }).onErrorResumeNext(new Function<Throwable, Flowable<String>>() {
//                    @Override
//                    public Flowable<String> apply(@NonNull Throwable throwable) throws Exception {
//                        return Flowable.error(ExceptionHandle.handleException(throwable));
//                    }
//                });
//            }
//        };
//    }
//
//
//    // 由于泛型擦除，retrofit只能统一返回responsebody的数据，在这里做处理，然后返回字符串。
//    // 另外确保是规范的数据，如果不是规范数据，会直接返回整个字符串
//    private static String handleResponseBody(ResponseBody responseBody) throws Exception {
//        String data = "";
//        String responseStr = responseBody.string();
//        int code = -1;
//        String msg = "";
//        JSONObject jsonObject = new JSONObject(responseStr.trim());
//        code = jsonObject.optInt(RESPONSE_NAME_CODE);
//        msg = jsonObject.optString(RESPONSE_NAME_MSG);
//        if (code != RESPONSE_CODE_OK) {
//            throw new RuntimeException(String.valueOf(code) + " " + msg);
//        }
//        data = jsonObject.optString(RESPONSE_NAME_DATA);
//        if (TextUtils.isEmpty(data)) {
//            data = responseStr;
//        }
//        return data;
//    }

}
