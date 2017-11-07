package com.youdao.test.ui.fragemnt;

import android.widget.Button;

import com.youdao.test.R;
import com.youdao.test.base.BaseFragment;
import com.youdao.test.ui.activity.ChargeActivity;
import com.youdao.test.ui.activity.MainActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by duchao on 2017/10/22.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.btn_netease_charge)
    Button mNeteaseCharge;

    @Override
    protected int getLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initEventAndData() {
    }

    @OnClick(R.id.btn_netease_charge)
    protected void jumpToCharge() {
        intent2Activity(ChargeActivity.class);
    }
}
