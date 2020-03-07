package com.zskjprojectj.andouclient.utils;

import com.blankj.utilcode.util.SPUtils;
import com.zskjprojectj.andouclient.model.User;

import static com.zskjprojectj.andouclient.model.User.KEY_TOKEN;
import static com.zskjprojectj.andouclient.model.User.KEY_UID;

public class LoginInfoUtil {
    public static String getToken() {
        return SPUtils.getInstance().getString(KEY_TOKEN, "");
    }

    public static String getUid() {
        return SPUtils.getInstance().getString(KEY_UID, "");
    }

    public static void saveLoginInfo(String uid, String token) {
        SPUtils.getInstance().put(User.KEY_UID, uid);
        SPUtils.getInstance().put(User.KEY_TOKEN, token);
    }

    public static Boolean isLogin() {
        return !LoginInfoUtil.getToken().isEmpty();
    }
}
