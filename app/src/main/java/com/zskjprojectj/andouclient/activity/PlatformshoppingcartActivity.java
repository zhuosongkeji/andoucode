package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.BalancesubsidiaryAdapter;
import com.zskjprojectj.andouclient.adapter.PlatformshoppingcartAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BalancesubsidiaryBean;
import com.zskjprojectj.andouclient.entity.PlatformshoppingcartBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.utils.TestUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 个人中心购物车
 */
public class PlatformshoppingcartActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private Button btn_gotoaccounts;
    PlatformshoppingcartAdapter adapter = new PlatformshoppingcartAdapter();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_platformshoppingcart);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("购物车");
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(mAt, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        mRecycler = findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
        btn_gotoaccounts = findViewById(R.id.btn_gotoaccounts);
        btn_gotoaccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(MallOnlineOrderActivity.class);
            }
        });
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().cart(
                TestUtil.getUid(),
                TestUtil.getToken()
        )).subscribe(new BaseObserver<List<CartItem>>(mAt) {
            @Override
            public void onHandleSuccess(List<CartItem> cartItem) throws IOException {
                adapter.setNewData(cartItem);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
