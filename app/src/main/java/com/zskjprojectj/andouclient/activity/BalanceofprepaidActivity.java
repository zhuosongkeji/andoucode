package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BalanceofprepaidPaywayAdapter;
import com.zskjprojectj.andouclient.adapter.BalancesubsidiaryAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BalanceofprepaidpaywayBean;
import com.zskjprojectj.andouclient.entity.BalancesubsidiaryBean;

import java.util.ArrayList;


/**
 *余额充值
 */
public class BalanceofprepaidActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<BalanceofprepaidpaywayBean> mDataList;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_balanceofprepaid);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("余额充值");
        mDataList = new ArrayList<>();
        for (int i=0;i<3;i++){
            BalanceofprepaidpaywayBean databean=new BalanceofprepaidpaywayBean();
            databean.setPaywayname("微信支付");
            mDataList.add(databean);
        }
        BalanceofprepaidPaywayAdapter adapter=new BalanceofprepaidPaywayAdapter(R.layout.item_balanceofprepaidpayway,mDataList);
        adapter.openLoadAnimation();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //showDialog();
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
