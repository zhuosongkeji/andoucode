package com.zskjprojectj.andouclient.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.User;
import com.zskjprojectj.andouclient.utils.LogUtil;
import com.zskjprojectj.andouclient.utils.PhonenumUtil;
import com.zskjprojectj.andouclient.utils.SharedPreferencesManager;

import java.io.IOException;

import io.reactivex.functions.Consumer;


/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/23
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class LoginActivity extends BaseActivity {

    private TextView btnNewregistered;
    private Button btn_login;
    private ImageView fingerprint_login;
    private EditText registered_phonenum, et_loginpwd;

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
        btnNewregistered = findViewById(R.id.btn_newregistered);
        btn_login = findViewById(R.id.btn_login);
        fingerprint_login = findViewById(R.id.iv_fingerprint_login);
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
                                SharedPreferencesManager.getInstance().setString(User.KEY_TOKEN, user.token);
                                SharedPreferencesManager.getInstance().setString(User.KEY_UID, user.id);
                                jumpActivity(MainActivity.class);
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
        /**
         * 指纹登录
         */
        fingerprint_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
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
}
