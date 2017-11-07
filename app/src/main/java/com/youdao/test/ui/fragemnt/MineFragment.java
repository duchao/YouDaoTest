package com.youdao.test.ui.fragemnt;

import android.text.TextUtils;
import android.widget.TextView;

import com.youdao.test.R;
import com.youdao.test.base.BaseFragment;
import com.youdao.test.model.http.bean.ChargeBean;
import com.youdao.test.model.http.bean.GunBean;
import com.youdao.test.model.http.bean.StubBean;
import com.youdao.test.model.http.HttpManager;
import com.youdao.test.model.http.request.QicaiRequest;

import java.util.List;

import butterknife.BindView;

/**
 * Created by duchao on 2017/10/22.
 */

public class MineFragment extends BaseFragment {

    @BindView(R.id.tv_stub)
    TextView mStubTextView;

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
        HttpManager.getInstance().json(new QicaiRequest() {
            @Override
            public void onSucceed(StubBean response) {
                if (response == null) {
                    return;
                }
                List<ChargeBean> list = response.getChargeList();

                StringBuilder sb = new StringBuilder();
                if (list != null) {
                    for(ChargeBean bean : list) {
                        GunBean gunBean = bean.getGunList().get(0);
                        if (gunBean.getGunState() == 2) {
                            sb.append("、" + bean.getChargeName());
                        }
                    }
                }
                StringBuilder newSb = new StringBuilder();
                if(TextUtils.isEmpty(sb.toString())) {
                    newSb.append("当前没有充电桩空闲");
                } else {
                    newSb.append("当前");
                    newSb.append(sb.toString());
                    newSb.append("空闲");
                }
                mStubTextView.setText(newSb.toString());

            }

            @Override
            public void onFailed(Throwable t) {
                super.onFailed(t);
            }
        });
    }
}
