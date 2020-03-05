package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.MeAllordersFragment;
import com.zskjprojectj.andouclient.fragment.MeCancelledFragment;
import com.zskjprojectj.andouclient.fragment.MeToEvaluateFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜市场模块
 */
public class VegetableMarketActivity extends BaseActivity {
    private FixedIndicatorView indicator;
    //碎片集合
    private List<Fragment> list;
    private ViewPager viewPager;
    //第三方指示器
    private IndicatorViewPager indicatorViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "菜市场订单");
        //这个FixedindicatorView是平分tab的屏幕长度的
        indicator = (FixedIndicatorView) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        list = new ArrayList<Fragment>();
        Fragment meAllordersFragment = new MeAllordersFragment();
        list.add(meAllordersFragment);
        Fragment meToEvaluateFragment = new MeToEvaluateFragment();
        list.add(meToEvaluateFragment);
        Fragment meCancelledFragment = new MeCancelledFragment();
        list.add(meCancelledFragment);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);
        //设置滑动时的那一项的图形和颜色变化，ColorBar对应的是下划线的形状。
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#00B2EE"), 5));
        viewPager.setOffscreenPageLimit(1);//缓存的左右页面的个数都是1
    }


    /**
     * 指示器适配器对形象
     */
    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()) {
        private String[] tabNames = {"全部订单", "待评价", "已取消"};

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
        return R.layout.me_vegetable_market;
    }
}
