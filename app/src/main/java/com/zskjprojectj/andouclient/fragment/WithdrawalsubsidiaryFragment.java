package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BalancesubsidiaryAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.BalancesubsidiaryBean;

import java.util.ArrayList;

/**
 * 提现明细
 */
public class WithdrawalsubsidiaryFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<BalancesubsidiaryBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_withdrawalsubsidiary;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList = new ArrayList<>();
        for (int i=0;i<20;i++){
            BalancesubsidiaryBean databean=new BalancesubsidiaryBean();
            databean.setTitlename("余额提现");
            databean.setPrice("+100");
            databean.setTime("2019-08-26 17:52");
            mDataList.add(databean);
        }
        BalancesubsidiaryAdapter adapter=new BalancesubsidiaryAdapter(R.layout.item_withdrawalsubsidiary,mDataList);
        adapter.openLoadAnimation();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //showDialog();
            }
        });
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }
}
