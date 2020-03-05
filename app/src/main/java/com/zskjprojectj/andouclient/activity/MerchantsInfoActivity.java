package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zskjprojectj.andouclient.R;


/**
 * 商家
 */
public class MerchantsInfoActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImageView busiess_back_image=findViewById(R.id.busiess_back_image);
        busiess_back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    @Override
    protected int getContentView() {
        return R.layout.fragment_merchant_list;
    }
}
