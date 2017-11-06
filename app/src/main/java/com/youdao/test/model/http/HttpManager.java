package com.youdao.test.model.http;

import io.reactivex.Flowable;

/**
 * Created by duchao on 2017/10/30.
 */

// 在网络外面，又包装了一层

public class HttpManager {

    private RetrofitClient mHttpClient;

    private HttpManager() {
        mHttpClient = RetrofitClient.getInstance();
    }

    private static class SingletonHolder {
        private static HttpManager INSTANCE = new HttpManager();
    }

    public static HttpManager getInstance() {
        return HttpManager.SingletonHolder.INSTANCE;
    }

//    public Flowable rxPost(HttpRequest httpRequest) {
//        return mHttpClient.rxPost(httpRequest);
//    }

    public void post(HttpRequest httpRequest) {
        mHttpClient.post(httpRequest);
    }

//    public Flowable rxGet(HttpRequest httpRequest) {
//        return mHttpClient.rxGet(httpRequest);
//    }
//
//    public void get(HttpRequest httpRequest) {
//        mHttpClient.get(httpRequest);
//    }
}
