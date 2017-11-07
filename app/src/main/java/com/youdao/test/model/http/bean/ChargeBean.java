package com.youdao.test.model.http.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by duchao on 2017/11/6.
 */

public class ChargeBean {

    @SerializedName("gun_info")
    private List<GunBean> mGunList;

    @SerializedName("term_type")
    private int mChargeType;

    @SerializedName("term_name")
    private String mChargeName;

    public List<GunBean> getGunList() {
        return mGunList;
    }

    public void setGunList(List<GunBean> gunList) {
        mGunList = gunList;
    }

    public int getChargeType() {
        return mChargeType;
    }

    public void setChargeType(int chargeType) {
        mChargeType = chargeType;
    }

    public String  getChargeName() {
        return mChargeName;
    }

    public void setChargeName(String chargeName) {
        mChargeName = chargeName;
    }
}
