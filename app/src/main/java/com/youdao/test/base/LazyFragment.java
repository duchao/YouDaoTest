package com.youdao.test.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;


/**
 * Created by duchao on 2017/10/22.
 */

public abstract class LazyFragment extends Fragment {

    private boolean mIsFirst = true;

    private boolean mIsPrepared = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mIsPrepared = true;
        if (getUserVisibleHint()) {
            onVisible();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (mIsPrepared) {
            if (isVisibleToUser) {
                onVisible();
            } else if(getUserVisibleHint()){
                onInVisible();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            onVisible();
        } else {
            onInVisible();
        }
    }

    protected void onVisible() {
        if (mIsFirst) {
            lazyLoad();
            mIsFirst = false;
        }
    }

    protected void onInVisible() {
    }

    protected void lazyLoad() {
    }

}
