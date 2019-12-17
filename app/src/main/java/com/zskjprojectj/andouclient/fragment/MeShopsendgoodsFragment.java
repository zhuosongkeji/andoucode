package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ShoporderdetailsActivity;
import com.zskjprojectj.andouclient.activity.ShoporderdsendetailsActivity;
import com.zskjprojectj.andouclient.adapter.MeShoppaymentAdapter;
import com.zskjprojectj.andouclient.adapter.MeShopsendgoodsAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MeShoppaymentBean;
import com.zskjprojectj.andouclient.entity.MeShopsendgoodsBean;

import java.util.ArrayList;


/**
 * 商城订单待发货界面
 */
public class MeShopsendgoodsFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MeShopsendgoodsBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shopsendgoods;
    }

    @Override
    protected void getDataFromServer() {
        mDataList = new ArrayList<>();
        for (int i=0;i<4;i++){
            MeShopsendgoodsBean databean=new MeShopsendgoodsBean();
            mDataList.add(databean);
        }
        MeShopsendgoodsAdapter adapter=new MeShopsendgoodsAdapter(R.layout.item_meshopsend,mDataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), ShoporderdsendetailsActivity.class));
            }
        });
    }

    @Override
    protected void initData() {

    }
}
