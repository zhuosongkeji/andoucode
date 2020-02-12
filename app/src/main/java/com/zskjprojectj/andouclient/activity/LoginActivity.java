package com.zskjprojectj.andouclient.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.User;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.Constants;
import com.zskjprojectj.andouclient.utils.LogUtil;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PhonenumUtil;
import com.zskjprojectj.andouclient.utils.SharedPreferencesManager;
import com.zskjprojectj.andouclient.utils.StatusBarUtil;

import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

import static com.zskjprojectj.andouclient.http.BaseObserver.REQUEST_CODE_LOGIN;


/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     startTime   : 2019/10/23
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class LoginActivity extends BaseActivity {

    public static final String KEY_FOR_RESULT = "KEY_FOR_RESULT";
    private static boolean started = false;
    private TextView btnNewregistered;
    private Button btn_login;
    private ImageView fingerprint_login, img_weixinlogin;
    private EditText registered_phonenum, et_loginpwd;
    private IWXAPI api;
    Receiver receiver;
    IntentFilter intentFilter;

    @BindView(R.id.rootView)
    TextView mRootView;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        checkRxPermission();
    }

    @Override
    protected void initViews() {
        int barHeight = StatusBarUtil.getStatusBarHeight(mAt);
        if (barHeight > 0) {
            //设置状态栏的高度
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRootView.getLayoutParams();
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mAt) + layoutParams.topMargin;
            mRootView.setLayoutParams(layoutParams);
        }
        StatusBarUtil.setStatusBarDarkTheme(mAt, true);

        receiver = new Receiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("wxdl");
        registerReceiver(receiver, intentFilter);
        btnNewregistered = findViewById(R.id.btn_newregistered);
        btn_login = findViewById(R.id.btn_login);
        fingerprint_login = findViewById(R.id.iv_fingerprint_login);
        img_weixinlogin = findViewById(R.id.img_weixinlogin);
        registered_phonenum = findViewById(R.id.et_loginphonenum);
        et_loginpwd = findViewById(R.id.et_loginpwd);
        //这个跳转最好是在网络请求成功过后去调用，现在没得接口请求暂时写在这里
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                String account = registered_phonenum.getText().toString().trim();
                String password = et_loginpwd.getText().toString();
                if (account.isEmpty()) {
                    Toast.makeText(mAt, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(mAt, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                HttpRxObservable.getObservable(ApiUtils.getApiService().login(account, password))
                        .subscribe(new BaseObserver<User>(mAt) {
                            @Override
                            public void onHandleSuccess(User user) throws IOException {
                                LoginInfoUtil.saveLoginInfo(user.id, user.token);
                                setResult(Activity.RESULT_OK);
                                if (!getIntent().getBooleanExtra(KEY_FOR_RESULT, false)) {
                                    jumpActivity(MainActivity.class);
                                }
                                finish();
                            }
                        });
            }
        });
        btnNewregistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转
                jumpActivity(RegisteredActivity.class);
                // finish();
            }
        });
        findViewById(R.id.forgetPasswordBtn).setOnClickListener(v -> {
            jumpActivity(ForgetActivity.class);
        });
        /**
         * 微信登录
         */
        img_weixinlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (api == null) {
                    api = WXAPIFactory.createWXAPI(LoginActivity.this, Constants.APP_ID, true);

                }
                if (!api.isWXAppInstalled()) {

                    showToast("您手机尚未安装微信，请安装后再登录");

                    return;

                }
                api.registerApp(Constants.APP_ID);
                SendAuth.Req req = new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_sdk_jj_login_state";
                api.sendReq(req);
            }
        });
    }

    //        /**
//         * 指纹登录
//         */
//        fingerprint_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//            }
//        });
    public class Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String code = intent.getStringExtra("code");
            Log.d("ASd_sdx", code);
            // getAccessToken(code);
            Map<String, Object> map = new HashMap<>();
            map.put("code", code);
            HttpRxObservable.getObservable(ApiUtils.getApiService().loginweixin(code))
                    .subscribe(new BaseObserver<User>(mAt) {
                        @Override
                        public void onHandleSuccess(User user) throws IOException {
                            LoginInfoUtil.saveLoginInfo(user.id, user.token);
                            LoginActivity.this.setResult(Activity.RESULT_OK);
                            if (!TextUtils.isEmpty(user.token)) {
                                if (!getIntent().getBooleanExtra(KEY_FOR_RESULT, false)) {
                                    jumpActivity(MainActivity.class);
                                }
                                finish();
                            } else {
                                Intent bindintent = new Intent();
                                bindintent.putExtra("nickname", user.name);
                                bindintent.putExtra("avatorpic", user.avator);
                                bindintent.putExtra("openid", user.openid);
                                bindintent.setClass(mAt, WeixinbingphoneActivity.class);
                                startActivity(bindintent);
                                finish();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            super.onError(e);
                        }
                    });
        }
    }

    @Override
    public void getDataFromServer() {
        //登录请求的逻辑在这里写
    }

    //MVP模式中需要用到，目前没有做P层就不需要
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 手机号码验证正则表达式
     */
    public static boolean isMobileNo(String mobiles) {
        /*
         * 移动号码段:139、138、137、136、135、134、150、151、152、157、158、159、182、183、184、187、188、147
         * 联通号码段:130、131、132、185、186、145、171/176/175
         * 电信号码段:133、153、180、181、189、173、177
         */
        String telRegex = "^((13[0-9])|(14[5|7])|(15([0-3]|[5-9]))|(17([1-3]|[5-9]))|(18[0-9]))\\d{8}$";
        /**
         * (13[0-9])代表13号段 130-139
         * (14[5|7])代表14号段 145、147
         * (15([0-3]|[5-9]))代表15号段 150-153 155-159
         * (17([1-3][5-8]))代表17号段 171-173 175-179 虚拟运营商170屏蔽
         * (18[0-9]))代表18号段 180-189
         * d{8}代表后面可以是0-9的数字，有8位
         */
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(telRegex);
        }
    }

    /**
     * 创建文本框监听事件
     */
//    class  MyEditTextChangeListener implements TextWatcher {
//        /**
//         * 编辑框的内容发生改变之前的回调方法
//         */
//        @Override
//        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//
//        /**
//         * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
//         * 我们可以在这里实时地 通过搜索匹配用户的输入
//         */
//        @Override
//        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
//
//        }
//        /**
//         * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
//         */
//        @Override
//        public void afterTextChanged(Editable editable) {
//            //判断用户输入的格式是否正确
//            if (isMobileNo(registered_phonenum.getText().toString().trim()))
//            {  ToastUtil.showToast("手机号格式不正确");}
//
//        }
//    }
    //动态申请权限
    @SuppressLint("CheckResult")
    private void checkRxPermission() {
        RxPermissions rxPermissions = new RxPermissions((Activity) mAt);
        String[] permissionsArr = new String[]{
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.RECORD_AUDIO,
                android.Manifest.permission.CALL_PHONE
        };
        rxPermissions
                .requestEach(
                        permissionsArr)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            LogUtil.i("testRxPermission CallBack onPermissionsGranted() : " + permission.name +
                                    " request granted , to do something...");
                            //todo somthing

                        } else if (permission.shouldShowRequestPermissionRationale) {
                            LogUtil.e("testRxPermission CallBack onPermissionsDenied() : " + permission.name + "request denied");
                            //ToastUtil.showShort(instance, "拒绝权限，等待下次询问哦");
                            alertDialog(mAt);
                            //todo request permission again
                        } else {
                            LogUtil.e("testRxPermission CallBack onPermissionsDenied() : this " + permission.name + " is denied " +
                                    "and never ask again");
                            // ToastUtil.showShort(instance, "拒绝权限，不再弹出询问框，请前往APP应用设置中打开此权限");
                            //todo nothing
                        }
                    }
                });
    }

    /**
     * 用户拒绝，并且选择不再提示,可以引导用户进入权限设置界面开启权限
     * 弹窗是否显示根据需求选择调用
     *
     * @param context
     */
    private static void alertDialog(final Activity context) {
        if (context != null) {
            new AlertDialog.Builder(context)
                    .setCancelable(false)
                    .setTitle("权限要求")
                    .setMessage("如果没有请求的权限，此应用程序可能无法正常工作。打开app设置界面修改app权限")
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                            context.finish();
                        }
                    })
                    .setNegativeButton("取消", null)
                    .create()
                    .show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
        started = false;
    }

    public static void start(Activity activity) {
        start(activity, REQUEST_CODE_LOGIN);
    }

    public static void start(Activity activity, int requestCode) {
        if (started) return;
        started = true;
        Bundle bundle = new Bundle();
        bundle.putBoolean(KEY_FOR_RESULT, true);
        ActivityUtils.startActivityForResult(bundle, activity, LoginActivity.class, requestCode);
    }
}
