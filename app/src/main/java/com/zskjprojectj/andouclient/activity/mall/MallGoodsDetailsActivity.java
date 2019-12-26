package com.zskjprojectj.andouclient.activity.mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.FontsContract;
import android.util.Log;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;


import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MallMainActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallBuyAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.TuchongEntity;
import com.zskjprojectj.andouclient.entity.XBannerBean;
import com.zskjprojectj.andouclient.entity.mall.MallBuyBean;
import com.zskjprojectj.andouclient.entity.mall.MallCommentBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsDetailsDataBean;
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailCommentFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailFacilityFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailMerchantFragment;
import com.zskjprojectj.andouclient.fragment.hotel.HotelDetailReserveFragment;
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsCommentFragment;
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsDetailFragment;
import com.zskjprojectj.andouclient.http.ApiException;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.DensityUtil;
import com.zskjprojectj.andouclient.utils.TestUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
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

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    //商品名称
    @BindView(R.id.tv_mall_goods_name)
    TextView mMallGoodsName;
    //商品价格
    @BindView(R.id.tv_price)
    TextView mTvPrice;
    //商品运费
    @BindView(R.id.tv_goods_dilivery)
    TextView mTvGoodsDilivery;
    //商品销量
    @BindView(R.id.tv_goods_volume)
    TextView mTvGoodsVolume;
    //商品库存
    @BindView(R.id.tv_goods_store_num)
    TextView mTvGoodsStoreNum;

    //商品收藏
    @BindView(R.id.tv_mall_goods_collection)
    TextView mtvMallGoodsCollection;

    //商家头像
    @BindView(R.id.iv_headPic)
    ImageView IvHeadPic;
    //商家名字
    @BindView(R.id.tv_name)
    TextView mTvName;

    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;
    private List<Fragment> list = new ArrayList<>();
    private Dialog bottomDialog;
    private View contentView;
    private int goodsId;
    private boolean isCollection=false;
    private String type;
    private String id;
    private String merchant_id;
    private ArrayList<MallBuyBean.SpecInfo> res;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_goods_details);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

        initLocalImage();

        //商品详情
        list.add(new MallGoodsDetailFragment(goodsId));
        //商品评论
        list.add(new MallGoodsCommentFragment(goodsId));


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

        //加载图片
        mBanner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
//                加载本地图片展示
                XBannerBean urlList = (XBannerBean) model;
                String url=BaseUrl.BASE_URL+urlList.getImageUrl();
                Glide.with(MallGoodsDetailsActivity.this).load(url).apply(new RequestOptions()
                        .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) view);
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
        //商品ID
        goodsId = getIntent().getIntExtra("id", 0);

        getBarDistance(mHeaderTitleView);
        mHeaderTitle.setText("商品详情");
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void getDataFromServer() {

        id = String.valueOf(goodsId);

        //商品详情展示
        HttpRxObservable.getObservable(ApiUtils.getApiService().mallDetailsShow(id,TestUtil.getUid()))
                .subscribe(new BaseObserver<MallGoodsDetailsDataBean>(this) {
                    @Override
                    public void onHandleSuccess(MallGoodsDetailsDataBean mallGoodsDetailsDataBean) throws IOException {
                        List<XBannerBean> urlBanner=new ArrayList<>();
                        urlBanner.clear();
                        //轮播图
                        String img = mallGoodsDetailsDataBean.getImg();
                        urlBanner.add(new XBannerBean(img));
                        List<String> album = mallGoodsDetailsDataBean.getAlbum();
                        if (album.size()!=0) {
                            for (String s : album) {
                                urlBanner.add(new XBannerBean(s));
                            }
                        }

                        mBanner.setBannerData(urlBanner);

                        mMallGoodsName.setText(mallGoodsDetailsDataBean.getName());
                        mTvPrice.setText(mallGoodsDetailsDataBean.getPrice());
                        mTvGoodsDilivery.setText(mallGoodsDetailsDataBean.getDilivery());
                        mTvGoodsVolume.setText(mallGoodsDetailsDataBean.getVolume());
                        mTvGoodsStoreNum.setText(mallGoodsDetailsDataBean.getStore_num());//商品库存

                        Glide.with(MallGoodsDetailsActivity.this).load(BaseUrl.BASE_URL+mallGoodsDetailsDataBean.getMerchant().getLogo_img())
                                .apply(new RequestOptions()
                                .placeholder(R.mipmap.ic_default_head_photo).error(R.mipmap.ic_default_head_photo))
                                .into(IvHeadPic);

                        mTvName.setText(mallGoodsDetailsDataBean.getMerchant().getName());

                        if ("0".equals(mallGoodsDetailsDataBean.getIs_collection())){
                            mtvMallGoodsCollection.setText("收藏");
                        }else {
                            mtvMallGoodsCollection.setText("已收藏");
                        }

                        merchant_id = mallGoodsDetailsDataBean.getMerchant_id();

                    }

                    @Override
                    public void onHandleError(ApiException apiExc) {
                        super.onHandleError(apiExc);
                    }
                });





    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }


    @OnClick({R.id.mall_goods_collection,R.id.tv_mall_home, R.id.tv_mall_shopping, R.id.tv_Mall_service, R.id.tv_buy_now, R.id.add_shopping, R.id.rv_shop_home, R.id.bt_mall_goods_discount, R.id.shared})
    public void clickButNow(View v) {
        switch (v.getId()) {

            case R.id.mall_goods_collection:
                if (!isCollection) {
                    mtvMallGoodsCollection.setText("已收藏");
                    isCollection=true;
                    type="1";
                }else {
                    mtvMallGoodsCollection.setText("收藏");
                    type="0";
                    isCollection=false;
                }

                Log.d(TAG, "type: "+type);
                HttpRxObservable.getObservable(ApiUtils.getApiService().mallGoodsCollection(
                        id,
                        TestUtil.getUid(),
                        TestUtil.getToken(),
                        type
                )).subscribe(new BaseObserver<Object>(this) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {

                    }
                });

                break;

            //购物车
            case R.id.tv_mall_shopping:
                Intent intent = new Intent(MallGoodsDetailsActivity.this, MallMainActivity.class);
                intent.putExtra("id", "MallShopping");
                startActivity(intent);
                break;
            //客服
            case R.id.tv_Mall_service:
                ToastUtil.showToast("客服");
                break;

            //立即购买
            case R.id.tv_buy_now:
                //加入购物车
            case R.id.add_shopping:

                HttpRxObservable.getObservable(ApiUtils.getApiService().buySpecification(id))
                        .subscribe(new BaseObserver<MallBuyBean>(mAt){
                            @Override
                            public void onHandleSuccess(MallBuyBean mallBuyBean) throws IOException {
//                                mallBuyBean.res.get(9).name
//                                mallBuyBean.res.get(9).value
                                //获取拼接选择之后的id，sum，price
//                                mallBuyBean.price.get("").
                                 res = mallBuyBean.res;

                            }
                        });



                initBuyNow();
                break;
            //店铺主页
            case R.id.tv_mall_home:
            case R.id.rv_shop_home:

                Intent shopHomeIntent=new Intent(MallGoodsDetailsActivity.this, MallShoppingHomeActivity.class);
                shopHomeIntent.putExtra("merchant_id",merchant_id);
                startActivity(shopHomeIntent);
                break;
            //优惠券
            case R.id.bt_mall_goods_discount:
                initDiscount();
                break;
            //分享
            case R.id.shared:
                CustomViewDialog dialog = new CustomViewDialog(this, R.layout.activity_shared_dialog_view,
                        new int[]{R.id.cancle, R.id.weixin, R.id.friendcircle, R.id.qq, R.id.qqkongjian, R.id.weibo});
                dialog.setOnCenterItemClickListener(new CustomViewDialog.OnCenterItemClickListener() {
                    @Override
                    public void OnCenterItemClick(CustomViewDialog dialog, View view) {
                        switch (view.getId()) {
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

        RecyclerView mBuyRecycler = contentView.findViewById(R.id.rv_buy_recyclerview);
        mBuyRecycler.setLayoutManager(new LinearLayoutManager(mBuyRecycler.getContext()));

        MallBuyAdapter buyAdapter=new MallBuyAdapter(R.layout.mall_buy_item,res);
        mBuyRecycler.setAdapter(buyAdapter);




        TextView mTvAddShopping = contentView.findViewById(R.id.tv_add_shopping);
        mTvAddShopping.setOnClickListener(v -> {
            HttpRxObservable.getObservable(ApiUtils.getApiService().addCar(
                    TestUtil.getUid(),
                    TestUtil.getToken(),
                    "1",
                    "1",
                    "1"
            )).subscribe(new BaseObserver<Object>(mAt) {
                @Override
                public void onHandleSuccess(Object o) throws IOException {
                    ToastUtil.showToast("加入购物车成功");
                    bottomDialog.dismiss();
                }
            });
        });
        TextView mBuyNow = contentView.findViewById(R.id.tv_buynow);
        mBuyNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MallGoodsDetailsActivity.this, MallOnlineOrderActivity.class);
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
                i += 1;
                String addNumber = Integer.toString(i);
                mNum.setText(addNumber);
            }
        });

        mSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = mNum.getText().toString();
                int i = Integer.parseInt(number);
                if (i > 1) {
                    i -= 1;
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

    @OnClick(R.id.iv_header_back)
    public void clickBack() {
        finish();
    }

}
