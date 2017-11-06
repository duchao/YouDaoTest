package com.youdao.test.model.http;

/**
 * Created by duchao on 2017/10/31.
 */

public interface Callback<T> {
    public void onStart();

    public void onSucceed(T response);

    public void onFailed(Throwable e);

    public void onComplete();

}
