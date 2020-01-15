package com.zskjprojectj.andouclient.activity.hotel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.willy.ratingbar.ScaleRatingBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailsBean;
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

    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar mSimpleRatingBar;

    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;
    private List<Fragment> list = new ArrayList<>();
    private HotelDetailReserveFragment hotelDetailReserveFragment = new HotelDetailReserveFragment();

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_hotel_detail);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

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


        //商家ID
        String merchantId = getIntent().getStringExtra("merchantId");

        Log.d(TAG, "initViews:===== "+merchantId);


        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelDetails(merchantId))
                .subscribe(new BaseObserver<HotelDetailsBean>(mAt) {
                    @Override
                    public void onHandleSuccess(HotelDetailsBean hotelDetailsBean) throws IOException {

                        //背景图片
                        Glide.with(mAt).load(UrlUtil.getImageUrl(hotelDetailsBean.getDoor_img()))
                                .apply(new RequestOptions().placeholder(R.drawable.default_image).error(R.drawable.default_image))
                                .into((ImageView) findViewById(R.id.iv_hotel_details_img));
                        //酒店名字
                        ((TextView) findViewById(R.id.hotel_name)).setText(hotelDetailsBean.getName());
                        //点赞数
                        ((TextView) findViewById(R.id.busiess_dianzancount1_textview)).setText(hotelDetailsBean.getPraise_num());
                        //电话
                        ((TextView) findViewById(R.id.mobileTxt)).setText(hotelDetailsBean.getTel());
                        //地址
                        ((TextView) findViewById(R.id.addressTxt)).setText(hotelDetailsBean.getAddress());

                        String stars_all = hotelDetailsBean.getStars_all();
                        float aFloat = Float.parseFloat(stars_all);
                        mSimpleRatingBar.setRating(aFloat);


                        String hotelMerchantId = hotelDetailsBean.getId();

                        Bundle bundle = new Bundle();
                        bundle.putString("hotelMerchantId", hotelMerchantId);
                        bundle.putString("desc",hotelDetailsBean.getDesc());
                        hotelDetailReserveFragment.setArguments(bundle);
                        //预订
                        list.add(hotelDetailReserveFragment);
                        //评论
                        HotelDetailCommentFragment hotelDetailCommentFragment = new HotelDetailCommentFragment();
                        hotelDetailCommentFragment.setArguments(bundle);
                        list.add(hotelDetailCommentFragment);
                        //商家
                        HotelDetailMerchantFragment hotelDetailMerchantFragment=new HotelDetailMerchantFragment();
                        hotelDetailMerchantFragment.setArguments(bundle);
                        list.add(hotelDetailMerchantFragment);
                        //环境设施
                        list.add(new HotelDetailFacilityFragment());

                        IndicatorViewPager indicatorViewPager = new IndicatorViewPager(mIndicator, mViewPager);
                        indicatorViewPager.setAdapter(adapter);


                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });

    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @OnClick(R.id.busiess_back_image)
    public void clickBack() {
        finish();
    }

    public static void start(String merchantId) {

        Bundle bundle = new Bundle();
        bundle.putString("merchantId", merchantId);
        ActivityUtils.startActivity(bundle, HotelDetailActivity.class);

    }
}
