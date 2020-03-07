package com.zskjprojectj.andouclient.activity.restaurant;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.model.RestaurantCategory;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zskjprojectj.andouclient.utils.ConstantKt.KEY_DATA;


public class RestaurantSearchActivity extends BaseActivity {

    RestaurantAdapter adapter = new RestaurantAdapter();

    @BindView(R.id.searchEdt)
    EditText searchEdt;
    @BindView(R.id.categoryNameTxt)
    TextView categoryNameTxt;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.setStatusBarLightMode(mActivity, true);
        BarUtils.transparentStatusBar(mActivity);
        RestaurantCategory category = (RestaurantCategory) getIntent().getSerializableExtra(KEY_DATA);
        searchEdt.requestFocus();
        adapter.setOnItemClickListener((adapter1, view, position)
                -> RestaurantDetailActivity.start(adapter.getItem(position).id));
        PageLoadUtil<Restaurant> pageLoadUtil = PageLoadUtil.get(mActivity,
                findViewById(R.id.recyclerView),
                adapter,
                refreshLayout);
        searchEdt.setOnEditorActionListener((v, actionId, event) -> {
            String keyword = searchEdt.getText().toString();
            if (TextUtils.isEmpty(keyword)) {
                ToastUtils.showShort("请输入关键字搜索!");
                return true;
            }
            load(null, pageLoadUtil);
            return true;
        });
        refreshLayout.setEnableRefresh(false);
        if (category != null) {
            ViewUtil.setText(mActivity, R.id.categoryNameTxt, category.name);
            load(category.id, pageLoadUtil);
        } else {
            KeyboardUtils.showSoftInput(searchEdt);
        }
    }

    private void load(String categoryId, PageLoadUtil<Restaurant> pageLoadUtil) {
        pageLoadUtil.load(() -> ApiUtils.getApiService().getRestaurants(
                searchEdt.getText().toString(),
                categoryId,
                null,
                pageLoadUtil.page));
    }

    @OnClick(R.id.backBtn)
    void onBackBtnClick() {
        onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        KeyboardUtils.hideSoftInput(mActivity);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_restaurant_search;
    }


    public static void start(RestaurantCategory category) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY_DATA, category);
        ActivityUtils.startActivity(bundle, RestaurantSearchActivity.class);
    }
}
