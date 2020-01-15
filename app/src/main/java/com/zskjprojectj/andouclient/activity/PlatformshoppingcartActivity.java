package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.model.IListData;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.PlatformshoppingcartAdapter;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;

/**
 * 个人中心购物车
 */
public class PlatformshoppingcartActivity extends BaseActivity {

   @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private RecyclerView mRecycler;
    private Button btn_gotoaccounts;
    PlatformshoppingcartAdapter adapter = new PlatformshoppingcartAdapter();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
        getDataFromServer();
    }

    private void initData() {
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }

    private void initViews() {
        mRecycler = findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        btn_gotoaccounts = findViewById(R.id.btn_gotoaccounts);
        btn_gotoaccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PlatformshoppingcartActivity.this,MallOnlineOrderActivity.class));
            }
        });
    }

    private void getDataFromServer() {
        PageLoadUtil<CartItem> pageLoadUtil = PageLoadUtil.get(mActivity,mRecycler, adapter, mRefreshLayout);
        pageLoadUtil.load(new RequestUtil.ObservableProvider<IListData<CartItem>>() {
            @Override
            public Observable<? extends BaseResult<? extends IListData<CartItem>>> getObservable() {
                return ApiUtils.getApiService().cart(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        pageLoadUtil.page);
            }
        });
    }



    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_platformshoppingcart;
    }
}
