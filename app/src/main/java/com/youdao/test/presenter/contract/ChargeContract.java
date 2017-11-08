package com.youdao.test.presenter.contract;

import com.youdao.test.base.BasePresenter;
import com.youdao.test.base.BaseView;

/**
 * Created by duchao on 2017/11/7.
 */

public interface ChargeContract {
    public interface View extends BaseView {

        public void updateChargeInfo(String info);

    }

    public interface Presenter extends BasePresenter<View> {
        public void updateConfigInfo(int config);
        public void startRefresh();
        public void stopRefresh();
        public void udpateBookMode(int bookMode);
    }
}
