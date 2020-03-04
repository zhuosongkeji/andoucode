package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.model.IListData;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.CheckthelogisticsActivity;
import com.zskjprojectj.andouclient.activity.ShoporderdetailsActivity;
import com.zskjprojectj.andouclient.activity.ToevaluateActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOrderCancelActivity;
import com.zskjprojectj.andouclient.adapter.MallOrderListAdapter;
import com.zskjprojectj.andouclient.event.OrderStateChangedEvent;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Order;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;

import butterknife.BindView;
import io.reactivex.Observable;

public class MallOrderListFragment extends BaseFragment {

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private RecyclerView mRecycler;
    MallOrderListAdapter adapter = new MallOrderListAdapter();
    String state;
    private PageLoadUtil<Order> pageLoadUtil;

    public MallOrderListFragment(String state) {
        this.state = state;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler = view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            //订单详情
            if (view1.getId() == R.id.btn_orderdetails) {
                ShoporderdetailsActivity.start(adapter.getItem(position));
                //取消订单
            }else if(view1.getId()==R.id.btn_cancel_order){
                MallOrderCancelActivity.start(adapter.getItem(position).id);
                //去付款
            } else if (view1.getId() == R.id.btn_gotopayment) {
                MallOnlineOrderActivity.start(adapter.getItem(position).order_id,"","","");
                //确认收货
            } else if (view1.getId() == R.id.btn_getgoods) {
                CustomViewDialog dialog = new CustomViewDialog(mActivity, R.layout.confirm_receipt_view, new int[]{
                        R.id.iv_cancle, R.id.btn_confirm_receipt});
                dialog.setOnCenterItemClickListener(new CustomViewDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CustomViewDialog dialog, View view) {
                        switch (view.getId()) {
                            case R.id.iv_cancle:
                                dialog.dismiss();
                                break;
                            case R.id.btn_confirm_receipt:
                                HttpRxObservable.getObservable(ApiUtils.getApiService().confirm(
                                        LoginInfoUtil.getUid(),
                                        LoginInfoUtil.getToken(),
                                        adapter.getItem(position).id

                                )).subscribe(new BaseObserver<Object>(mActivity) {
                                    @Override
                                    public void onHandleSuccess(Object o) throws IOException {
                                        EventBus.getDefault().post(new OrderStateChangedEvent());
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

                Intent intent = new Intent(mActivity, CheckthelogisticsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
                //发表评论
            } else if (view1.getId() == R.id.btn_comment) {

                ToevaluateActivity.start("" + adapter.getItem(position).goods_id, adapter.getItem(position).order_id, "" + adapter.getItem(position).merchant_id, adapter.getItem(position).img);

            }
        });


        getDataFromServer();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentEventBus(PaySuccessEvent paySuccessEvent) {
        pageLoadUtil.refresh();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onOrderStateChanged(OrderStateChangedEvent event) {
        pageLoadUtil.refresh();
    }


    private void getDataFromServer() {
        pageLoadUtil = PageLoadUtil.get((BaseActivity) getActivity(), mRecycler, adapter, mRefreshLayout);
        pageLoadUtil.load(new RequestUtil.ObservableProvider<IListData<Order>>() {
            @Override
            public Observable<? extends BaseResult<? extends IListData<Order>>> getObservable() {
                return ApiUtils.getApiService().orderList(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        state,
                        pageLoadUtil.page
                );
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mall_order_list;
    }
}
