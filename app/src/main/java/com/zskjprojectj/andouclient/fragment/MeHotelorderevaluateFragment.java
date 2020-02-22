package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.HotelorderevaluationdetailsActivity;
import com.zskjprojectj.andouclient.activity.HotelordergotoevaluationActivity;
import com.zskjprojectj.andouclient.adapter.HotelorderevaluateAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.HotelorderevaluateBean;

import java.util.ArrayList;


/**
 * 酒店管理待评价
 */
public class MeHotelorderevaluateFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<HotelorderevaluateBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.recyclerView);
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
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId())
                {
                    case R.id.btn_hotelorderevaluationdetails:
                        startActivity(new Intent(getContext(), HotelorderevaluationdetailsActivity.class));
                        break;
                    case R.id.btn_hotelordergotoevaluation:
                        startActivity(new Intent(getContext(), HotelordergotoevaluationActivity.class));
                        break;
                }
            }
        });
    }
}
