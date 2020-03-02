package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.BalancesubsidiaryFragment;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.BalanceDetail;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 我的钱包
 */
public class MywalletActivity extends BaseActivity {

    @BindView(R.id.mTitleView)
    RelativeLayout mTitleView;

    @BindView(R.id.mHeaderTitle)
    TextView mHeaderTitle;

    private FixedIndicatorView indicator;
    //碎片集合
    private List<Fragment> list;
    private ViewPager viewPager;
    //第三方指示器
    private IndicatorViewPager indicatorViewPager;
    private Button balanceofprepaid, btn_withdrawal;
    BalancesubsidiaryFragment meBalancesubsidiaryFragment = new BalancesubsidiaryFragment();
    BalancesubsidiaryFragment meCashFragment = new BalancesubsidiaryFragment();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mywallet);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        getBarDistance(mTitleView);
        mHeaderTitle.setText("我的钱包");
    }

    @Override
    protected void initViews() {
        balanceofprepaid = findViewById(R.id.btn_balanceofprepaid);
        btn_withdrawal = findViewById(R.id.btn_withdrawal);
        EventBus.getDefault().register(this);
        //设置点击事件
        balanceofprepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(BalanceofprepaidActivity.class);

                // finish();
            }
        });
        //余额提现
        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(WithdrawalActivity.class);
                // finish();
            }
        });
        //这个FixedindicatorView是平分tab的屏幕长度的
        indicator = (FixedIndicatorView) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        list = new ArrayList<Fragment>();
        list.add(meBalancesubsidiaryFragment);
        list.add(meCashFragment);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);
        //设置滑动时的那一项的图形和颜色变化，ColorBar对应的是下划线的形状。
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#5ed3ae"), 5));
        viewPager.setOffscreenPageLimit(1);//缓存的左右页面的个数都是1
    }

    @Override
    public void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().balanceDetail(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken()
        )).subscribe(new BaseObserver<BalanceDetail>(mAt) {
            @Override
            public void onHandleSuccess(BalanceDetail balanceDetail) throws IOException {
                ((TextView) findViewById(R.id.tv_balanceofnum)).setText("¥" + balanceDetail.money);
                meBalancesubsidiaryFragment.addData(balanceDetail.log, true);
            }
        });

        HttpRxObservable.getObservable(ApiUtils.getApiService().cashDetail(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken()
        )).subscribe(new BaseObserver<BalanceDetail>(mAt) {
            @Override
            public void onHandleSuccess(BalanceDetail balanceDetail) throws IOException {
                ((TextView) findViewById(R.id.tv_balanceofnum)).setText("¥" + balanceDetail.money);
                meCashFragment.addData(balanceDetail.log, true);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 指示器适配器对形象
     */
    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()) {
        private String[] tabNames = {"余额明细", "提现明细"};

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            //此方法设置的tab的页面和显示
            if (convertView == null) {
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.tab,
                        container, false);
            }
            TextView tv = (TextView) convertView;
            tv.setText(tabNames[position]);
            return convertView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            //设置viewpager下的页面
            Fragment fragment = list.get(position);
            return fragment;
        }
    };

    @OnClick(R.id.mHeaderBack)
    public void clickView() {
        finish();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void initnewData(PaySuccessEvent paySuccessEvent) {
         getDataFromServer();

    }
}
