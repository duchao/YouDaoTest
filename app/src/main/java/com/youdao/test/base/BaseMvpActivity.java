package com.youdao.test.base;

/**
 * Created by duchao on 2017/10/22.
 */

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {

    protected T mPresenter;

    protected abstract T initPresenter();

    @Override
    protected void onViewCreated() {
        super.onViewCreated();
        mPresenter = initPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
    }

    @Override
    public void showErrorMessage(String msg) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
}
