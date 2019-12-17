package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ShoporderdetailsActivity;
import com.zskjprojectj.andouclient.adapter.MeShopFragmentAdapter;
import com.zskjprojectj.andouclient.adapter.MeShoppaymentAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MeShopFragmentBean;
import com.zskjprojectj.andouclient.entity.MeShoppaymentBean;

import java.util.ArrayList;

/**
 * 待付款界面
 */
public class MeShoppaymentFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MeShoppaymentBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shoppayment;
    }

    @Override
    protected void getDataFromServer() {
        mDataList = new ArrayList<>();
        for (int i=0;i<4;i++){
            MeShoppaymentBean databean=new MeShoppaymentBean();
            mDataList.add(databean);
        }
        MeShoppaymentAdapter adapter=new MeShoppaymentAdapter(R.layout.item_meshoppay,mDataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ShoporderdetailsActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }
}
