package com.zskjprojectj.andouclient.fragment.restaurant;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantOrderListAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.RestaurantOrder;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

public class RestaurantOrderListFragment extends BaseFragment {

    private int state;

    private RestaurantOrderListAdapter adapter = new RestaurantOrderListAdapter();

    public RestaurantOrderListFragment(int state) {
        this.state = state;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PageLoadUtil<RestaurantOrder> pageLoadUtil = PageLoadUtil.get(mActivity,
                view.findViewById(R.id.recyclerView),
                adapter,
                view.findViewById(R.id.refreshLayout));
        pageLoadUtil.load(() -> ApiUtils.getApiService().orderList(
                LoginInfoUtil.getUid(),
                pageLoadUtil.page,
                state
        ));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_restaurant_order_list;
    }
}
