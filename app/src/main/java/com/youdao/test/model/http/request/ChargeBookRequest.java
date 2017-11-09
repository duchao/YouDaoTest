package com.youdao.test.model.http.request;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by duchao on 2017/11/7.
 */

public class ChargeBookRequest extends HttpRequest<Object> {

    public ChargeBookRequest(String gunId, String chargeId) {
        mParams = initParams(gunId, chargeId);
    }

    @Override
    protected Map initParams(String... params) {
        Map<String, String> map = new HashMap<>();
        map.put("custom_no", "1000031393");
        map.put("timestamp", "1509970276665");
        map.put("key", "146FF5DCC4FA2AE2854A7BF9999F1437");
        map.put("gun_id", params[0]);
        map.put("term_id", params[1]);
        return map;
    }

    @Override
    protected String initUrl() {
        return "ichargeservice/rest/ChargePileWebc/1.0/F105";
    }

    @Override
    public Class<?> getBeanClass() {
        return Object.class;
    }
}
