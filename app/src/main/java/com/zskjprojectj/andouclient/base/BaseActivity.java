package com.zskjprojectj.andouclient.base;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.gyf.immersionbar.ImmersionBar;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.listener.LifeCycleListener;
import com.zskjprojectj.andouclient.view.TopView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import io.reactivex.annotations.Nullable;


public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {
    protected TopView topView;
    protected Activity mAt;
    /**
     * 回调函数
     */
    public LifeCycleListener mListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAt = this;
        if (mListener != null) {
            mListener.onCreate(savedInstanceState);
        }
        setRootView();
        ButterKnife.bind(this);

        //设置沉浸式状态栏
//        StatusBarUtil.setTranslucentForImageView(this, 0, );
        //沉浸式代码配置
        //当FitsSystemWindows设置 true 时，会在屏幕最上方预留出状态栏高度的 padding
        //设置状态栏透明
        //一般的手机的状态栏文字和图标都是白色的, 可如果你的应用也是纯白色的, 或导致状态栏文字看不清
        //所以如果你是这种情况,请使用以下代码, 设置状态使用深色文字图标风格, 否则你可以选择性注释掉这个if内容
        //如果不支持设置深色风格 为了兼容总不能让状态栏白白的看不清, 于是设置一个状态栏颜色为半透明,
        //这样半透明+白=灰, 状态栏的文字能看得清
        // 所有子类都将继承这些相同的属性,请在设置界面之后设置
        ImmersionBar.with(this).init();

        initViews();
        initData(savedInstanceState);
        getDataFromServer();

    }

    //设置布局数据
    protected abstract void setRootView();

    //设置配置数据()比如设置点击事件或者列表里面刷新数据adapter.notify
    protected abstract void initData(Bundle savedInstanceState);

    //findView
    protected abstract void initViews();

    //网络请求绑定适配器用
    public abstract void getDataFromServer();

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);


        try {
            int resId = findBar();
            if (resId > 0) {
                topView = findViewById(resId);
                topView.setFinishActivity(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    protected int findBar() {
        return R.id.alltopview;
    }

    protected void setTitle(String msg) {
        if (topView != null)
            topView.setTitle(msg);
    }

    protected void jumpActivity(Class<?> mClass) {
        Intent intent = new Intent(this, mClass);
        startActivity(intent);
    }

    public void showToast(String str) {
        Toasty.info(this, str, Toasty.LENGTH_SHORT).show();
    }

    protected abstract P createPresenter();

    @Override
    protected void onStart() {
        super.onStart();
        if (mListener != null)
            mListener.onStart();
    }

    public void setOnLifeCycleListener(LifeCycleListener listener) {
        mListener = listener;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (mListener != null)
            mListener.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mListener != null)
            mListener.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mListener != null)
            mListener.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mListener != null)
            mListener.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }


}
