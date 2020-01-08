package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity;
import com.zskjprojectj.andouclient.adapter.MyFocusonAdapter;
import com.zskjprojectj.andouclient.adapter.MycollectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.MyFocusonBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MyFocusonActivity extends BaseActivity {
    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    private RecyclerView mRecycler;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_focuson);
    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("店铺关注");
        mRecycler = findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().usersfollow(LoginInfoUtil.getUid(),LoginInfoUtil.getToken())).subscribe(new BaseObserver<List<MyFocusonBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MyFocusonBean> myFocusonBeans) throws IOException {
                MyFocusonAdapter adapter = new MyFocusonAdapter(R.layout.item_focuson, myFocusonBeans);
                adapter.openLoadAnimation();
                mRecycler.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        MallShoppingHomeActivity.start(myFocusonBeans.get(position).getId());
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
