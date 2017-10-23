package com.youdao.test.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.youdao.test.app.App;

/**
 * Created by duchao on 2017/10/23.
 */

public class NetworkUtils {

    public static boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info =  cm.getActiveNetworkInfo();
        return info != null && info.isAvailable();
    }

    public boolean isWifiAvailable() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info =  cm.getActiveNetworkInfo();
        return info != null && info.isAvailable()
                && info.getType() == ConnectivityManager.TYPE_WIFI;
    }

}
