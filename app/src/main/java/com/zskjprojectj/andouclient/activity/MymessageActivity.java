package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MymessageAdapter;
import com.zskjprojectj.andouclient.adapter.MyscoreAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.MymessageBean;
import com.zskjprojectj.andouclient.entity.MyscoreBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 我的消息
 */
public class MymessageActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<MymessageBean> mDataList;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mymessage);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("我的消息");
        mDataList=new ArrayList<>();
        for (int i=0;i<10;i++){
            MymessageBean databean=new MymessageBean();
//            databean.setBrowsingnpic(R.mipmap.ic_busiess_canting);
//            databean.setBrowsingname("北平楼涮羊肉");
            mDataList.add(databean);
        }
        MymessageAdapter adapter=new MymessageAdapter(R.layout.item_mymessage,mDataList);
        adapter.openLoadAnimation();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("数据访问异常");
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
