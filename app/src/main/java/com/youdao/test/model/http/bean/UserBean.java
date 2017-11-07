package com.youdao.test.model.http.bean;

import com.google.gson.annotations.SerializedName;

/**
 * Created by duchao on 2017/10/31.
 */

// {"code":0,"data":{"country":"\u7f8e\u56fd","country_id":"US","area":"\u897f\u90e8","area_id":"US_WT","region":"\u534e\u76db\u987f\u5dde","region_id":"US_WA","city":"","city_id":"-1","county":"","county_id":"-1","isp":"\u7535\u8baf\u76c8\u79d1","isp_id":"3000107","ip":"63.223.108.42"}}
//
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
