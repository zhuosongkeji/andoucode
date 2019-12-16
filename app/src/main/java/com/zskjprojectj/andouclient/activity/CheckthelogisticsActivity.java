package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.CheckthelogisticsAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.CheckthelogisticsBean;
import com.zskjprojectj.andouclient.entity.PlatformshoppingcartBean;

import java.util.ArrayList;
import java.util.List;

public class CheckthelogisticsActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<CheckthelogisticsBean> mDataList;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_checkthelogistics);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("查看物流");
    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setFocusable(false);
        //解决ScrollView嵌套RecyclerView出现的系列问题
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(new CheckthelogisticsAdapter(mAt,R.layout.item_checkthelogistics, getData()));
    }
    private List<CheckthelogisticsBean> getData() {
        mDataList = new ArrayList<>();
        mDataList.add(new CheckthelogisticsBean("2018-05-20 13:37:57", "客户 签收人: 他人代收 已签收 感谢使用圆通速递，期待再次为您服务"));
        mDataList.add(new CheckthelogisticsBean("2018-05-20 09:03:42", "【广东省深圳市宝安区新安公司】 派件人: 陆黄星 派件中 派件员电话13360979918"));
        mDataList.add(new CheckthelogisticsBean("2018-05-20 08:27:10", "【广东省深圳市宝安区新安公司】 已收入"));
        mDataList.add(new CheckthelogisticsBean("2018-05-20 04:38:32", "【深圳转运中心】 已收入"));
        mDataList.add(new CheckthelogisticsBean("2018-05-19 01:27:49", "【北京转运中心】 已发出 下一站 【深圳转运中心】"));
        mDataList.add(new CheckthelogisticsBean("2018-05-19 01:17:19", "【北京转运中心】 已收入"));
        mDataList.add(new CheckthelogisticsBean("2018-05-18 18:34:28", "【河北省保定市容城县公司】 已发出 下一站 【北京转运中心】"));
        mDataList.add(new CheckthelogisticsBean("2018-05-18 18:33:23", "【河北省保定市容城县公司】 已打包"));
        mDataList.add(new CheckthelogisticsBean("2018-05-18 18:27:21", "【河北省保定市容城县公司】 已收件"));
        return mDataList;
    }
    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
