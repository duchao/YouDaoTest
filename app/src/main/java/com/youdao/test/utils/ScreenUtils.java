package com.youdao.test.utils;

import android.app.Application;
import android.content.Context;
import android.view.WindowManager;

import com.youdao.test.app.App;

/**
 * Created by duchao on 2017/10/23.
 */

public class ScreenUtils {
    private static Application mApp = App.getInstance();

    public static int dip2px(Context paramContext, float paramFloat) {
        return (int) (paramFloat * paramContext.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int px2dip(Context paramContext, float paramFloat) {
        return (int) (paramFloat / paramContext.getResources().getDisplayMetrics().density + 0.5F);
    }

    public static int getScreenWidth() {
        WindowManager wm = (WindowManager) mApp.getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 640;
        } else {
            return wm.getDefaultDisplay().getWidth();
        }
    }

    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) mApp
                .getSystemService(Context.WINDOW_SERVICE);
        if (wm == null) {
            return 1240;
        } else {
            return wm.getDefaultDisplay().getHeight();
        }
    }
}
