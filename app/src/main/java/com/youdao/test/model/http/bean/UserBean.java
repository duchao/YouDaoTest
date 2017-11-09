package com.youdao.test.model.http.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duchao on 2017/10/31.
 */

public class UserBean {

    @SerializedName("country")
    String mResult;

    public String getResult() {
        return mResult;
    }

    public void setResult(String result) {
        mResult = result;
    }

}
