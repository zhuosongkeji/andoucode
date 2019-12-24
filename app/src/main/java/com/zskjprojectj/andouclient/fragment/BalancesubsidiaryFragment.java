package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BalancesubsidiaryAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.ReserveAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.BalancesubsidiaryBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;

import java.util.ArrayList;

/**
 * 余额明细
 */
public class BalancesubsidiaryFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<BalancesubsidiaryBean> mDataList;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_balancesubsidiary;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList = new ArrayList<>();
        for (int i=0;i<20;i++){
            BalancesubsidiaryBean databean=new BalancesubsidiaryBean();
            databean.setTitlename("外卖订单完成");
            databean.setPrice("+144");
            databean.setTime("2019-08-26 17:52");
            mDataList.add(databean);
        }
        BalancesubsidiaryAdapter adapter=new BalancesubsidiaryAdapter(R.layout.item_balancesubsidiary,mDataList);
        adapter.openLoadAnimation();
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                ToastUtil.showToast("点击了第"+position);
//                showDialog();
//
//            }
//        });
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