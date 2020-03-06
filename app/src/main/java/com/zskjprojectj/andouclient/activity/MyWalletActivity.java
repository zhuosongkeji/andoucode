package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.AccountChangeListFragment;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.BalanceDetail;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 我的钱包
 */
public class MyWalletActivity extends BaseActivity {

    private FixedIndicatorView indicator;
    //碎片集合
    private List<Fragment> list;
    private ViewPager viewPager;
    //第三方指示器
    private IndicatorViewPager indicatorViewPager;
    private Button balanceofprepaid, btn_withdrawal;
    AccountChangeListFragment meAccountChangeListFragment = new AccountChangeListFragment(0);
    AccountChangeListFragment meCashFragment = new AccountChangeListFragment(1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "我的钱包");
        balanceofprepaid = findViewById(R.id.btn_balanceofprepaid);
        btn_withdrawal = findViewById(R.id.btn_withdrawal);
        //设置点击事件
        balanceofprepaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(mActivity, BalanceofprepaidActivity.class);
            }
        });
        //余额提现
        btn_withdrawal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(mActivity, WithdrawalActivity.class);
            }
        });
        //这个FixedindicatorView是平分tab的屏幕长度的
        indicator = findViewById(R.id.indicator);
        viewPager = findViewById(R.id.viewPager);
        list = new ArrayList<Fragment>();
        list.add(meAccountChangeListFragment);
        list.add(meCashFragment);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);
        //设置滑动时的那一项的图形和颜色变化，ColorBar对应的是下划线的形状。
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#5ed3ae"), 5));
        viewPager.setOffscreenPageLimit(1);//缓存的左右页面的个数都是1
        HttpRxObservable.getObservable(ApiUtils.getApiService().cashDetail(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                1
        )).subscribe(new BaseObserver<BalanceDetail>(mActivity) {
            @Override
            public void onHandleSuccess(BalanceDetail balanceDetail) throws IOException {
                ((TextView) findViewById(R.id.tv_balanceofnum)).setText("¥" + balanceDetail.money);
            }
        });
    }

    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()) {
        private String[] tabNames = {"消费明细", "提现明细"};

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

    @Override
    protected int getContentView() {
        return R.layout.activity_my_wallet;
    }
}
