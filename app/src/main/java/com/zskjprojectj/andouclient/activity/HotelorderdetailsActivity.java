package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

public class HotelorderdetailsActivity extends BaseActivity {
    private Button btn_hotelordercancle,btn_hotelordercall;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelorderdetails);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("订单详情");
    }

    @Override
    protected void initViews() {
        btn_hotelordercancle=findViewById(R.id.btn_hotelordercancle);
        btn_hotelordercall=findViewById(R.id.btn_hotelordercall);
        btn_hotelordercancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(HotelordercancleActivity.class);
            }
        });
        btn_hotelordercall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phoneNumber = "13812342345";
                    Intent myCallIntent = new Intent(Intent.ACTION_DIAL,
                            Uri.parse("tel" + phoneNumber));
                    startActivity(myCallIntent);
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
