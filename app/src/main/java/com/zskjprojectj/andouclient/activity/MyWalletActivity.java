package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.AccountChangeListFragment;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.util.ArrayList;

public class MyWalletActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "我的钱包");
        Button balanceofprepaid = findViewById(R.id.btn_balanceofprepaid);
        Button btn_withdrawal = findViewById(R.id.btn_withdrawal);
        balanceofprepaid.setOnClickListener(view -> ActivityUtils.startActivity(mActivity, BalanceofprepaidActivity.class));
        btn_withdrawal.setOnClickListener(view -> ActivityUtils.startActivity(mActivity, WithdrawalActivity.class));
        SlidingTabLayout tabLayout = findViewById(R.id.tabLayout);
        //碎片集合
        ViewPager viewPager = findViewById(R.id.viewPager);
        ArrayList<Fragment> list = new ArrayList<>();
        AccountChangeListFragment meAccountChangeListFragment = new AccountChangeListFragment(0);
        AccountChangeListFragment meCashFragment = new AccountChangeListFragment(1);
        list.add(meAccountChangeListFragment);
        list.add(meCashFragment);
        tabLayout.setViewPager(viewPager, new String[]{"消费明细", "提现明细"}, mActivity, list);
        viewPager.setOffscreenPageLimit(2);
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().cashDetail(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        1
                ),
                result -> {
                    ((TextView) findViewById(R.id.tv_balanceofnum)).setText("¥" + result.data.money);
                });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_my_wallet;
    }
}
