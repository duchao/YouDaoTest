package com.youdao.test.model;

import com.youdao.test.model.http.HttpManager;
import com.youdao.test.model.http.HttpRequest;

/**
 * Created by duchao on 2017/10/23.
 */

public class DataSource {


    private HttpManager mHttpManager;

    private DataSource() {
        mHttpManager = HttpManager.getInstance();
    }

    private static class SingletonHolder {
        private static DataSource INSTANCE = new DataSource();
    }

    public static DataSource getInstance() {
        return SingletonHolder.INSTANCE;
    }


}
