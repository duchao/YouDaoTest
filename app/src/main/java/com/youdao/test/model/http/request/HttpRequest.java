package com.youdao.test.model.http.request;

import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by duchao on 2017/10/23.
 */

public abstract class HttpRequest<T> extends DisposableSubscriber<T> {

    private String mUrl;

    private Map<String, String> mParams;

    private HttpRequest(Map<String, String> params) {
        mUrl = initUrl();
        mParams = initParams(params);
    }

    public HttpRequest() {
        this(new HashMap<String, String>());
    }

    public String getUrl() {
        return mUrl;
    }

    public Map<String, String> getParams() {
        return mParams;
    }

    protected abstract Map initParams(Map<String, String> map);

    protected abstract String initUrl();

    public abstract Class<?> getBeanClass();

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onNext(T data) {
        onSucceed(data);
    }

    public void onSucceed(T response) {
    }

    @Override
    public void onError(Throwable t) {
        onFailed(t);
    }

    public void onFailed(Throwable t) {

    }

    @Override
    public void onComplete() {

    }

    protected T handleResponse(String response) {
        return null;
    }

//    private T transformData(String dataStr) {
//        T data = null;
//        Gson gson = new Gson();
//        Class<?> beanClass = getBeanClass();
//        if (beanClass != null) {
//            data = (T) gson.fromJson(dataStr, getBeanClass());
//        } else {
//            data = handleResponse(dataStr);
//        }
//        return data;
//    }

    public T transformData(String response) {
        if (response == null) {
            return null;
        }
        T data = null;
        Gson gson = new Gson();
        Class<?> beanClass = getBeanClass();
        if (beanClass != null) {
            data = (T) gson.fromJson(response, getBeanClass());
        } else {
            data = handleResponse(response);
        }
        return data;
    }

}
