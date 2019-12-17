package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.CheckthelogisticsActivity;
import com.zskjprojectj.andouclient.activity.ShoporderdsendetailsActivity;
import com.zskjprojectj.andouclient.activity.ShoporderforthegoodsdetailsActivity;
import com.zskjprojectj.andouclient.adapter.MeShopforthegoodsAdapter;
import com.zskjprojectj.andouclient.adapter.MeShopsendgoodsAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MeShopforthegoodsBean;
import com.zskjprojectj.andouclient.entity.MeShopsendgoodsBean;

import java.util.ArrayList;

/**
 * 商品订单待收货
 */
public class MeShopforthegoodsFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MeShopforthegoodsBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shopforgoods;
    }

    @Override
    protected void getDataFromServer() {
        mDataList = new ArrayList<>();
        for (int i=0;i<4;i++){
            MeShopforthegoodsBean databean=new MeShopforthegoodsBean();
            mDataList.add(databean);
        }
        MeShopforthegoodsAdapter adapter=new MeShopforthegoodsAdapter(R.layout.item_meshopforgoods,mDataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId())
                {
                    //订单详情
                    case R.id.btn_orderforgoodsdetails:
                        startActivity(new Intent(getContext(), ShoporderforthegoodsdetailsActivity.class));
                        break;
                        // 查看物流
                    case R.id.btn_orderforgoodschecklogistics:
                        startActivity(new Intent(getContext(), CheckthelogisticsActivity.class));
                        break;
                        //确认收货
                    case R.id.btn_suregetgoods:
                        break;

                }
            }
        });
    }

    @Override
    protected void initData() {

    }
}
