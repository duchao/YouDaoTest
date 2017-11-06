package com.youdao.test.model.http;

import android.util.Log;

import com.youdao.test.BuildConfig;
import com.youdao.test.model.bean.UserBean;
import com.youdao.test.utils.LogUtils;
import com.youdao.test.utils.RxUtils;

import org.reactivestreams.Subscription;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.*;
import retrofit2.Callback;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by duchao on 2017/10/24.
 */

public class RetrofitClient {

    private static final String BASE_URL = "http://note.youdao.com";

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
                .baseUrl(BASE_URL)
                .client(sOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        mApiService = apiRetrofit.create(BaseApiService.class);
    }

    private void initOkHttp() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
//        if (BuildConfig.DEBUG) {
//            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
//            builder.addInterceptor(loggingInterceptor);
//        }

        // 设置超时
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);

        // 错误重连
        builder.retryOnConnectionFailure(true);
        sOkHttpClient = builder.build();
    }


//    public <T>Flowable rxPost(HttpRequest<T> httpRequest) {
//        return mApiService.post(httpRequest.getUrl(), httpRequest.getParams())
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult());
//    }

    public void post(HttpRequest httpRequest) {
        mApiService.post(httpRequest.getUrl(), httpRequest.getParams())
//                .compose(RxUtils.transformer())
                .compose(RxUtils.rxScheduler())
                .compose(RxUtils.handleResult())
                .subscribe(httpRequest);
    }

//    public <T> Flowable rxGet(HttpRequest<T> httpRequest) {
//        return mApiService.get(httpRequest.getUrl(), httpRequest.getParams())
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult());
//    }
//
//    public void get(HttpRequest<ResponseBody> httpRequest) {
//        mApiService.get(httpRequest.getUrl(), httpRequest.getParams())
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult())
//                .subscribe(httpRequest);
//    }

    //    public Flowable post(String url, Map<String, String> parameters) {
//        return mApiService.post(url, parameters)
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult());
//    }

//    public Flowable get(String url, Map<String, String> parameters) {
//        return mApiService.get(url, parameters)
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult());
//    }

//    public Flowable json(String url, RequestBody jsonStr) {
//        return mApiService.json(url, jsonStr)
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult());
//    }

//    public Flowable upload(String url, RequestBody requestBody) {
//        return mApiService.upLoadFile(url, requestBody)
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult());
//    }
//
//    public void download(String url, final CallBack callBack) {
//        mApiService.downloadFile(url)
//                .compose(RxUtils.rxScheduler())
//                .compose(RxUtils.handleResult());
//                .subscribe(new DownSubscriber<ResponseBody>(callBack));
//    }


}
