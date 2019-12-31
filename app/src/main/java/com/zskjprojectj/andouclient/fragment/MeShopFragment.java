package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ShoporderdetailsActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.MeShopFragmentAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.ReserveAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MeShopFragmentBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的商城全部订单
 */
public class MeShopFragment extends BaseFragment {
    private RecyclerView mRecycler;
    MeShopFragmentAdapter adapter = new MeShopFragmentAdapter();
    String state;

    public MeShopFragment(String state) {
        this.state = state;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler = view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            if (view1.getId() == R.id.btn_orderdetails) {
                ShoporderdetailsActivity.start(adapter.getItem(position));
            } else if (view1.getId() == R.id.btn_gotopayment) {
                MallOnlineOrderActivity.start(adapter.getItem(position).order_id);
            }
        });
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shopallorder;
    }

    @Override
    protected void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().orderList(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                state
        )).subscribe(new BaseObserver<List<Order>>(mAty) {
            @Override
            public void onHandleSuccess(List<Order> data) throws IOException {
                adapter.setNewData(data);
            }
        });


        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
