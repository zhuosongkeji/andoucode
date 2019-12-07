package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.OrederSubmitbooking;
import com.zskjprojectj.andouclient.base.BaseFragment;

/**
 * 在线预订模块预订模块碎片
 */
public class OrderBookingFragment extends BaseFragment {
    private Button btn_orderbook_now;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        btn_orderbook_now=view.findViewById(R.id.btn_orderbook_now);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_orderbooking;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        btn_orderbook_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), OrederSubmitbooking.class));
            }
        });
    }
}
