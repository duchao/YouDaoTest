package com.youdao.test.model.http.bean;

import com.google.gson.annotations.SerializedName;
import com.youdao.test.model.http.bean.ChargeBean;

import java.util.List;

/**
 * Created by duchao on 2017/11/6.
 */

public class StubBean {

    @SerializedName("charge_info")
    private List<ChargeBean> mChargeList;

    public List<ChargeBean> getChargeList() {
        return mChargeList;
    }

    public void setChargeList(List<ChargeBean> chargeList) {
        mChargeList = chargeList;
    }
}
