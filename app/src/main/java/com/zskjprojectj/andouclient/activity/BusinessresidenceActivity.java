package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.model.UserIn;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商家入驻
 */
public class BusinessresidenceActivity extends BaseActivity {
    public static final int REQUEST_CODE_JOIN = 666;
    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;
    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;

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
        getBarDistance(mTitleView);
        mHeaderTitle.setText("商家入驻");
    }

    @Override
    protected void initViews() {
        restaurant_businessin_layout = findViewById(R.id.restaurant_businessin_layout);
        hotel_businessin_layout = findViewById(R.id.hotel_businessin_layout);
        mallmerchants_businessin_layout = findViewById(R.id.mallmerchants_businessin_layout);
        //在线预订商家（饭店）
        restaurant_businessin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              // jumpActivity(RestaurantbusinessinActivity.class);
                MallmerchantsbusinessinActivity.start(mAt, UserIn.Role.Type.RESTAURANT,REQUEST_CODE_JOIN);
            }
        });
        //酒店商家
        hotel_businessin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // jumpActivity(HotelbusinessinActivity.class);
                MallmerchantsbusinessinActivity.start(mAt, UserIn.Role.Type.HOTEL,REQUEST_CODE_JOIN);
            }
        });
        //商城商家
        mallmerchants_businessin_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  jumpActivity(MallmerchantsbusinessinActivity.class);
                MallmerchantsbusinessinActivity.start(mAt, UserIn.Role.Type.MALL,REQUEST_CODE_JOIN);
            }
        });
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_JOIN && resultCode == Activity.RESULT_OK) {
            setResult(Activity.RESULT_OK);
            finish();
        }
    }
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.mHeaderBack)
    public void clickView() {
        finish();
    }

}
