package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 商家入驻
 */
public class BusinessresidenceActivity extends BaseActivity {
    //饭店商家入驻
    private RelativeLayout restaurant_businessin_layout;
    //酒店商家入驻
    private RelativeLayout hotel_businessin_layout;
    //商城商家入驻
    private RelativeLayout mallmerchants_businessin_layout;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_businessresidence);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("商家入驻");
    }

    @Override
    protected void initViews() {
        restaurant_businessin_layout=findViewById(R.id.restaurant_businessin_layout);
        hotel_businessin_layout=findViewById(R.id.hotel_businessin_layout);
        mallmerchants_businessin_layout=findViewById(R.id.mallmerchants_businessin_layout);
        //在线预订商家（饭店）
        restaurant_businessin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(RestaurantbusinessinActivity.class);
            }
        });
        //酒店商家
        hotel_businessin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(HotelbusinessinActivity.class);
            }
        });
        //商城商家
        mallmerchants_businessin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(MallmerchantsbusinessinActivity.class);
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
