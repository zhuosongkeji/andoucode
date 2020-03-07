package com.zskjprojectj.andouclient.activity.hotel;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailCommentFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailFacilityFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailMerchantFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailReserveFragment;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.MapUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

public class HotelDetailActivity extends BaseActivity {

    @BindView(R.id.simpleRatingBar)
    ScaleRatingBar mSimpleRatingBar;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.fixContainer)
    View fixContainer;
    private ViewPager mViewPager;
    private HotelDetailReserveFragment hotelDetailReserveFragment = new HotelDetailReserveFragment();
    @BindView(R.id.tv_collectTxtfocuson)
    TextView mtvMallMerchantsFocuson;
    @BindView(R.id.img_hotelfocuson)
    ImageView ivisfocuson;
    private boolean isfocuson = false;
    private String type;
    private String merchantId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.transparentStatusBar(mActivity);
        BarUtils.setStatusBarLightMode(mActivity, false);
        ActionBarUtil.setTitle(mActivity, "商家详情", false);
        ActionBarUtil.getBackground(mActivity, false).setAlpha(0);
        ((AppBarLayout) findViewById(R.id.appBarLayout))
                .addOnOffsetChangedListener((appBarLayout, verticalOffset) ->
                        ActionBarUtil.getBackground(mActivity, false)
                                .setAlpha(Math.abs(verticalOffset) * 0.01f));
        fixContainer.setPadding(fixContainer.getPaddingLeft(), BarUtils.getStatusBarHeight(), fixContainer.getPaddingEnd(), 0);
        mViewPager = findViewById(R.id.viewPager);
        merchantId = getIntent().getStringExtra("merchantId");
        RequestUtil.request(mActivity, true, true,
                () -> ApiUtils.getApiService().hotelDetails(
                        LoginInfoUtil.getUid(),
                        merchantId),
                result -> {
                    findViewById(R.id.locationBtn).setOnClickListener(v -> {
                        MapUtil.start(result.data.getAddress(), mActivity);
                    });
                    Glide.with(mActivity).load(UrlUtil.INSTANCE.getImageUrl(result.data.getDoor_img()))
                            .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                            .into((ImageView) findViewById(R.id.iv_hotel_details_img));
                    ((TextView) findViewById(R.id.hotel_name)).setText(result.data.getName());
                    ((TextView) findViewById(R.id.busiess_dianzancount1_textview)).setText(result.data.getPraise_num());
                    ((TextView) findViewById(R.id.mobileTxt)).setText(result.data.getTel());
                    ((TextView) findViewById(R.id.addressTxt)).setText(result.data.getAddress());
                    String stars_all = result.data.getStars_all();
                    float aFloat = Float.parseFloat(stars_all);
                    mSimpleRatingBar.setRating(aFloat);
                    if ("0".equals(result.data.getStatus())) {
                        mtvMallMerchantsFocuson.setText("关注");
                        isfocuson = false;
                        ivisfocuson.setImageResource(R.mipmap.ic_restaurant_detail_collect);
                    } else {
                        mtvMallMerchantsFocuson.setText("已关注");
                        isfocuson = true;
                        ivisfocuson.setImageResource(R.mipmap.ic_heart_mall);
                    }
                    String hotelMerchantId = result.data.getId();
                    ArrayList<Fragment> list = new ArrayList<>();
                    Bundle bundle = new Bundle();
                    bundle.putString("hotelMerchantId", hotelMerchantId);
                    bundle.putString("desc", result.data.getDesc());
                    bundle.putSerializable("facilities", result.data.getFacilities());
                    hotelDetailReserveFragment.setArguments(bundle);
                    list.add(hotelDetailReserveFragment);
                    HotelDetailCommentFragment hotelDetailCommentFragment = new HotelDetailCommentFragment();
                    hotelDetailCommentFragment.setArguments(bundle);
                    list.add(hotelDetailCommentFragment);
                    HotelDetailMerchantFragment hotelDetailMerchantFragment = new HotelDetailMerchantFragment();
                    hotelDetailMerchantFragment.setArguments(bundle);
                    list.add(hotelDetailMerchantFragment);
                    HotelDetailFacilityFragment hotelDetailFacilityFragment = new HotelDetailFacilityFragment();
                    hotelDetailFacilityFragment.setArguments(bundle);
                    list.add(hotelDetailFacilityFragment);
                    tabLayout.setViewPager(mViewPager, new String[]{"预订", "评论", "商家", "环境设施"}, mActivity, list);
                    mViewPager.setOffscreenPageLimit(4);
                });
    }

    @OnClick({R.id.collectBtn})
    public void clickView(View view) {
        if (view.getId() == R.id.collectBtn) {
            if (!isfocuson) {
                mtvMallMerchantsFocuson.setText("已关注");
                isfocuson = true;
                ivisfocuson.setImageResource(R.mipmap.ic_heart_mall);
                type = "1";
            } else {
                mtvMallMerchantsFocuson.setText("关注");
                ivisfocuson.setImageResource(R.mipmap.ic_restaurant_detail_collect);
                type = "0";
                isfocuson = false;
            }
            RequestUtil.request(mActivity, true, false,
                    () -> ApiUtils.getApiService().mallgoodsfollow(
                            merchantId,
                            LoginInfoUtil.getUid(),
                            LoginInfoUtil.getToken(),
                            type
                    ),
                    result -> {
                    });
        }
    }

    public static void start(String merchantId) {
        Bundle bundle = new Bundle();
        bundle.putString("merchantId", merchantId);
        ActivityUtils.startActivity(bundle, HotelDetailActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_hotel_detail;
    }
}
