package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 酒店订单待评价详情界面
 */
public class HotelorderevaluationdetailsActivity extends BaseActivity {
    private Button btn_hotelordergotoevaluationdetail;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelorderevaluationdetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("订单详情");
    }

    @Override
    protected void initViews() {
        btn_hotelordergotoevaluationdetail=findViewById(R.id.btn_hotelordergotoevaluationdetail);
        btn_hotelordergotoevaluationdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(HotelordergotoevaluationActivity.class);
            }
        });
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
