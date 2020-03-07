package com.zskjprojectj.andouclient.base;

import android.app.Application;

import com.bugtags.library.Bugtags;
import com.tencent.bugly.Bugly;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.BuildConfig;
import com.zskjprojectj.andouclient.activity.LoginActivity;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import static com.zskjprojectj.andouclient.utils.ConstantKt.REQUEST_CODE_LOGIN;


public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RequestUtil.onLoginRequest = activity -> {
            LoginActivity.Companion.start(activity, REQUEST_CODE_LOGIN);
            LoginInfoUtil.saveLoginInfo("", "");
        };
        Bugly.init(getApplicationContext(), "f184c5e735", BuildConfig.DEBUG);
        Bugtags.start("bcb6809d0785875785a9599892aceb19", this, Bugtags.BTGInvocationEventBubble);
    }
}
