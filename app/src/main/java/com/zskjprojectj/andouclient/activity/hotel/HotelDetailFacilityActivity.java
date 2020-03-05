package com.zskjprojectj.andouclient.activity.hotel;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailFacilityBean;

import java.util.ArrayList;

/**
 * 酒店详情-环境设施
 */
public class HotelDetailFacilityActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<HotelDetailFacilityBean> mDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "环境设施");
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new GridLayoutManager(this, 3));
        mDataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            HotelDetailFacilityBean databean = new HotelDetailFacilityBean();
            databean.setImageResource(R.mipmap.hotel_details);
            mDataList.add(databean);
        }
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hotel_detail_facility;
    }
}
