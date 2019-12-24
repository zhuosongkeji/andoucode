package com.zskjprojectj.andouclient.utils;

import com.zskjprojectj.andouclient.base.BaseUrl;

public class UrlUtil {
    public static String getImageUrl(String path) {
        return BaseUrl.BASE_URL + path;
    }
}
