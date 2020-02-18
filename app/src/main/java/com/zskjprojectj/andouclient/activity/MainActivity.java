package com.zskjprojectj.andouclient.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.next.easynavigation.view.EasyNavigationBar;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.HomePageFragment;
import com.zskjprojectj.andouclient.fragment.InfoPageFragment;
import com.zskjprojectj.andouclient.fragment.MePageFragment;
import com.zskjprojectj.andouclient.fragment.MerchantsPageFragment;
import com.zskjprojectj.andouclient.fragment.TieBaFragment;
import com.zskjprojectj.andouclient.utils.LogUtil;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.StatusBarUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

import static com.zskjprojectj.andouclient.http.BaseObserver.REQUEST_CODE_LOGIN;

public class MainActivity extends BaseActivity {
    private EasyNavigationBar navigationBar;
    //定义字体颜色
    private int normalTextColor = Color.parseColor("#646464");
    private int selectTextColor = Color.parseColor("#5ED3AE");
    private String[] tabText = {"首页", "商家", "贴吧", "信息", "我的"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.home_icon_uncheck, R.mipmap.merchants_icon_uncheck, R.mipmap.info_icon, R.mipmap.tieba_icon_uncheck, R.mipmap.me_icon_uncheck};
    //选中时icon
    private int[] selectIcon = {R.mipmap.home_icon_check, R.mipmap.merchants_icon_check, R.mipmap.info_icon, R.mipmap.tieba_icon_check, R.mipmap.me_icon_check};

    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtil.setTranslucentStatus(mActivity);
        initViews();
    }

    private void initViews() {
        navigationBar = findViewById(R.id.navigationBar);
        fragments.add(new HomePageFragment());
        fragments.add(new MerchantsPageFragment());
        fragments.add(new TieBaFragment());
        fragments.add(new InfoPageFragment());
        fragments.add(new MePageFragment());
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .anim(null)
                .addLayoutRule(EasyNavigationBar.RULE_BOTTOM)
                .addLayoutBottom(0)
                .addAlignBottom(true)
                .addAsFragment(true)
                .normalTextColor(normalTextColor)
                .selectTextColor(selectTextColor)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(false)
                .mode(EasyNavigationBar.MODE_ADD)
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        if (position == 4) {
                            if (TextUtils.isEmpty(LoginInfoUtil.getToken())) {
                                LoginActivity.start(mActivity);
                                return true;
                            }
                            MePageFragment mePageFragment = (MePageFragment) fragments.get(4);
                            mePageFragment.refresh();
                        }
//                        switch (position) {
//                            case 0:
//                                ActionBarUtil.setVisible(mActivity, false);
//                                break;
//                            case 1:
//                                ActionBarUtil.setTitle(mActivity, "商家");
//                                break;
//                            case 2:
//                                ActionBarUtil.setVisible(mActivity, false);
//                                break;
//                            case 3:
//                                ActionBarUtil.setTitle(mActivity, "信息");
//                                break;
//                            case 4:
//                                ActionBarUtil.setVisible(mActivity,false);
//                                break;
//
//                        }
                        return false;
                    }
                })
                .build();
        navigationBar.selectTab(0);
        checkRxPermission();
    }

    /**
     * 按键执行操作，连点两次退出程序
     *
     * @param keyCode
     * @param event
     * @return
     */
    private long firstTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long secondTime = System.currentTimeMillis();

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (secondTime - firstTime < 2000) {
                System.exit(0);
            } else {
                Toast.makeText(getApplicationContext(), "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                firstTime = System.currentTimeMillis();
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_LOGIN && resultCode == Activity.RESULT_CANCELED) {
            navigationBar.selectTab(0);
        }
    }

    public EasyNavigationBar getNavigationBar() {
        return navigationBar;
    }

    //动态申请权限
    @SuppressLint("CheckResult")
    private void checkRxPermission() {
        RxPermissions rxPermissions = new RxPermissions((Activity) mActivity);
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
                            alertDialog(mActivity);
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
    protected int getContentView() {
        return R.layout.activity_main;
    }
}
