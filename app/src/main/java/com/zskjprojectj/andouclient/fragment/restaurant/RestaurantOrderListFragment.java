package com.zskjprojectj.andouclient.fragment.restaurant;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantBillActivity;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantOrderDetailActivity;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantOrderListAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.RestaurantOrder;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import butterknife.BindView;

public class RestaurantOrderListFragment extends BaseFragment {

    private int state;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    private RestaurantOrderListAdapter adapter = new RestaurantOrderListAdapter();

    public RestaurantOrderListFragment(int state) {
        this.state = state;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        adapter.setOnItemClickListener((adapter1, view, position) -> {
            RestaurantOrder order = adapter.getItem(position);
            if (order.status != RestaurantOrder.STATE.DAI_ZHI_FU.stateInt) {
                RestaurantOrderDetailActivity.start(mActivity, order.id, 666);
            } else {
                RestaurantBillActivity.start(mActivity, order, 666);
            }
        });
        PageLoadUtil<RestaurantOrder> pageLoadUtil = PageLoadUtil.get(mActivity,
                view.findViewById(R.id.recyclerView),
                adapter,
                refreshLayout);
        pageLoadUtil.load(() -> ApiUtils.getApiService().getOrderList(
                LoginInfoUtil.getUid(),
                pageLoadUtil.page,
                state
        ));
    }

    public void refresh() {
        refreshLayout.autoRefresh();
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_restaurant_order_list;
    }
}
