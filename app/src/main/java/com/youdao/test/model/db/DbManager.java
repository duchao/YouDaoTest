package com.youdao.test.model.db;

/**
 * Created by duchao on 2017/10/23.
 */

public class DbManager {

    private DbManager() {



    }
    private static class SingletonHolder {
        private static DbManager INSTANCE = new DbManager();
    }

    private DbManager getInstance() {
        return SingletonHolder.INSTANCE;
    }




}
