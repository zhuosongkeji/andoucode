package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.HotelResultAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.HotelResultBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 酒店列表
 * Bin
 * 2019/12/6
 */
public class HotelFilterActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecycler;
    private ArrayList<HotelResultBean> mDataList;
    private LinearLayout mPriceStar;

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
        mPriceStar=findViewById(R.id.ll_price_star);
        mPriceStar.setOnClickListener(this);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ll_price_star:
                ToastUtil.showToast("sdds");
            break;
        }
    }
}
