package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.BarUtils;

/**
 * 酒店预约
 *  bin
 *  2019/12/5
 */
public class HotelActivity extends BaseActivity implements View.OnClickListener {

private RecyclerView mRvRecycler;
private LinearLayout mTitle,mHotel_list,mSearchHotel;


    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
    mTitle=findViewById(R.id.titlt_view);
        //设置状态栏的高度
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) mTitle.getLayoutParams();
        layoutParams.topMargin= BarUtils.getStatusBarHeight(this);
        mTitle.setLayoutParams(layoutParams);

        mHotel_list=findViewById(R.id.ll_hotel_list);
        mSearchHotel=findViewById(R.id.search_hotel);
        mHotel_list.setOnClickListener(this);
        mSearchHotel.setOnClickListener(this);

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
            case R.id.ll_hotel_list:
                Intent hotelListIntent=new Intent(this,HotelDetailActivity.class);
                startActivity(hotelListIntent);
                break;
            case R.id.search_hotel:
                Intent searchTotelIntent=new Intent(this,HotelFilterActivity.class);
                startActivity(searchTotelIntent);
                break;
        }
    }
}
