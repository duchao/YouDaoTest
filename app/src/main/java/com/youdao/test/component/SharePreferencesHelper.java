package com.youdao.test.component;

import android.content.Context;
import android.content.SharedPreferences;

import com.youdao.test.app.App;

/**
 * Created by duchao on 2017/10/23.
 */

public class SharePreferencesHelper {
    private SharedPreferences mSp;
    public static String PREFERENCE_NAME = "youdaoTest";

    private static class SingletonHolder {
        private static final SharePreferencesHelper INSTANCE = new SharePreferencesHelper();
    }

    public static SharePreferencesHelper getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public SharePreferencesHelper() {
        mSp = App.getInstance().getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
    }

    public String getString(String key, String defValue) {
        return mSp.getString(key, defValue);
    }

    public SharePreferencesHelper set(String key, String value) {
        mSp.edit().putString(key, value).commit();
        return this;
    }

    public boolean getBoolean(String key, boolean defValue) {
        return mSp.getBoolean(key, defValue);
    }

    public SharePreferencesHelper set(String key, boolean value) {
        mSp.edit().putBoolean(key, value).commit();
        return this;
    }

    public int getInt(String key, int defValue) {
        return mSp.getInt(key, defValue);
    }

    public SharePreferencesHelper set(String key, int value) {
        mSp.edit().putInt(key, value).commit();
        return this;
    }

    public long getLong(String key, long defValue) {
        return mSp.getLong(key, defValue);
    }

    public void set(String key, long value) {
        mSp.edit().putLong(key, value).commit();
    }
}
