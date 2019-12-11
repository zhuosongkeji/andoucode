package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MehotelorderAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.CommentAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MehotelorderBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailCommentBean;

import java.util.ArrayList;


/**
 * 酒店预定所有订单
 */
public class MeHotelorderFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MehotelorderBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mehotelorder;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        for (int i=0;i<3;i++){
            MehotelorderBean databean=new MehotelorderBean();
            mDataList.add(databean);
        }

        MehotelorderAdapter adapter=new MehotelorderAdapter(R.layout.item_fragmentmehotelorder,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);

    }
}
