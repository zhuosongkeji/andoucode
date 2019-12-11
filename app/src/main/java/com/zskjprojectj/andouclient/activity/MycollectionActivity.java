package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MerchantListAdapter;
import com.zskjprojectj.andouclient.adapter.MycollectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.MerchantListBean;
import com.zskjprojectj.andouclient.entity.MycollectionBean;

import java.util.ArrayList;

public class MycollectionActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<MycollectionBean> mDataList;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mycollection);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mDataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            MycollectionBean databean=new MycollectionBean();
            databean.setCollectionpic(R.mipmap.ic_busiess_canting);
            databean.setCollectionname("北平楼涮羊肉");
            mDataList.add(databean);
        }

        MycollectionAdapter adapter=new MycollectionAdapter(R.layout.item_mycollection,mDataList);
        adapter.openLoadAnimation();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRecycler.addItemDecoration(new DividerItemDecoration(mAt,DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        topView.setTitle("我的收藏");
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
