package com.youdao.test.base;

/**
 * Created by duchao on 2017/10/22.
 */

public interface BasePresenter<T extends BaseView> {
    void attachView(T view);
    void detachView();
}
