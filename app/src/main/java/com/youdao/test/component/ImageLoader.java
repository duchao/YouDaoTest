package com.youdao.test.component;

import android.app.Activity;
import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

/**
 * Created by duchao on 2017/10/23.
 */

public class ImageLoader {
    public static void load(Activity activity, String url, ImageView iv) {
        Glide.with(activity).load(url).into(iv);
    }

    public static void load(Context context, String url, ImageView iv) {
        Glide.with(context).load(url).into(iv);
    }
}
