package com.youdao.test.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by duchao on 2017/10/22.
 */

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected T mPresenter;

    protected abstract T initPresenter();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        mPresenter = initPresenter();
        if(mPresenter != null) {
            mPresenter.attachView(this);
        }
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void showErrorMessage(String msg) {

    }
}
