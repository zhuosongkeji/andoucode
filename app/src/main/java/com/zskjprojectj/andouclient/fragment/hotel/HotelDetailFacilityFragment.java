package com.zskjprojectj.andouclient.fragment.hotel;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.FacilityAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailFacilityBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 9:36
 * des:酒店环境设施
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailFacilityFragment extends BaseFragment {

    private RecyclerView mRecycler;
    private ArrayList<HotelDetailFacilityBean> mDataList;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(),3));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_hotel_detail_facility;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        for (int i=0;i<6;i++){
            HotelDetailFacilityBean databean=new HotelDetailFacilityBean();
            databean.setImageResource(R.mipmap.hotel_details);
            mDataList.add(databean);
        }

        FacilityAdapter adapter=new FacilityAdapter(R.layout.facility_item_view,mDataList);
        adapter.openLoadAnimation();
        View footButton = getLayoutInflater().inflate(R.layout.facility_button_foot, (ViewGroup) mRecycler.getParent(), false);
        adapter.addFooterView(footButton);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("sdsafd");
            }
        });

        mRecycler.setAdapter(adapter);

    }
}
