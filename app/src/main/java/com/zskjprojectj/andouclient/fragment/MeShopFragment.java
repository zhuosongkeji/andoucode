package com.zskjprojectj.andouclient.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.CheckthelogisticsActivity;
import com.zskjprojectj.andouclient.activity.ShoporderdetailsActivity;
import com.zskjprojectj.andouclient.activity.ToevaluateActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.MeShopFragmentAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.ReserveAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MeShopFragmentBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailReserveBean;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的商城全部订单
 */
public class MeShopFragment extends BaseFragment {
    private RecyclerView mRecycler;
    MeShopFragmentAdapter adapter = new MeShopFragmentAdapter();
    String state;

    public MeShopFragment(String state) {
        this.state = state;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler = view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            //订单详情
            if (view1.getId() == R.id.btn_orderdetails) {
                ShoporderdetailsActivity.start(adapter.getItem(position));
                //去付款
            } else if (view1.getId() == R.id.btn_gotopayment) {
                MallOnlineOrderActivity.start(adapter.getItem(position).order_id);
                //确认收货
            } else if (view1.getId() == R.id.btn_getgoods) {
                CustomViewDialog dialog = new CustomViewDialog(mAty, R.layout.confirm_receipt_view, new int[]{
                        R.id.iv_cancle, R.id.btn_confirm_receipt});
                dialog.setOnCenterItemClickListener(new CustomViewDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CustomViewDialog dialog, View view) {
                        switch (view.getId()) {
                            case R.id.iv_cancle:
                                dialog.dismiss();
                                break;
                            case R.id.btn_confirm_receipt:
                                int id = adapter.getItem(position).id;
                                String confirmId = String.valueOf(id);
                                HttpRxObservable.getObservable(ApiUtils.getApiService().confirm(
                                        LoginInfoUtil.getUid(),
                                        LoginInfoUtil.getToken(),
                                        confirmId

                                )).subscribe(new BaseObserver<Object>(mAty) {
                                    @Override
                                    public void onHandleSuccess(Object o) throws IOException {
                                        ToastUtil.showToast("确认收货成功");
                                        dialog.dismiss();
                                    }
                                });


                                break;
                        }
                    }
                });
                dialog.show();
                //查看物流
            } else if (view1.getId() == R.id.btn_check_logistics) {

                Bundle bundle = new Bundle();
                bundle.putSerializable("express_id", adapter.getItem(position).express_id);
                bundle.putSerializable("courier_num", adapter.getItem(position).courier_num);

                Intent intent = new Intent(mAty, CheckthelogisticsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //发表评论
            } else if (view1.getId() == R.id.btn_comment) {

                ToevaluateActivity.start("" + adapter.getItem(position).goods_id, adapter.getItem(position).order_id, "" + adapter.getItem(position).merchant_id, adapter.getItem(position).img);

            }
        });
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shopallorder;
    }

    @Override
    protected void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().orderList(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                state
        )).subscribe(new BaseObserver<List<Order>>(mAty) {
            @Override
            public void onHandleSuccess(List<Order> data) throws IOException {
                adapter.setNewData(data);
            }
        });


        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {

    }
}
