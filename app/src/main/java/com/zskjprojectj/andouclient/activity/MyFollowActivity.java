package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity;
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity;
import com.zskjprojectj.andouclient.adapter.MyFollowAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFollowActivity extends BaseActivity {
    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;
    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;
    private RecyclerView mRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "店铺关注");
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        loadData();
    }

    private void loadData() {
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().usersfollow(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()), result -> {
                    MyFollowAdapter adapter = new MyFollowAdapter(R.layout.item_focuson, result.data);
                    adapter.setEmptyView(R.layout.layout_empty_view, mRecycler);
                    adapter.openLoadAnimation();
                    mRecycler.setAdapter(adapter);
                    adapter.setOnItemChildClickListener((adapter1, view, position) -> {
                        switch (result.data.get(position).getMerchant_type_id()) {
                            //商家商城
                            case "2":
                                MallShoppingHomeActivity.start(result.data.get(position).getId());
                                break;
                            //酒店商家
                            case "3":
                                HotelDetailActivity.start(result.data.get(position).getId());
                                break;
                            //饭店商家
                            case "4":
                                RestaurantDetailActivity.start(result.data.get(position).getId());
                                break;
                            //农家乐
                            case "5":
                                break;
                            //旅游
                            case "6":
                                break;
                            //美食预订
                            case "7":
                                break;
                            //农家乐民宿
                            case "8":
                                break;
                        }
                    });
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    @OnClick(R.id.mHeaderBack)
    public void clickView() {
        finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_follow;
    }
}
