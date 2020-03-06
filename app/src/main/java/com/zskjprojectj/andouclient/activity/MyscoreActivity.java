package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MyscoreAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.IntegralDetail;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;

/**
 * 我的积分
 */
public class MyscoreActivity extends BaseActivity {

    MyscoreAdapter adapter = new MyscoreAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "我的感恩币");
        RecyclerView mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter.openLoadAnimation();
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        mRecycler.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().integralDetail(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken()
                ),
                result -> {
                    ((TextView) findViewById(R.id.tv_jfnum)).setText("¥" + result.data.integral);
                    adapter.setNewData(result.data.log);
                });

    }

    @Override
    protected int getContentView() {
        return R.layout.activity_myscore;
    }
}
