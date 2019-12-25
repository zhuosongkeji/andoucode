package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Merchant;
import com.zskjprojectj.andouclient.utils.BarUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

/**
 * 酒店详情页
 * bin
 * 2019/12/6
 */

public class HotelDetailActivity extends BaseActivity {

    @BindView(R.id.titlt_view)
    LinearLayout mTitleView;

    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;
    private List<Fragment> list = new ArrayList<>();
    HotelDetailReserveFragment hotelDetailReserveFragment = new HotelDetailReserveFragment();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_detail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        //预订
        list.add(hotelDetailReserveFragment);
        //评论
        list.add(new HotelDetailCommentFragment());
        //商家
        list.add(new HotelDetailMerchantFragment());
        //环境设施
        list.add(new HotelDetailFacilityFragment());

        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
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
    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()) {
        private String[] tabNames = {"预订", "评论", "商家", "环境设施"};

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

        //设置状态栏的高度
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) mTitleView.getLayoutParams();
        layoutParams.topMargin = BarUtils.getStatusBarHeight(this);
        mTitleView.setLayoutParams(layoutParams);

        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);

    }

    @Override
    public void getDataFromServer() {
        Merchant merchant = (Merchant) getIntent().getSerializableExtra(KEY_DATA);
        HttpRxObservable.getObservable(ApiUtils.getApiService().merchantDetail(merchant.id))
                .subscribe(new BaseObserver<Merchant>(mAt) {
                    @Override
                    public void onHandleSuccess(Merchant merchant) throws IOException {
                        Glide.with(mAt).load(merchant.banner_img)
                                .into((ImageView) findViewById(R.id.busiess_back_image));
                        ((TextView) findViewById(R.id.hotel_name)).setText(merchant.name);
                        ((TextView) findViewById(R.id.busiess_dianzancount1_textview)).setText(merchant.praise_num + "");
                        ((TextView) findViewById(R.id.mobileTxt)).setText(merchant.tel);
                        ((TextView) findViewById(R.id.addressTxt)).setText(merchant.address);
                    }
                });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.busiess_back_image)
    public void clickBack() {
        finish();
    }
}
