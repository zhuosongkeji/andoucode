package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.HotelorderevaluateAdapter;
import com.zskjprojectj.andouclient.adapter.MehotelorderAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.HotelorderevaluateBean;
import com.zskjprojectj.andouclient.entity.MehotelorderBean;

import java.util.ArrayList;


/**
 * 酒店管理待评价
 */
public class MeHotelorderevaluateFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<HotelorderevaluateBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mehotelorderevaluate;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        for (int i=0;i<3;i++){
            HotelorderevaluateBean databean=new HotelorderevaluateBean();
            mDataList.add(databean);
        }

        HotelorderevaluateAdapter adapter=new HotelorderevaluateAdapter(R.layout.item_fragmenthotelorderevaluate,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);

    }
}
