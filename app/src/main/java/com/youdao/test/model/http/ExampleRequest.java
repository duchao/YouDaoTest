package com.youdao.test.model.http;

import com.youdao.test.model.bean.UserBean;

import java.util.Map;


/**
 * Created by duchao on 2017/10/31.
 */
public class ExampleRequest extends HttpRequest<UserBean> {

    // 将要初始化的参数组装在这里
    @Override
    protected Map initParams(Map<String, String> map) {
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
