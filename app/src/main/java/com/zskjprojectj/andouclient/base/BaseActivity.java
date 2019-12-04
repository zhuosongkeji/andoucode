package com.zskjprojectj.andouclient.base;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.listener.LifeCycleListener;
import com.zskjprojectj.andouclient.view.TopView;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import es.dmoral.toasty.Toasty;
import io.reactivex.annotations.Nullable;

public abstract class BaseActivity<P extends BasePresenter> extends RxAppCompatActivity {
    private Unbinder mUnBinder;
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

        mUnBinder = ButterKnife.bind(this);
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
        mUnBinder.unbind();
        if (mListener != null) {
            mListener.onDestroy();
        }
    }


}
