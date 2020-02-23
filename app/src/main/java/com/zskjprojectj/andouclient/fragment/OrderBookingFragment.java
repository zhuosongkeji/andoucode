package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.OnlineBookingAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.OnlineBookingBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 在线预订模块预订模块碎片
 */
public class OrderBookingFragment extends BaseFragment {
    private Button btn_orderbook_now;
    private RecyclerView mRecycler;
    private ArrayList<OnlineBookingBean> dataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

        btn_orderbook_now=view.findViewById(R.id.btn_orderbook_now);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_orderbooking;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

        dataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            OnlineBookingBean databean=new OnlineBookingBean();
            databean.setOnlineName("屠宰场毛肚");
            dataList.add(databean);
        }


        OnlineBookingAdapter adapter=new OnlineBookingAdapter(R.layout.fragment_online_booking,dataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);




        btn_orderbook_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("功能完善中，敬请期待~");

//                startActivity(new Intent(getContext(), OrederSubmitbooking.class));
            }
        });
    }
}
