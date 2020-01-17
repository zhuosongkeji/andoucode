package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Layout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.FacilityAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailFacilityBean;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 酒店详情-环境设施
 */
public class HotelDetailFacilityActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    private RecyclerView mRecycler;
    private ArrayList<HotelDetailFacilityBean> mDataList;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_detail_facility);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        mDataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            HotelDetailFacilityBean databean=new HotelDetailFacilityBean();
            databean.setImageResource(R.mipmap.hotel_details);
            mDataList.add(databean);
        }
        //FacilityAdapter adapter=new FacilityAdapter(R.layout.facility_item_view,mDataList);
       // mRecycler.setAdapter(adapter);


    }

    @Override
    protected void initViews() {
        mHeaderTitle.setText("环境设施");
        getBarDistance(mHeaderTitleView);
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(this,3));
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    @OnClick(R.id.iv_header_back)
    public void clickBack(){
        finish();
    }
}
