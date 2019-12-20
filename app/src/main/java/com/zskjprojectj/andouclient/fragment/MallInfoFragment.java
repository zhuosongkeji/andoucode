package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.view.TopView;

import butterknife.BindView;


/**
 * 在线商城信息
 */
public class MallInfoFragment extends BaseFragment {
    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @BindView(R.id.iv_header_back)
    ImageView mHeaderBack;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mHeaderTitle.setText("会员中心");
        mHeaderBack.setVisibility(View.GONE);
        getBarDistance(mHeaderTitleView);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mallinfo;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

    }
}
