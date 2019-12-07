package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.HotelResultAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.HotelResultBean;

import java.util.ArrayList;

/**
 * 酒店列表
 * Bin
 * 2019/12/6
 */
public class HotelFilterActivity extends BaseActivity {

    private RecyclerView mRecycler;
    private ArrayList<HotelResultBean> mDataList;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_filter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mDataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            HotelResultBean databean=new HotelResultBean();
            databean.setHotelName("精尚来公寓酒店");
            mDataList.add(databean);
        }

        HotelResultAdapter adapter=new HotelResultAdapter(R.layout.hotelresuilt_item_view,mDataList);
        mRecycler.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);




    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
