package com.youdao.test.model.http.request;

import com.youdao.test.model.http.bean.ChargeInfoBean;

import java.util.Map;

/**
 * Created by duchao on 2017/11/6.
 */

public class ChargeInfoRequest extends HttpRequest<ChargeInfoBean> {

    // 用抓包工具抓的包
    @Override
    protected Map initParams(Map<String, String> map) {
        map.put("term_id", "1101081071");
        map.put("key", "146FF5DCC4FA2AE2854A7BF9999F1437");
        map.put("custom_no", "1000031393");
        map.put("timestamp", "1509970276665");
        return map;
    }

    // 请求的URL
    @Override
    protected String initUrl() {
        return "ichargeservice/rest/ChargePileWebc/1.0/F103";
    }

    // 这个地方必须返回对应的Class
    @Override
    public Class<?> getBeanClass() {
        return ChargeInfoBean.class;
    }
}
