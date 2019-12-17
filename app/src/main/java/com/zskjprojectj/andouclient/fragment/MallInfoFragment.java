package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.view.TopView;


/**
 * 在线商城信息
 */
public class MallInfoFragment extends BaseFragment {
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        TopView mTopView = view.findViewById(R.id.alltopview);
        mTopView.setTitle("我的");
        getBarDistance(mTopView);

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mallinfo;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

    }
}
