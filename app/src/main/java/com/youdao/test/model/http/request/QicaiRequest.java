package com.youdao.test.model.http.request;

import com.youdao.test.model.http.bean.StubBean;

import java.util.Map;

/**
 * Created by duchao on 2017/11/6.
 */

public class QicaiRequest extends HttpRequest<StubBean> {
    @Override
    protected Map initParams(Map<String, String> map) {
        map.put("term_id", "1101081071");
        map.put("key", "146FF5DCC4FA2AE2854A7BF9999F1437");
        map.put("custom_no", "1000031393");
        map.put("timestamp", "1509970276665");
        return map;
    }

    @Override
    protected String initUrl() {
        return "ichargeservice/rest/ChargePileWebc/1.0/F103";
    }

    @Override
    public Class<?> getBeanClass() {
        return StubBean.class;
    }
}
