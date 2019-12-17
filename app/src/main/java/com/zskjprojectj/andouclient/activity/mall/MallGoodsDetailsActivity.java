package com.zskjprojectj.andouclient.activity.mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;
import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailCommentFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailFacilityFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailMerchantFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailReserveFragment;
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsCommentFragment;
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsDetailFragment;
import com.zskjprojectj.andouclient.utils.DensityUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MallGoodsDetailsActivity extends BaseActivity {

    @BindView(R.id.tv_buy_now)
    TextView mBuyNow;

    @BindView(R.id.rv_shop_home)
    RelativeLayout mShopHome;

    @BindView(R.id.bt_mall_goods_discount)
    Button mDiscount;

    @BindView(R.id.bannertop)
    XBanner mBanner;

    @BindView(R.id.root_view)
    RelativeLayout mRootView;




    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;
    private List<Fragment> list = new ArrayList<>();
    private Dialog bottomDialog;
    private View contentView;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_goods_details);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initLocalImage();

        //商品详情
        list.add(new MallGoodsDetailFragment());
        //商品评论
        list.add(new MallGoodsCommentFragment());


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
     * 加载本地图片
     */
    private void initLocalImage() {
        List<LocalImageInfo> data = new ArrayList<>();
        data.add(new LocalImageInfo(R.drawable.home_mall_pic));
        data.add(new LocalImageInfo(R.drawable.home_hotel_pic));
        data.add(new LocalImageInfo(R.drawable.banner_placeholder));
        data.add(new LocalImageInfo(R.drawable.banner_placeholder));
        mBanner.setBannerData(data);

        //加载图片
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
//                加载本地图片展示
                ((ImageView) view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        mBanner.startAutoPlay();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mBanner.stopAutoPlay();
    }





    /**
     * 指示器适配器对形象
     */
    public IndicatorViewPager.IndicatorFragmentPagerAdapter adapter = new IndicatorViewPager.IndicatorFragmentPagerAdapter(getSupportFragmentManager()) {
        private String[] tabNames = {"产品详情", "产品评价"};

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
        getBarDistance(mRootView);
        topView.setTitle("商品详情");
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.tv_buy_now, R.id.rv_shop_home,R.id.bt_mall_goods_discount,R.id.shared})
    public void clickButNow(View v) {
        switch (v.getId()) {

            case R.id.tv_buy_now:
                initBuyNow();
                break;

            case R.id.rv_shop_home:
                startActivity(new Intent(MallGoodsDetailsActivity.this,MallShoppingHomeActivity.class));
                break;

            case R.id.bt_mall_goods_discount:
                initDiscount();
                break;

            case R.id.shared:
                CustomViewDialog dialog=new CustomViewDialog(this,R.layout.activity_shared_dialog_view,
                        new int[]{R.id.cancle,R.id.weixin,R.id.friendcircle,R.id.qq,R.id.qqkongjian,R.id.weibo});
                dialog.setOnCenterItemClickListener(new CustomViewDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CustomViewDialog dialog, View view) {
                        switch (view.getId()){
                            case R.id.cancle:
                                dialog.dismiss();
                                break;
                            case R.id.weixin:
                                ToastUtil.showToast("微信");
                                break;
                            case R.id.friendcircle:
                                ToastUtil.showToast("朋友圈");
                                break;
                            case R.id.qq:
                                ToastUtil.showToast("QQ");
                                break;
                            case R.id.qqkongjian:
                                ToastUtil.showToast("QQ空间");
                                break;
                            case R.id.weibo:
                                ToastUtil.showToast("微博");
                                break;
                        }
                    }
                });
                dialog.show();
                break;
        }

    }

    private void initDiscount() {

        Dialog bottomDialog = new Dialog(this, R.style.BottomDialog);

        Window window = bottomDialog.getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);



        View contentView = LayoutInflater.from(this).inflate(R.layout.dialog_mall_goods_discount, null);
        bottomDialog.setContentView(contentView);
//        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) contentView.getLayoutParams();
//        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
//        params.bottomMargin = DensityUtil.dp2px(this, 8f);
//        contentView.setLayoutParams(params);
        ImageView mCancle = contentView.findViewById(R.id.iv_cancle);
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

    }

    private void initBuyNow() {

        bottomDialog = new Dialog(this, R.style.BottomDialog);


        Window window = bottomDialog.getWindow();
        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        // 设置宽度
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
        window.getDecorView().setBackgroundColor(Color.TRANSPARENT);

        contentView = LayoutInflater.from(this).inflate(R.layout.dialog_buy_now, null);

        TextView mBuyNow = contentView.findViewById(R.id.tv_buynow);
        mBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MallGoodsDetailsActivity.this,MallOnlineOrderActivity.class);
                startActivity(intent);
                bottomDialog.dismiss();
            }
        });

        ImageView mCancle = contentView.findViewById(R.id.iv_cancle);
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });
        //减
        Button mSub = contentView.findViewById(R.id.btn_sub);
        Button mAdd = contentView.findViewById(R.id.btn_add);
        TextView mNum = contentView.findViewById(R.id.tv_num);

        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mNum.getText().toString();
                int i = Integer.parseInt(number);
                i+=1;
                String addNumber = Integer.toString(i);
                mNum.setText(addNumber);
            }
        });

        mSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mNum.getText().toString();
                int i = Integer.parseInt(number);
                if (i>1){
                    i-=1;
                    String addNumber = Integer.toString(i);
                    mNum.setText(addNumber);
                }
            }
        });


        bottomDialog.setContentView(contentView);
//        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        contentView.setLayoutParams(layoutParams);
        bottomDialog.getWindow().setGravity(Gravity.BOTTOM);
        bottomDialog.setCanceledOnTouchOutside(true);
        bottomDialog.getWindow().setWindowAnimations(R.style.BottomDialog_Animation);
        bottomDialog.show();

    }

}
