package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.adapter.MycollectionAdapter;
import com.zskjprojectj.andouclient.entity.MycollectionBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

public class MycollectionActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "商品收藏");
        RecyclerView mRecycler = findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(mActivity));
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().usercollection(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken()),
                result -> {
                    MycollectionAdapter adapter = new MycollectionAdapter(R.layout.item_mycollection, result.data);
                    adapter.openLoadAnimation();
                    mRecycler.setAdapter(adapter);
                    adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                        @Override
                        public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                            //商品ID
                            MallGoodsDetailsActivity.Companion.start(result.data.get(position).getId(), null, null);
                        }
                    });
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mycollection;
    }
}
