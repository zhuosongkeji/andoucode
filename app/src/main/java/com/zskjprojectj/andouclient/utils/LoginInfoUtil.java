package com.zskjprojectj.andouclient.utils;

import com.zskjprojectj.andouclient.model.User;

import static com.zskjprojectj.andouclient.model.User.KEY_TOKEN;
import static com.zskjprojectj.andouclient.model.User.KEY_UID;

public class LoginInfoUtil {
    public static String getToken() {
        return SharedPreferencesManager.getInstance().getString(KEY_TOKEN, "");
    }

    public static String getUid() {
        return SharedPreferencesManager.getInstance().getString(KEY_UID, "");
    }

    public static void saveLoginInfo(String uid, String token) {
        SharedPreferencesManager.getInstance().setString(User.KEY_UID, uid);
        SharedPreferencesManager.getInstance().setString(User.KEY_TOKEN, token);
    }

    public static Boolean isLogin() {
        return !LoginInfoUtil.getToken().isEmpty();
    }
}
