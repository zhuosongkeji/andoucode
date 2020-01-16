package com.zskjprojectj.andouclient.activity.restaurant;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zhuosongkj.android.library.model.ListData;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import mlxy.utils.S;

public class RestaurantSearchActivity extends BaseActivity {

    RestaurantAdapter adapter = new RestaurantAdapter();

    @BindView(R.id.searchEdt)
    EditText searchEdt;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarLightMode(mActivity, true);
        BarUtils.transparentStatusBar(mActivity);
        searchEdt.requestFocus();
        KeyboardUtils.showSoftInput(searchEdt);
        adapter.setOnItemClickListener((adapter1, view, position)
                -> RestaurantDetailActivity.start(adapter.getItem(position).id));
        PageLoadUtil<Restaurant> pageLoadUtil = PageLoadUtil.get(mActivity,
                findViewById(R.id.recyclerView),
                adapter,
                refreshLayout);
        searchEdt.setOnEditorActionListener((v, actionId, event) -> {
            String keyword = searchEdt.getText().toString();
            if (TextUtils.isEmpty(keyword)) {
                ToastUtil.showToast("请输入关键字搜索!");
                return true;
            }
            pageLoadUtil.load(() -> ApiUtils.getApiService().getRestaurants(searchEdt.getText().toString(),
                    pageLoadUtil.page));
            return true;
        });
        refreshLayout.setEnableRefresh(false);
    }

    @OnClick(R.id.backBtn)
    void onBackBtnClick() {
        onBackPressed();
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_search;
    }
}
