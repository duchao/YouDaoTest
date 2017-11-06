package com.youdao.test.ui.fragemnt;

import com.youdao.test.R;
import com.youdao.test.base.BaseFragment;
import com.youdao.test.model.bean.UserBean;
import com.youdao.test.model.http.ExampleRequest;
import com.youdao.test.model.http.HttpManager;

/**
 * Created by duchao on 2017/10/22.
 */

public class MineFragment extends BaseFragment {
    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {
    }


    @Override
    protected void onVisible() {
        super.onVisible();
        HttpManager.getInstance().post(new ExampleRequest() {
            @Override
            public void onSucceed(UserBean response) {
                super.onSucceed(response);
            }

            @Override
            public void onFailed(Throwable t) {
                super.onFailed(t);
            }
        });
    }
}
