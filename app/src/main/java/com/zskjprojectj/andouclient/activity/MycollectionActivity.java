package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.adapter.MycollectionAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.MycollectionBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MycollectionActivity extends BaseActivity {

    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;

    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;

    private RecyclerView mRecycler;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mycollection);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void initViews() {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("商品收藏");
        mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().usercollection(LoginInfoUtil.getUid(),LoginInfoUtil.getToken())).subscribe(new BaseObserver<List<MycollectionBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MycollectionBean> mycollectionBeans) throws IOException {
                MycollectionAdapter adapter = new MycollectionAdapter(R.layout.item_mycollection, mycollectionBeans);
                adapter.openLoadAnimation();
                mRecycler.setAdapter(adapter);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                        //商品ID
                        MallGoodsDetailsActivity.start( mycollectionBeans.get(position).getId(),"PUTONG");
                    }
                });
            }
        });

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void onResume() {
        super.onResume();
        getDataFromServer();
    }

    @OnClick(R.id.mHeaderBack)
    public void clickView() {
        finish();
    }
}
