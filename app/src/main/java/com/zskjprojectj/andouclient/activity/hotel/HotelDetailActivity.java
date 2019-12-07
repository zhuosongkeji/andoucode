package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailCommentFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailFacilityFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailMerchantFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailReserveFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 酒店详情页
 * bin
 * 2019/12/6
 */

public class HotelDetailActivity extends BaseActivity {

    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;
    private List<Fragment> list=new ArrayList<>();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_detail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        //预订
        list.add(new HotelDetailReserveFragment());
        //评论
        list.add(new HotelDetailCommentFragment());
        //商家
        list.add(new HotelDetailMerchantFragment());
        //环境设施
        list.add(new HotelDetailFacilityFragment());

        IndicatorViewPager indicatorViewPager=new IndicatorViewPager(mIndicator,mViewPager);
        indicatorViewPager.setAdapter(adapter);
        //设置滑动时的那一项的图形和颜色变化，ColorBar对应的是下划线的形状。
        mIndicator.setScrollBar(new ColorBar(getApplicationContext(), Color.parseColor("#5ED3AE"), 5));
        mViewPager.setOffscreenPageLimit(1);//缓存的左右页面的个数都是1

        float unSelectSize = 16;//Title的文字大小
        int selectColor = getResources().getColor(R.color.green_bg);//当前显示的Title颜色
        int unSelectColor = getResources().getColor(R.color.black_bg);//未显示的Title颜色
        mIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(selectColor, unSelectColor).setSize(unSelectSize, unSelectSize));


    }

    /**
     * 指示器适配器对形象
     */
    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter=new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()){
        private String[] tabNames = {"预订", "评论","商家","环境设施"};
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
    protected void initViews() {

        mIndicator=findViewById(R.id.indicator);
        mViewPager=findViewById(R.id.viewPager);

    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
