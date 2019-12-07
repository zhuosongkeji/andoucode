package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.HotelResultAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 酒店列表
 * Bin
 * 2019/12/6
 */
public class HotelFilterActivity extends BaseActivity {

    private RecyclerView mRecycler;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_filter);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        mRecycler.setLayoutManager(linearLayoutManager);
//        HotelResultAdapter adapter=new HotelResultAdapter();
//        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.rv_recycler);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
