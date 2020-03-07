package com.zskjprojectj.andouclient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MymessageAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

/**
 * 我的消息
 */
public class MymessageActivity extends BaseActivity {

    MymessageAdapter adapter = new MymessageAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "我的消息");
        RecyclerView mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
        mRecycler.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapters, View view, int position) {
                Intent intent = new Intent();
                intent.putExtra("id", adapter.getItem(position).getId());
                intent.setClass(mActivity, MessageinfoActivity.class);
                startActivity(intent);
            }
        });
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().notificationcenter(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken()),
                result -> {
                    adapter.setNewData(result.data);
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mymessage;
    }
}
