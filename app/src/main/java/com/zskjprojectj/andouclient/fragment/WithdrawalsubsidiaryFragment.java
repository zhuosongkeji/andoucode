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

/**
 * 提现明细
 */
public class WithdrawalsubsidiaryFragment extends BaseFragment {
    private RecyclerView mRecycler;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler = view.findViewById(R.id.recyclerView);
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

        BalancesubsidiaryAdapter adapter = new BalancesubsidiaryAdapter();
        adapter.openLoadAnimation();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //showDialog();
            }
        });
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }
}
