package com.youdao.test.presenter.contract;

import com.youdao.test.base.BasePresenter;
import com.youdao.test.base.BaseView;

/**
 * Created by duchao on 2017/10/22.
 */

public interface ExampleContract {
    public interface View extends BaseView {

    }

    public interface Presenter extends BasePresenter<View> {

    }

}
