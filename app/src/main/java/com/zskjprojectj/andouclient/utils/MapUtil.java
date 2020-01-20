package com.zskjprojectj.andouclient.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;

public class MapUtil {
    public static void start(String keyword, Activity activity) {
        Intent intent;
        try {
            intent = Intent.parseUri("intent://map/direction?" +
                    "destination=" + keyword +
                    "&mode=driving&" +
                    "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);

            ComponentName ComponentName = intent.resolveActivity(activity.getPackageManager());
            if (ComponentName == null) {
                intent = Intent.parseUri("androidamap://poi?sourceApplication=安抖本地生活" +
                        "&keywords=" + keyword +
                        "&dev=0", 0);
            }
            ComponentName = intent.resolveActivity(activity.getPackageManager());
            if (ComponentName == null) {
                intent = new Intent("android.intent.action.VIEW", Uri.parse("qqmap://map/search?keyword=" +
                        keyword + "&referer=安抖本地生活"));
            }
            activity.startActivity(intent);
        } catch (Exception e) {
            ToastUtil.showToast("地图启动失败,请检查是否安装地图!");
            e.printStackTrace();
        }
    }
}
