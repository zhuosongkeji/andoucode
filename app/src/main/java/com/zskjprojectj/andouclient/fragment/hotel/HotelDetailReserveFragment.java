package com.zskjprojectj.andouclient.fragment.hotel;


import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.ReserveAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 酒店详情预订
 * bin
 * 2019/12/7
 */
public class HotelDetailReserveFragment extends BaseFragment {

    private RecyclerView mRecycler;
    private ArrayList<HotelDetailReserveBean> mDataList;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_hotel_detail_reserve;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

        mDataList = new ArrayList<>();
        for (int i=0;i<20;i++){
            HotelDetailReserveBean databean=new HotelDetailReserveBean();
            databean.setHotelName("阳光大床房（带大阳台）");
            databean.setHotelPrice("187");
            databean.setImageResource(R.mipmap.hotel_details);
            mDataList.add(databean);
        }
        ReserveAdapter adapter=new ReserveAdapter(R.layout.reserver_item_view,mDataList);
        adapter.openLoadAnimation();
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                ToastUtil.showToast("点击了第"+position);
//                showDialog();
//
//            }
//        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                showDialog();
            }
        });
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);


    }

    private void showDialog() {

        Dialog bottomDialog = new Dialog(getActivity(), R.style.BottomDialog);
        View contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hotel_detail, null);
        bottomDialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

    }
}
