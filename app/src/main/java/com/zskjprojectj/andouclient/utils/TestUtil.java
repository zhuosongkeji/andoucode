package com.zskjprojectj.andouclient.utils;

import static com.zskjprojectj.andouclient.model.User.KEY_TOKEN;
import static com.zskjprojectj.andouclient.model.User.KEY_UID;

public class TestUtil {
    public static String getToken() {
        return SharedPreferencesManager.getInstance().getString(KEY_TOKEN, "");
    }

    public static String getUid() {
        return SharedPreferencesManager.getInstance().getString(KEY_UID, "");
    }
}
