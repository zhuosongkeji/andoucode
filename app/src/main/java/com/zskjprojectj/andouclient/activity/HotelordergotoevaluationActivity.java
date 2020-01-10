package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 酒店订单去评价界面
 */
public class HotelordergotoevaluationActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    private String merchant_id;
    private String id;
    private String book_sn;


    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelordergotoevaluation);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mHeaderTitle.setText("发布评论");
        getBarDistance(mTitleView);
    }

    @Override
    protected void initViews() {
        merchant_id = getIntent().getStringExtra("merchant_id");
        id = getIntent().getStringExtra("id");
        book_sn = getIntent().getStringExtra("book_sn");

    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void start(String merchant_id, String id, String book_sn) {

        Bundle bundle = new Bundle();
        bundle.putString("merchant_id", merchant_id);
        bundle.putString("id", id);
        bundle.putString("book_sn", book_sn);
        ActivityUtils.startActivity(bundle, HotelordergotoevaluationActivity.class);
    }
}
