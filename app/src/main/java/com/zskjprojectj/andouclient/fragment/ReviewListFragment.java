package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.ReviewAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.model.Review;

public class ReviewListFragment extends BaseFragment {

    private Restaurant restaurant;

    public ReviewListFragment(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PageLoadUtil<Review> pageLoadUtil = PageLoadUtil.get(mActivity,
                view.findViewById(R.id.recyclerView),
                new ReviewAdapter(),
                view.findViewById(R.id.refreshLayout));
        pageLoadUtil.load(() -> ApiUtils.getApiService().getReviews(restaurant.id, pageLoadUtil.page));
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_review_list;
    }
}
