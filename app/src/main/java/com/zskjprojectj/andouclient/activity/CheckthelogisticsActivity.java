package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.CheckthelogisticsAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.CheckLogisticsBean;
import com.zskjprojectj.andouclient.entity.CheckthelogisticsBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;

public class CheckthelogisticsActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<CheckthelogisticsBean> mDataList;
    private String express_id;
    private String courier_num;

    //快递公司
    @BindView(R.id.tv_express_company)
    TextView mTvExpressCompany;
    //快递单号
    @BindView(R.id.tv_express_number)
    TextView mTvExpressNumber;



    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_checkthelogistics);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("查看物流");

        express_id = getIntent().getStringExtra("express_id");
        courier_num = getIntent().getStringExtra("courier_num");
        Log.d(TAG, "快递公司ID: "+express_id+" "+"快递单号："+ courier_num);

    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setFocusable(false);
        //解决ScrollView嵌套RecyclerView出现的系列问题
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setHasFixedSize(true);
//        mRecycler.setAdapter(new CheckthelogisticsAdapter(mAt,R.layout.item_checkthelogistics, data()));
    }
//    private List<CheckthelogisticsBean> data {
//        mDataList = new ArrayList<>();
//        mDataList.add(new CheckthelogisticsBean("2018-05-20 13:37:57", "客户 签收人: 他人代收 已签收 感谢使用圆通速递，期待再次为您服务"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-20 09:03:42", "【广东省深圳市宝安区新安公司】 派件人: 陆黄星 派件中 派件员电话13360979918"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-20 08:27:10", "【广东省深圳市宝安区新安公司】 已收入"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-20 04:38:32", "【深圳转运中心】 已收入"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-19 01:27:49", "【北京转运中心】 已发出 下一站 【深圳转运中心】"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-19 01:17:19", "【北京转运中心】 已收入"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-18 18:34:28", "【河北省保定市容城县公司】 已发出 下一站 【北京转运中心】"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-18 18:33:23", "【河北省保定市容城县公司】 已打包"));
//        mDataList.add(new CheckthelogisticsBean("2018-05-18 18:27:21", "【河北省保定市容城县公司】 已收件"));
//        return mDataList;
//    }
    @Override
    public void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().CheckLogistics(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                express_id,
                courier_num
        )).subscribe(new BaseObserver<CheckLogisticsBean>(mAt) {
            @Override
            public void onHandleSuccess(CheckLogisticsBean checkLogisticsBean) throws IOException {
                mRecycler.setAdapter(new CheckthelogisticsAdapter(mAt,R.layout.item_checkthelogistics, checkLogisticsBean.getWuliu_msg().getData()));
                mTvExpressCompany.setText(checkLogisticsBean.getName());
                mTvExpressNumber.setText(checkLogisticsBean.getCourier_num());


            }
        });


    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


}
