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
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 我的商城全部订单
 */
public class MeShopFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MeShopFragmentBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shopallorder;
    }

    @Override
    protected void getDataFromServer() {
        mDataList = new ArrayList<>();
        for (int i=0;i<4;i++){
            MeShopFragmentBean databean=new MeShopFragmentBean();
            mDataList.add(databean);
        }
        MeShopFragmentAdapter adapter=new MeShopFragmentAdapter(R.layout.item_meshop,mDataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId())
                {
                    case R.id.btn_gotopayment:
                        startActivity(new Intent(getContext(), MallOnlineOrderActivity.class));
                        break;
                    case R.id.btn_orderdetails:
                        startActivity(new Intent(getContext(), ShoporderdetailsActivity.class));
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
