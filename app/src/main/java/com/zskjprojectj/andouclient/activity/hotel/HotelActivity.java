package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.BarUtils;

/**
 * 酒店预约
 *  bin
 *  2019/12/5
 */
public class HotelActivity extends BaseActivity {

private RecyclerView mRvRecycler;
private LinearLayout mTitle;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel);

    }

    @Override
    protected void initData(Bundle savedInstanceState) {
//        topView.setTitle("酒店住宿");
    }

    @Override
    protected void initViews() {
    mTitle=findViewById(R.id.titlt_view);
        //设置状态栏的高度
        RelativeLayout.LayoutParams layoutParams= (RelativeLayout.LayoutParams) mTitle.getLayoutParams();
        layoutParams.topMargin= BarUtils.getStatusBarHeight(this);
        mTitle.setLayoutParams(layoutParams);
//        mRvRecycler=findViewById(R.id.rv_recycler);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
