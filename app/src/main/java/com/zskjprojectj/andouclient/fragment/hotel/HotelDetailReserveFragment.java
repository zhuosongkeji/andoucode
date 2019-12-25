package com.zskjprojectj.andouclient.fragment.hotel;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.hotel.HotelOnlineReserveActivity;
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
public class HotelDetailReserveFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecycler;
    private ArrayList<HotelDetailReserveBean> mDataList;
    private View contentView;
    private Dialog bottomDialog;

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
        ReserveAdapter adapter=new ReserveAdapter();
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

        bottomDialog = new Dialog(mAty, R.style.BottomDialog);

        Window window = bottomDialog.getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);


        contentView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_hotel_detail, null);
        bottomDialog.setContentView(contentView);
        TextView mReserve = contentView.findViewById(R.id.tv_reserve);
        mReserve.setOnClickListener(this);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_reserve:
//                HotelOnlineReserveActivity
                Intent intent=new Intent(getActivity(),HotelOnlineReserveActivity.class);
                startActivity(intent);
                bottomDialog.dismiss();
                break;
        }
    }
}
