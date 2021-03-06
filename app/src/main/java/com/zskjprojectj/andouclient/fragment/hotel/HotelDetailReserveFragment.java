package com.zskjprojectj.andouclient.fragment.hotel;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.stx.xhb.xbanner.XBanner;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.hotel.HotelOnlineReserveActivity;
import com.zskjprojectj.andouclient.adapter.hotel.ReserveAdapter;
import com.zskjprojectj.andouclient.entity.XBannerBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeDetailsBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 酒店详情预订
 * bin
 * 2019/12/7
 */
public class HotelDetailReserveFragment extends BaseFragment {

    private RecyclerView mRecycler;
    private View contentView;
    private Dialog bottomDialog;
    private ReserveAdapter adapter = new ReserveAdapter();
    private int hotelId;
    private XBanner xBanner;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        String hotelMerchantId = getArguments().getString("hotelMerchantId");
        hotelId = Integer.parseInt(hotelMerchantId);
        mRecycler = view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().hotelDetailsHomeList(hotelId),
                result -> {
                    adapter.setNewData(result.data.getHotel_room());
                    adapter.setOnItemChildClickListener((adapter, view, position) ->
                            RequestUtil.request(mActivity, true, true,
                                    () -> ApiUtils.getApiService().hotelHomeDetails(
                                            String.valueOf(result.data.getHotel_room().get(position).getId())
                                    ),
                                    result1 -> {
                                        showDialog(result1.data);
                                    }));
                });

    }


    private void showDialog(HotelHomeDetailsBean hotelHomeDetailsBean) {
        bottomDialog = new Dialog(mActivity, R.style.BottomDialog);
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
        xBanner = contentView.findViewById(R.id.bannertop);
        List<XBannerBean> urlBanner = new ArrayList<>();
        urlBanner.clear();
        List<String> img = hotelHomeDetailsBean.getImg();
        if (img.size() != 0) {
            for (String s : img) {
                urlBanner.add(new XBannerBean(s));
            }
        }
        xBanner.setBannerData(urlBanner);
        xBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
//                加载本地图片展示
                XBannerBean urlList = (XBannerBean) model;
                String url = UrlUtil.INSTANCE.getImageUrl(urlList.getImageUrl());
                Glide.with(mActivity).load(url).apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_placeholder)).into((ImageView) view);
            }
        });

        TextView mHomeAllPrice = contentView.findViewById(R.id.tv_home_all_price);
        mHomeAllPrice.setText("¥" + hotelHomeDetailsBean.getPrice());

        TextView mHomeName = contentView.findViewById(R.id.name);
        mHomeName.setText(hotelHomeDetailsBean.getHouse_name());

        TextView mHomePrice = contentView.findViewById(R.id.tv_home_price);
        mHomePrice.setText("¥" + hotelHomeDetailsBean.getPrice());

        TextView mHomeAreas = contentView.findViewById(R.id.tv_areas);
        mHomeAreas.setText(hotelHomeDetailsBean.getAreas());

        TextView mHomeWindow = contentView.findViewById(R.id.tv_has_window);
        mHomeWindow.setText(hotelHomeDetailsBean.getHas_window());

        TextView mHomeWifi = contentView.findViewById(R.id.tv_wifi);
        mHomeWifi.setText(hotelHomeDetailsBean.getWifi());

        TextView mHomePersonNUm = contentView.findViewById(R.id.tv_person_num);
        mHomePersonNUm.setText(hotelHomeDetailsBean.getNum_people());

        TextView mHomeBreakfast = contentView.findViewById(R.id.tv_has_breakfast);
        mHomeBreakfast.setText(hotelHomeDetailsBean.getHas_breakfast());

        TextView mHomeBedType = contentView.findViewById(R.id.tv_bed_type);
        mHomeBedType.setText(hotelHomeDetailsBean.getBed_type());

        TextView mHomeOtherSets = contentView.findViewById(R.id.tv_other_sets);
        mHomeOtherSets.setText(hotelHomeDetailsBean.getOther_sets());

        TextView mReserve = contentView.findViewById(R.id.tv_reserve);
        mReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String homeId = String.valueOf(hotelHomeDetailsBean.getId());
                String today = getTime(0);
                String tomorrow = getTime(1);
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().hotelSettlement(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                today,
                                tomorrow,
                                homeId
                        ),
                        result -> {
                            Intent intent = new Intent(getActivity(), HotelOnlineReserveActivity.class);
                            intent.putExtra("hotelSettlementBean", result.data);
                            startActivity(intent);
                            bottomDialog.dismiss();
                        });
            }
        });
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();
    }

    private String getTime(int date) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, date);
        return sf.format(c.getTime());
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_hotel_detail_reserve;
    }
}
