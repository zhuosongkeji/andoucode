package com.zskjprojectj.andouclient.utils;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class BitmapUtil {

    public static void recycle(Bitmap bitmap) {
        if (bitmap != null && !bitmap.isRecycled()) {
            bitmap.recycle();
        }
    }

    public static void recycle(ImageView imageView) {
        if (imageView == null) return;
        Drawable drawable = imageView.getDrawable();
        imageView.setImageBitmap(null);
        if (drawable instanceof BitmapDrawable) {
            recycle(((BitmapDrawable) drawable).getBitmap());
        }
    }
}
