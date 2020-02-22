package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MymessageAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.MymessageBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的消息
 */
public class MymessageActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    private RecyclerView mRecycler;
    MymessageAdapter adapter=new MymessageAdapter();
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mymessage);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("我的消息");
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(mAt,DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapters, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("id",adapter.getItem(position).getId());
                intent.setClass(mAt,MessageinfoActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().notificationcenter(LoginInfoUtil.getUid(),LoginInfoUtil.getToken())).subscribe(new BaseObserver<List<MymessageBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MymessageBean> mymessageBeans) throws IOException {
                adapter.setNewData(mymessageBeans);
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

    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }
}
