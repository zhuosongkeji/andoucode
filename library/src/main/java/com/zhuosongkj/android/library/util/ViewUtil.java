package com.zhuosongkj.android.library.util;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.IdRes;

public class ViewUtil {
    public static void setText(Activity activity, @IdRes int textViewRes, String text) {
        View view = activity.findViewById(textViewRes);
        if (view instanceof TextView) {
            ((TextView) view).setText(text);
        }
    }
}
