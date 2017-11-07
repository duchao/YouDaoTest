package com.youdao.test.model.http.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duchao on 2017/11/6.
 */

public class GunBean {

    @SerializedName("gid")
    private String mGunNum;


    @SerializedName("gstatus")
    private int mGunState;


    public int getGunState() {
        return mGunState;
    }

    public void setGunState(int gunState) {
        mGunState = gunState;
    }

    public String getGunNum() {
        return mGunNum;
    }

    public void setGunNum(String gunNum) {
        mGunNum = gunNum;
    }


}
