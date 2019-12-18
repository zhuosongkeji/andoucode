package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.HotelorderdetailsActivity;
import com.zskjprojectj.andouclient.adapter.MeHotelorderstayinAdapter;
import com.zskjprojectj.andouclient.adapter.MehotelorderAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MeHotelorderstayinBean;
import com.zskjprojectj.andouclient.entity.MehotelorderBean;

import java.util.ArrayList;

/**
 * 待入驻
 */
public class MeHotelorderstayinFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MeHotelorderstayinBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mehotelorderstayin;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        for (int i=0;i<3;i++){
            MeHotelorderstayinBean databean=new MeHotelorderstayinBean();
            mDataList.add(databean);
        }

        MeHotelorderstayinAdapter adapter=new MeHotelorderstayinAdapter(R.layout.item_fragmentmehotelorderstayin,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), HotelorderdetailsActivity.class));
            }
        });
    }
}
