package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BrowsingAdapter;
import com.zskjprojectj.andouclient.adapter.MyaddressAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BrowsingBean;
import com.zskjprojectj.andouclient.entity.MyaddressBean;

import java.util.ArrayList;

/**
 * 我的地址
 */
public class MyaddressActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<MyaddressBean> mDataList;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_myaddress);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("我的地址");
        mDataList=new ArrayList<>();
        for (int i=0;i<3;i++){
            MyaddressBean databean=new MyaddressBean();
//            databean.setBrowsingnpic(R.mipmap.ic_busiess_canting);
//            databean.setBrowsingname("北平楼涮羊肉");
            mDataList.add(databean);
        }

        MyaddressAdapter adapter=new MyaddressAdapter(R.layout.item_myaddress,mDataList);
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
