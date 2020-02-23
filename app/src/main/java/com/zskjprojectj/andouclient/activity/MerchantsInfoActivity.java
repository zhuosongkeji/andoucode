package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;


/**
 * 商家
 */
public class MerchantsInfoActivity extends BaseActivity {
    private ImageView busiess_back_image;
    @Override
    protected void setRootView() {
        setContentView(R.layout.fragment_merchant_list);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        busiess_back_image=findViewById(R.id.busiess_back_image);
        busiess_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
