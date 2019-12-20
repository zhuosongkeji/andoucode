package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class TieBaFragment extends BaseFragment {
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;


    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mHeaderTitle.setText("贴吧");
        getBarDistance(mHeaderTitleView);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_tiebapage;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

    }

    @OnClick(R.id.iv_header_back)
    public void clickBack(){
        mAty.finish();
    }
}
