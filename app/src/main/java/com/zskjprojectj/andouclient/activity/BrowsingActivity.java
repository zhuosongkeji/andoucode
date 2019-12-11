package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BrowsingAdapter;
import com.zskjprojectj.andouclient.adapter.MycollectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BrowsingBean;
import com.zskjprojectj.andouclient.entity.MycollectionBean;

import java.util.ArrayList;

/**
 * 浏览痕迹
 */
public class BrowsingActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<BrowsingBean> mDataList;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_browsing);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("浏览痕迹");
        mDataList=new ArrayList<>();
        for (int i=0;i<10;i++){
            BrowsingBean databean=new BrowsingBean();
            databean.setBrowsingnpic(R.mipmap.ic_busiess_canting);
            databean.setBrowsingname("北平楼涮羊肉");
            mDataList.add(databean);
        }

        BrowsingAdapter adapter=new BrowsingAdapter(R.layout.item_browsing,mDataList);
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
