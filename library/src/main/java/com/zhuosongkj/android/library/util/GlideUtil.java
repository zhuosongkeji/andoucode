package com.zhuosongkj.android.library.util;

import android.app.Activity;
import android.widget.ImageView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;

public class GlideUtil {

    public static void load(Activity activity, String url, int placeholderRes, ImageView target, int radius) {
        Glide.with(activity)
                .load(url)
                .apply(RequestOptions
                        .bitmapTransform(new RoundedCorners(radius))
                        .placeholder(placeholderRes))
                .into(target);
    }

    public static void loadCircle(Activity activity, String url, int placeholderRes, ImageView target) {
        load(activity, url, placeholderRes, target, ScreenUtils.getAppScreenHeight());
    }
}
