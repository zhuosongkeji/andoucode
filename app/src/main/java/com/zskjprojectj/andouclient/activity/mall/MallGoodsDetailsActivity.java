package com.zskjprojectj.andouclient.activity.mall;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.shizhefei.view.indicator.FixedIndicatorView;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.slidebar.ColorBar;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;


import com.stx.xhb.xbanner.XBanner;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MallMainActivity;
import com.zskjprojectj.andouclient.activity.MyaddressActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallBuyAdapter;
import com.zskjprojectj.andouclient.adapter.mall.MallPinTuanAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.model.PinTuanDetails;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.entity.XBannerBean;
import com.zskjprojectj.andouclient.entity.mall.MallBuyBean;
import com.zskjprojectj.andouclient.entity.mall.MallBuyNowBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsDetailsDataBean;
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog;
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsCommentFragment;
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsDetailFragment;
import com.zskjprojectj.andouclient.http.ApiException;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.OnClick;

public class MallGoodsDetailsActivity extends BaseActivity {

    private static final String GOODS_ID = "goodsId";
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
    //商品收藏图标
    @BindView(R.id.iv_iscollection)
    ImageView iviscollection;
    //商家头像
    @BindView(R.id.iv_headPic)
    ImageView IvHeadPic;
    //商家名字
    @BindView(R.id.tv_name)
    TextView mTvName;

    @BindView(R.id.miaoShaContainer)
    LinearLayout miaoShaContainer;

    @BindView(R.id.goodsPrice)
    LinearLayout mGoodsPrice;

    @BindView(R.id.tv_mall_goods_name2)
    TextView tv_mall_goods_name2;

    @BindView(R.id.tv_pintuan)
    RecyclerView tv_pintuan;

    @BindView(R.id.ll_pintuan_person)
    LinearLayout ll_pintuan_person;

    @BindView(R.id.ll_pintuan_list)
    LinearLayout ll_pintuan_list;

    @BindView(R.id.tv_pintuan_number)
    TextView tv_pintuan_number;

    @BindView(R.id.tv_total_member)
    TextView tv_total_member;
    @BindView(R.id.ll_normal)
    LinearLayout ll_normal;
    @BindView(R.id.ll_pintuan)
    LinearLayout ll_pintuan;

    private FixedIndicatorView mIndicator;
    private ViewPager mViewPager;
    private List<Fragment> list = new ArrayList<>();
    private Dialog bottomDialog;
    private View contentView;
    private String goodsId;
    private boolean isCollection = false;
    private String type;
    private String merchant_id;
    private ArrayList<MallBuyBean.SpecInfo> res;
    //商户Id
    private int merchantId;

    private MallPinTuanAdapter pinTuanAdapter = new MallPinTuanAdapter();

    //商品图片
    private String goodsImg;
    //商品名称
    private String goodsName;
    //商品价格
    private String goodsPrice;
    //商品订单号
    private String order_sn;
    //订单图片
    private String orderImg;
    //订单标题
    private String orderName;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_goods_details);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {


        initLocalImage();

        //商品详情

        MallGoodsDetailFragment mallGoodsDetailFragment = new MallGoodsDetailFragment();
        Bundle detailBundle = new Bundle();
        detailBundle.putString("id", goodsId);
        mallGoodsDetailFragment.setArguments(detailBundle);
        list.add(mallGoodsDetailFragment);

        //商品评论

        MallGoodsCommentFragment mallGoodsCommentFragment = new MallGoodsCommentFragment();
        Bundle commentBundle = new Bundle();
        commentBundle.putString("id", goodsId);
        mallGoodsCommentFragment.setArguments(commentBundle);
        list.add(mallGoodsCommentFragment);


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

    private void initPinTuan() {
        pinTuanAdapter.bindToRecyclerView(tv_pintuan);

        //商品详情
        HttpRxObservable.getObservable(ApiUtils.getApiService().tuangouDetails(goodsId))
                .subscribe(new BaseObserver<PinTuanDetails>(this) {

                    @Override
                    public void onHandleSuccess(PinTuanDetails pinTuanDetails) throws IOException {
                        if("0".equals(pinTuanDetails.getGroup_goods().getCode())){
                            ll_pintuan_person.setVisibility(View.VISIBLE);
                            ll_pintuan_list.setVisibility(View.VISIBLE);

                            mTvPrice.setText(pinTuanDetails.getGroup_goods().getPrice());

                            tv_pintuan_number.setText(pinTuanDetails.getGroup_goods().getTop_member()+"人拼");
                            mTvGoodsVolume.setText(pinTuanDetails.getGroup_goods().getSale_total());
                            mTvGoodsStoreNum.setText(pinTuanDetails.getGroup_goods().getStorage());
                            tv_total_member.setText("已有"+pinTuanDetails.getTotal_member()+"人参团");
                            pinTuanAdapter.setNewData(pinTuanDetails.getTeam_list());
                            ll_normal.setVisibility(View.GONE);
                            ll_pintuan.setVisibility(View.VISIBLE);
                        }
                    }
                });




        pinTuanAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MallOnlineOrderActivity.start(order_sn);
            }
        });
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
                String url = UrlUtil.getImageUrl(urlList.getImageUrl());
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
        goodsId = getIntent().getStringExtra(GOODS_ID);


        getBarDistance(mHeaderTitleView);
        mHeaderTitle.setText("商品详情");
        mIndicator = findViewById(R.id.indicator);
        mViewPager = findViewById(R.id.viewPager);
    }

    @Override
    public void getDataFromServer() {


        //商品详情
        HttpRxObservable.getObservable(ApiUtils.getApiService().mallDetailsShow(goodsId, LoginInfoUtil.getUid()))
                .subscribe(new BaseObserver<MallGoodsDetailsDataBean>(this) {
                    @Override
                    public void onHandleSuccess(MallGoodsDetailsDataBean mallGoodsDetailsDataBean) throws IOException {
                        merchantId = mallGoodsDetailsDataBean.getMerchant().getId();
                        goodsImg = mallGoodsDetailsDataBean.getImg();
                        goodsName = mallGoodsDetailsDataBean.getName();
                        goodsPrice = mallGoodsDetailsDataBean.getPrice();

                        List<XBannerBean> urlBanner = new ArrayList<>();
                        urlBanner.clear();
                        //轮播图
                        String img = mallGoodsDetailsDataBean.getImg();
                        urlBanner.add(new XBannerBean(img));
                        List<String> album = mallGoodsDetailsDataBean.getAlbum();
                        if (album.size() != 0) {
                            for (String s : album) {
                                urlBanner.add(new XBannerBean(s));
                            }
                        }
                        /**
                         * 随机设置是否秒杀
                         */
                        mallGoodsDetailsDataBean.isMiaoSha = new Random().nextBoolean();
                        miaoShaContainer.setVisibility(mallGoodsDetailsDataBean.isMiaoSha ? View.VISIBLE : View.GONE);
                        mGoodsPrice.setVisibility(mallGoodsDetailsDataBean.isMiaoSha ? View.GONE : View.VISIBLE);
                        tv_mall_goods_name2.setVisibility(mallGoodsDetailsDataBean.isMiaoSha ? View.VISIBLE : View.GONE);
                        mMallGoodsName.setVisibility(mallGoodsDetailsDataBean.isMiaoSha ? View.GONE : View.VISIBLE);
                        mBanner.setBannerData(urlBanner);
                        mMallGoodsName.setText(mallGoodsDetailsDataBean.getName());
                        mTvPrice.setText(mallGoodsDetailsDataBean.getPrice());
                        mTvGoodsDilivery.setText(mallGoodsDetailsDataBean.getDilivery());
                        mTvGoodsVolume.setText(mallGoodsDetailsDataBean.getVolume());
                        mTvGoodsStoreNum.setText(mallGoodsDetailsDataBean.getStore_num());//商品库存

                        Glide.with(MallGoodsDetailsActivity.this).load(UrlUtil.getImageUrl(mallGoodsDetailsDataBean.getMerchant().getLogo_img()))
                                .apply(new RequestOptions()
                                        .placeholder(R.mipmap.ic_default_head_photo).error(R.mipmap.ic_default_head_photo))
                                .into(IvHeadPic);

                        mTvName.setText(mallGoodsDetailsDataBean.getMerchant().getName());

                        if ("0".equals(mallGoodsDetailsDataBean.getIs_collection())) {
                            mtvMallGoodsCollection.setText("收藏");
                            isCollection = false;
                            iviscollection.setImageResource(R.mipmap.uncollectionicon);
                        } else {
                            mtvMallGoodsCollection.setText("已收藏");
                            isCollection = true;
                            iviscollection.setImageResource(R.mipmap.ic_heart_mall);
                        }

                        merchant_id = mallGoodsDetailsDataBean.getMerchant_id();

                        orderImg = mallGoodsDetailsDataBean.getImg();
                        orderName = mallGoodsDetailsDataBean.getName();
                        //拼团
                        initPinTuan();

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


    @OnClick({R.id.pintuan_add_shopping,R.id.mall_goods_collection, R.id.tv_mall_home, R.id.tv_mall_shopping, R.id.tv_Mall_service, R.id.tv_buy_now, R.id.add_shopping, R.id.rv_shop_home, R.id.bt_mall_goods_discount, R.id.shared})
    public void clickButNow(View v) {
        switch (v.getId()) {

            case R.id.mall_goods_collection:
                if (!isCollection) {
                    mtvMallGoodsCollection.setText("已收藏");
                    iviscollection.setImageResource(R.mipmap.ic_heart_mall);
                    isCollection = true;
                    type = "1";
                } else {
                    mtvMallGoodsCollection.setText("收藏");
                    iviscollection.setImageResource(R.mipmap.uncollectionicon);
                    type = "0";
                    isCollection = false;
                }

                Log.d(TAG, "type: " + type);
                HttpRxObservable.getObservable(ApiUtils.getApiService().mallGoodsCollection(
                        goodsId,
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
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
                //开团单独购买
            case R.id.pintuan_add_shopping:

                goToBuy();

                break;
                //拼团购买

            //TODO
            case R.id.pintuan_tv_buy_now:
            //  goToBuy();
                break;
            //店铺主页
            case R.id.tv_mall_home:
            case R.id.rv_shop_home:
                MallShoppingHomeActivity.start(merchant_id);
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

    private void goToBuy() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().buySpecification(goodsId))
                .subscribe(new BaseObserver<MallBuyBean>(mAt) {
                    @Override
                    public void onHandleSuccess(MallBuyBean mallBuyBean) throws IOException {
//                                mallBuyBean.res.get(9).name
//                                mallBuyBean.res.get(9).value
                        //获取拼接选择之后的id，sum，price
//                                mallBuyBean.price.get("").
                        res = new ArrayList<>();
                        for (MallBuyBean.SpecInfo re : mallBuyBean.res) {
                            MallBuyBean.SpecInfo info = new MallBuyBean.SpecInfo();
                            info.name = re.name;
                            res.add(info);
                        }

                        initBuyNow(mallBuyBean.res, mallBuyBean.price);
                    }
                });
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

    private void initBuyNow(ArrayList<MallBuyBean.SpecInfo> res, HashMap<String, MallBuyBean.PriceInfo> price) {

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

        ImageView mOrderImg = contentView.findViewById(R.id.iv_online_order_image);
        Glide.with(mOrderImg.getContext()).load(UrlUtil.getImageUrl(orderImg)).apply(new RequestOptions()
                .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into(mOrderImg);
        TextView mOrderName = contentView.findViewById(R.id.tv_order_name);
        mOrderName.setText(orderName);
        TextView mGoodsNum = contentView.findViewById(R.id.tv_goods_num_a);
        TextView mGoodsPrice = contentView.findViewById(R.id.tv_goods_price_a);
        RecyclerView mBuyRecycler = contentView.findViewById(R.id.rv_buy_recyclerview);
        mBuyRecycler.setLayoutManager(new LinearLayoutManager(mBuyRecycler.getContext()));


        MallBuyAdapter buyAdapter = new MallBuyAdapter(R.layout.mall_buy_item, res);
        mBuyRecycler.setAdapter(buyAdapter);

        buyAdapter.setItemClickKind(new MallBuyAdapter.ItemClickKind() {
            @Override
            public void getItemKind(String spec, String kind) {
                for (MallBuyBean.SpecInfo re : MallGoodsDetailsActivity.this.res) {
                    if (re.name.equals(spec)) {
                        re.value.clear();
                        if (kind != null) {
                            re.value.add(kind);
                        }
                    }
                }


                //显示价格，库存
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < MallGoodsDetailsActivity.this.res.size(); i++) {
                    MallBuyBean.SpecInfo info = MallGoodsDetailsActivity.this.res.get(i);
                    if (info.value.size() <= 0) {
//                        ToastUtil.showToast("请选择" + info.name);
                        return;
                    } else {
                        buffer.append(info.value.get(0)).append("-");
                    }
                }
                String selectKind = buffer.substring(0, buffer.length() - 1);
                MallBuyBean.PriceInfo priceInfo = price.get(selectKind);
                int num = priceInfo.num;
                String goodsNum = String.valueOf(num);
                mGoodsNum.setText(goodsNum);
                mGoodsPrice.setText("¥" + priceInfo.price);


            }
        });
        //减
        ImageView mSub = contentView.findViewById(R.id.btn_sub);
        //加
        ImageView mAdd = contentView.findViewById(R.id.btn_add);
        TextView mNum = contentView.findViewById(R.id.tv_num);

        //加入购物车
        TextView mTvAddShopping = contentView.findViewById(R.id.tv_add_shopping);
        mTvAddShopping.setOnClickListener(v -> {

            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < MallGoodsDetailsActivity.this.res.size(); i++) {
                MallBuyBean.SpecInfo info = MallGoodsDetailsActivity.this.res.get(i);
                if (info.value.size() <= 0) {
                    ToastUtil.showToast("请选择" + info.name);
                    return;
                } else {
                    buffer.append(info.value.get(0)).append("-");
                }
            }
            String selectKind = buffer.substring(0, buffer.length() - 1);
            MallBuyBean.PriceInfo priceInfo = price.get(selectKind);
            String goods_sku_id = priceInfo.id;


            HttpRxObservable.getObservable(ApiUtils.getApiService().addCar(
                    LoginInfoUtil.getUid(),
                    LoginInfoUtil.getToken(),
                    goodsId,
                    merchant_id,
                    goods_sku_id
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
                //购买数量
                String num = mNum.getText().toString();
                StringBuffer buffer = new StringBuffer();
                for (int i = 0; i < MallGoodsDetailsActivity.this.res.size(); i++) {
                    MallBuyBean.SpecInfo info = MallGoodsDetailsActivity.this.res.get(i);
                    if (info.value.size() <= 0) {
                        ToastUtil.showToast("请选择" + info.name);
                        return;
                    } else {
                        buffer.append(info.value.get(0)).append("-");
                    }
                }
                String selectKind = buffer.substring(0, buffer.length() - 1);

                MallBuyBean.PriceInfo priceInfo = price.get(selectKind);
                String goods_sku_id = priceInfo.id;

                String merchant_id = String.valueOf(merchantId);

                HttpRxObservable.getObservable(ApiUtils.getApiService().MallBuyNow(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        goodsId,
                        merchant_id,
                        goods_sku_id,
                        num
                )).subscribe(new BaseObserver<MallBuyNowBean>(mAt) {
                    @Override
                    public void onHandleSuccess(MallBuyNowBean mallBuyNowBean) throws IOException {
                        order_sn = mallBuyNowBean.getOrder_sn();
                        MallOnlineOrderActivity.start(order_sn);
                        bottomDialog.dismiss();

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);

                        if ("请填写收货地址".equals(e.getMessage())) {
                            startActivity(new Intent(mAt, MyaddressActivity.class));
                        }
                    }
                });


            }
        });

        ImageView mCancle = contentView.findViewById(R.id.iv_cancle);
        mCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomDialog.dismiss();
            }
        });


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

    public static void start(String id) {
        Bundle intent = new Bundle();
        intent.putString(GOODS_ID, id);
        ActivityUtils.startActivity(intent, MallGoodsDetailsActivity.class);

    }
}
