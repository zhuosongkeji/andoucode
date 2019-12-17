package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ShoporderforthegoodsdetailsActivity;
import com.zskjprojectj.andouclient.activity.ToevaluateActivity;
import com.zskjprojectj.andouclient.adapter.MeShopforthegoodsAdapter;
import com.zskjprojectj.andouclient.adapter.MeShoptoevaluateAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MeShopforthegoodsBean;
import com.zskjprojectj.andouclient.entity.MeShoptoevaluateBean;

import java.util.ArrayList;

/**
 * 商品订单待评价订单
 */
public class MeShoptoevaluateFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MeShoptoevaluateBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shopealuate;
    }

    @Override
    protected void getDataFromServer() {
        mDataList = new ArrayList<>();
        for (int i=0;i<4;i++){
            MeShoptoevaluateBean databean=new MeShoptoevaluateBean();
            mDataList.add(databean);
        }
        MeShoptoevaluateAdapter adapter=new MeShoptoevaluateAdapter(R.layout.item_meshoptoevaluate,mDataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ToevaluateActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }
}
