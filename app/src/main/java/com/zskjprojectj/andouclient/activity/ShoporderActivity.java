package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;


import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.MeShopFragment;
import com.zskjprojectj.andouclient.fragment.MeShopforthegoodsFragment;
import com.zskjprojectj.andouclient.fragment.MeShoppaymentFragment;
import com.zskjprojectj.andouclient.fragment.MeShopreturngoodsFragment;
import com.zskjprojectj.andouclient.fragment.MeShopsendgoodsFragment;
import com.zskjprojectj.andouclient.fragment.MeShoptoevaluateFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 商城订单
 */
public class ShoporderActivity extends BaseActivity {




    private FixedIndicatorView indicator;
    //碎片集合
    private List<Fragment> list;
    private ViewPager viewPager;
    //第三方指示器
    private IndicatorViewPager indicatorViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "全部订单");
        initViews();
        initData();
    }

    private void initData() {
        String flag = getIntent().getStringExtra("flag");
        if ("MallPaySuccess".equals(flag)){
            viewPager.setCurrentItem(2, true);
        }
    }


    private void initViews() {
        //这个FixedindicatorView是平分tab的屏幕长度的
        indicator = (FixedIndicatorView) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        list = new ArrayList<Fragment>();
        Fragment meShopFragment1=new MeShopFragment("");
        Fragment meShopFragment2=new MeShopFragment("10");
        Fragment meShopFragment3=new MeShopFragment("20");
        Fragment meShopFragment4=new MeShopFragment("40");
        Fragment meShopFragment5=new MeShopFragment("50");
        list.add(meShopFragment1);
        list.add(meShopFragment2);
        list.add(meShopFragment3);
        list.add(meShopFragment4);
        list.add(meShopFragment5);
        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);
        //设置滑动时的那一项的图形和颜色变化，ColorBar对应的是下划线的形状。
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#5ed3ae"), 5));
        viewPager.setOffscreenPageLimit(5);//缓存的左右页面的个数都是
        viewPager.setCurrentItem(0, true);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        ActionBarUtil.setTitle(mActivity, "全部订单");
                        break;
                    case 1:
                        ActionBarUtil.setTitle(mActivity, "待付款");
                        break;
                    case 2:
                        ActionBarUtil.setTitle(mActivity, "待发货");
                        break;
                    case 3:
                        ActionBarUtil.setTitle(mActivity, "待收货");
                        break;
                    case 4:
                        ActionBarUtil.setTitle(mActivity, "待评价");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }


    /**
     * 指示器适配器对形象
     */
    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter=new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager())
    {
        private String[] tabNames = {"全部订单", "待付款","待发货","待收货","待评价"};
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
        return R.layout.activity_shoporder;
    }
}
