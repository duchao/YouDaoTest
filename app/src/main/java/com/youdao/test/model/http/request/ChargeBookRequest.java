package com.youdao.test.model.http.request;

import java.util.Map;

/**
 * Created by duchao on 2017/11/7.
 */

public class ChargeBookRequest extends HttpRequest<Object> {

    private String mGunId;

    private String mChargeId;

    @Override
    protected Map initParams(Map<String, String> map) {
        map.put("custom_no", "1000031393");
        map.put("timestamp", "1510069771841");
        map.put("key", "84AD64505FFA195730D7261ABB9D79C8");
        map.put("gun_id", "1294");
        map.put("term_id", "1101081071101280");
        return map;
    }

    public ChargeBookRequest(String gunId, String chargeId) {
        mGunId = gunId;
        mChargeId = chargeId;
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
