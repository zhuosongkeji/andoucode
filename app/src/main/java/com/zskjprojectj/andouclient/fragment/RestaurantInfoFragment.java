package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Restaurant;

public class RestaurantInfoFragment extends BaseFragment {

    private Restaurant restaurant;

    public RestaurantInfoFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ViewUtil.setText(mActivity, R.id.contentTxt, restaurant.desc);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_restaurant_info;
    }
}
