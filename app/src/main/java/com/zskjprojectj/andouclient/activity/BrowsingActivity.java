package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BrowsingAdapter;
import com.zskjprojectj.andouclient.adapter.MycollectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BrowsingBean;
import com.zskjprojectj.andouclient.entity.MycollectionBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 浏览痕迹
 */
public class BrowsingActivity extends BaseActivity {
    private RecyclerView mRecycler;
    BrowsingAdapter adapter=new BrowsingAdapter();
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_browsing);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("浏览痕迹");
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
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
