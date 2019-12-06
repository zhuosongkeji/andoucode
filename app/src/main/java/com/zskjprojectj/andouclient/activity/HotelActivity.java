package com.zskjprojectj.andouclient.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 酒店预约
 *  bin
 *  2019/12/5
 */
public class HotelActivity extends BaseActivity {

private RecyclerView mRvRecycler;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initViews() {

        mRvRecycler=findViewById(R.id.rv_recycler);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
