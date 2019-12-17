package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.HotelorderevaluateAdapter;
import com.zskjprojectj.andouclient.adapter.MeHotelordercancelledAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.HotelorderevaluateBean;
import com.zskjprojectj.andouclient.entity.MeHotelordercancelledBean;

import java.util.ArrayList;


/**
 * 已取消订单
 */
public class MeHotelordercancelledFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MeHotelordercancelledBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mehotelordercancle;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        for (int i=0;i<3;i++){
            MeHotelordercancelledBean databean=new MeHotelordercancelledBean();
            mDataList.add(databean);
        }

        MeHotelordercancelledAdapter adapter=new MeHotelordercancelledAdapter(R.layout.item_fragmenthotelordercancle,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }
}
