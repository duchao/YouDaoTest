package com.youdao.test.model.http.request;

import com.youdao.test.model.http.bean.UserBean;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by duchao on 2017/10/31.
 */
public class ExampleRequest extends HttpRequest<UserBean> {

    public ExampleRequest() {
        mParams = initParams();
    }

    // 将要初始化的参数组装在这里
    @Override
    protected Map initParams(String... params) {
        Map<String, String> map = new HashMap<>();
        map.put("userId", "1234567");
        return map;
    }

    // 将要访问的路径填写在这里
    @Override
    protected String initUrl() {
        String s = "service/get";
        return String.valueOf(s);
    }

    @Override
    public Class<?> getBeanClass() {
        return UserBean.class;
    }

}
