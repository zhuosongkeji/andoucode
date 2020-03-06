package com.zskjprojectj.andouclient.base;

import android.app.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;


import com.zskjprojectj.andouclient.listener.LifeCycleListener;
import com.zskjprojectj.andouclient.utils.BarUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment extends Fragment {
    protected static final String TAG = "wangbin";
    private Unbinder mUnBinder;
    protected Activity mAty;
    private LifeCycleListener mListener;
    private boolean canExcute = true;//为了onActivityCreated中setUserVisibleHint方法只执行一次
    private boolean isFirstExcute = true;//标记页面是否是第一次加载，为了初始化只执行一次
    public void showToast(String str) {
    }


    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewRes(), container, false);
        mUnBinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (canExcute) {//onActivityCreated方法执行之前，不让此方法执行
            return;
        }
        if (isVisibleToUser && isFirstExcute) {// 页面可见并且是第一次加载
            isFirstExcute = false;//标记页面已经加载过一次，以后不需要在执行
            onFirsLoad();
        }
    }

    @Override
    public void onViewCreated(View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view, savedInstanceState);
        initData();
    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mAty = activity;
    }
    @Override
    public void onActivityCreated( Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (canExcute) {//页面第一次加载时执行，以后不在执行
            canExcute = false;
            setUserVisibleHint(getUserVisibleHint());
        }
    }
    protected void onFirsLoad() {
        getDataFromServer();
    }
    protected abstract int getContentViewRes();
    protected abstract void initViews(View view, Bundle savedInstanceState);
    protected abstract void initData();
    protected abstract void getDataFromServer();


    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void startActivity(Class<?> tClass){
        Intent intent=new Intent(getContext(),tClass);
        startActivity(intent);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnBinder != null) {
            mUnBinder.unbind();
            mUnBinder = null;
        }
    }

    protected void getBarDistance(View view){
        //设置状态栏的高度
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
        layoutParams.topMargin = BarUtils.getStatusBarHeight(getActivity()) + layoutParams.topMargin;
        view.setLayoutParams(layoutParams);
    }


}
