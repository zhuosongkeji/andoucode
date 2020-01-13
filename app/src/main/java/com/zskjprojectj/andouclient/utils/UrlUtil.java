package com.zskjprojectj.andouclient.utils;

import android.text.TextUtils;

import com.zskjprojectj.andouclient.base.BaseUrl;

public class UrlUtil {
    public static String getImageUrl(String path) {
        if (TextUtils.isEmpty(path))
        return "";
        String newpath = path;
        if (!path.startsWith("/")) {
            newpath = "/"+path;
        }
        return (BaseUrl.BASE_URL + newpath);
    }
}
