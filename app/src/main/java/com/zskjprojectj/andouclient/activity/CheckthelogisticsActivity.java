package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.CheckthelogisticsAdapter;
import com.zskjprojectj.andouclient.entity.CheckthelogisticsBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "查看物流");
        express_id = getIntent().getStringExtra("express_id");
        courier_num = getIntent().getStringExtra("courier_num");
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        mRecycler.setFocusable(false);
        //解决ScrollView嵌套RecyclerView出现的系列问题
        mRecycler.setNestedScrollingEnabled(false);
        mRecycler.setHasFixedSize(true);

        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().CheckLogistics(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        express_id,
                        courier_num
                ),
                result -> {
                    mRecycler.setAdapter(new CheckthelogisticsAdapter(mActivity, R.layout.item_checkthelogistics, result.data.getWuliu_msg().getData()));
                    mTvExpressCompany.setText(result.data.getName());
                    mTvExpressNumber.setText(result.data.getCourier_num());
                }
        );

    }


    @Override
    protected int getContentView() {
        return R.layout.activity_checkthelogistics;
    }
}
