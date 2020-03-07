package com.zskjprojectj.andouclient.base;

import android.app.Application;
import android.widget.ImageView;

import com.bugtags.library.Bugtags;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
import com.tencent.bugly.Bugly;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.BuildConfig;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.LoginActivity;
import com.zskjprojectj.andouclient.http.ApiService;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.SharedPreferencesManager;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.zskjprojectj.andouclient.utils.ConstantKt.REQUEST_CODE_LOGIN;


public class BaseApplication extends Application {
    private ApiService api;
    private static BaseApplication application;
    private static final String TAG = "FloatWindow";

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        SharedPreferencesManager.init(application);
        initHttp();
        Logger.addLogAdapter(new AndroidLogAdapter());
        RequestUtil.onLoginRequest = activity -> {
            LoginActivity.Companion.start(activity, REQUEST_CODE_LOGIN);
            LoginInfoUtil.saveLoginInfo("", "");
        };
        Bugly.init(getApplicationContext(), "f184c5e735", BuildConfig.DEBUG);
        Bugtags.start("bcb6809d0785875785a9599892aceb19", this, Bugtags.BTGInvocationEventBubble);
    }

    public static BaseApplication getInstance() {
        return application;
    }

    void initHttp() {
        /**
         * 初始化悬浮窗
         */
        ImageView imageView = new ImageView(getApplicationContext());
        imageView.setImageResource(R.drawable.icon);

//        FloatWindow
//                .with(getApplicationContext())
//                .setView(imageView)
//                .setWidth(Screen.width,0.2f)
//                .setHeight(Screen.width,0.2f)
//                .setX(Screen.width,0.7f)
//                .setY(Screen.height,0.2f)
//                .setMoveType(MoveType.back)
//                .setMoveStyle(300,null)
//              //  .setFilter(true, OnlinemallActivity.class)
//                .build();
        /*
        初始化网络请求
         */
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(ApiService.class);
    }

    public ApiService API() {
        return api;
    }
//    public static Context getMyAppContext(){
//        return application;
//    }

}
