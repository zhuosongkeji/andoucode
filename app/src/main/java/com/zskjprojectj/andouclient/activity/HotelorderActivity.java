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
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.MeHotelorderFragment;
import com.zskjprojectj.andouclient.fragment.MeHotelordercancelledFragment;
import com.zskjprojectj.andouclient.fragment.MeHotelorderevaluateFragment;
import com.zskjprojectj.andouclient.fragment.MeHotelorderstayinFragment;
import com.zskjprojectj.andouclient.fragment.hotel.MeHotelFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 酒店预订
 */
public class HotelorderActivity extends BaseActivity {

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;

    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    private FixedIndicatorView indicator;
    //碎片集合
    private List<Fragment> list;
    private ViewPager viewPager;
    //第三方指示器
    private IndicatorViewPager indicatorViewPager;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotelorder);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getBarDistance(mTitleView);
        mHeaderTitle.setText("酒店预订");
    }

    @Override
    protected void initViews() {
        //这个FixedindicatorView是平分tab的屏幕长度的
        indicator = (FixedIndicatorView) findViewById(R.id.indicator);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        list = new ArrayList<Fragment>();
//        //全部订单
//        Fragment meHotelorderFragment = new MeHotelorderFragment();
//        list.add(meHotelorderFragment);
//        //待入驻
//        Fragment meHotelorderstayinFragment = new MeHotelorderstayinFragment();
//        list.add(meHotelorderstayinFragment);
//        //待评价
//        Fragment meHotelorderevaluateFragment = new MeHotelorderevaluateFragment();
//        list.add(meHotelorderevaluateFragment);
//        //已取消
//        Fragment meHotelordercancelledFragment = new MeHotelordercancelledFragment();
//        list.add(meHotelordercancelledFragment);

        Fragment meHotelFragment1 = new MeHotelFragment("");
        Fragment meHotelFragment2 = new MeHotelFragment("20");
        Fragment meHotelFragment3 = new MeHotelFragment("40");
        Fragment meHotelFragment4 = new MeHotelFragment("0");

        list.add(meHotelFragment1);
        list.add(meHotelFragment2);
        list.add(meHotelFragment3);
        list.add(meHotelFragment4);

        indicatorViewPager = new IndicatorViewPager(indicator, viewPager);
        indicatorViewPager.setAdapter(adapter);
        //设置滑动时的那一项的图形和颜色变化，ColorBar对应的是下划线的形状。
        indicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#5ed3ae"), 5));
        viewPager.setOffscreenPageLimit(4);//缓存的左右页面的个数都是1
        viewPager.setCurrentItem(0, true);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    /**
     * 指示器适配器对形象
     */
    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()) {
        private String[] tabNames = {"全部订单", "待入驻", "待评价", "已取消"};

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


    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }
}
