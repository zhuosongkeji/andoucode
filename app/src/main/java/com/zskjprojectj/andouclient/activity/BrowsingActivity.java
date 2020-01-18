package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity;
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity;
import com.zskjprojectj.andouclient.adapter.BrowsingAdapter;
import com.zskjprojectj.andouclient.adapter.MycollectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BrowsingBean;
import com.zskjprojectj.andouclient.entity.MycollectionBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 浏览痕迹
 */
public class BrowsingActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;

    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    private RecyclerView mRecycler;
    BrowsingAdapter adapter=new BrowsingAdapter();
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_browsing);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("浏览痕迹");
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(mAt,DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().merchantrecord(LoginInfoUtil.getUid(),LoginInfoUtil.getToken())).subscribe(new BaseObserver<List<BrowsingBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<BrowsingBean> browsingBeans) throws IOException {
                adapter.setNewData(browsingBeans);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                        switch (browsingBeans.get(position).getMerchant_type_id()){
                            //商家商城
                            case "2":
                                MallShoppingHomeActivity.start(browsingBeans.get(position).getId());
                                break;
                            //酒店商家
                            case "3":
                                HotelDetailActivity.start(browsingBeans.get(position).getId());
                                break;
                            //饭店商家
                            case "4":
                                RestaurantDetailActivity.start(browsingBeans.get(position).getId());
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
                    }
                });
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }
}
