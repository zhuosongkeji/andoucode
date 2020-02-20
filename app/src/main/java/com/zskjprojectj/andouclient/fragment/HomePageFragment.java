package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.stx.xhb.xbanner.XBanner;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.model.ListData;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.AppHomeActivity;
import com.zskjprojectj.andouclient.activity.BookingorderActivity;
import com.zskjprojectj.andouclient.activity.LoginActivity;
import com.zskjprojectj.andouclient.activity.MallMainActivity;
import com.zskjprojectj.andouclient.activity.QrCodeActivity;
import com.zskjprojectj.andouclient.activity.hotel.HotelActivity;
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantHomeActivity;
import com.zskjprojectj.andouclient.adapter.CoverFlowAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.HotelHomeAdapter;
import com.zskjprojectj.andouclient.adapter.mall.RecommendProductsAdapter;
import com.zskjprojectj.andouclient.adapter.merchantsCategoryAdapter;
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantAdapter;
import com.zskjprojectj.andouclient.entity.EnvelopesBean;
import com.zskjprojectj.andouclient.entity.IndexHomeBean;
import com.zskjprojectj.andouclient.entity.NewuserBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean;
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ScreenUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.view.CustomDialog;
import com.zskjprojectj.andouclient.view.OnRedPacketDialogClickListener;
import com.zskjprojectj.andouclient.view.RedPacketViewHolder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import recycler.coverflow.RecyclerCoverFlow;

public class HomePageFragment extends BaseFragment implements CoverFlowAdapter.onItemClick {

    @BindView(R.id.coverflow)
    RecyclerCoverFlow mCoverFlow;

    @BindView(R.id.rv_merchants)
    RecyclerView mRvMerchants;


    @BindView(R.id.view_flipper)
    ViewFlipper view_flipper;

    @BindView(R.id.root_view)
    LinearLayout mRootView;

    private boolean enable;
    private List<HotCity> hotCities;
    private int anim;
    private int theme;
    private TextView tv_cityinfo;
    private XBanner bannertops;
    private LinearLayout onlinebroadcast_see_more_layout, appointment_see_more_layout, onlinebooking_see_more_layout, ly_citychoose;
    private CoverFlowAdapter adapter;
    private merchantsCategoryAdapter merchantsAdapter = new merchantsCategoryAdapter();
    RecommendProductsAdapter recommendProductsAdapter = new RecommendProductsAdapter(R.layout.fragment_mall_goods_details_view, new ArrayList<>());

    //红包
    private CustomDialog mRedPacketDialog;
    private View mRedPacketDialogView;
    private RedPacketViewHolder mRedPacketViewHolder;
    private HotelHomeAdapter hoteladapter = new HotelHomeAdapter();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int barHeight = StatusBarUtil.getStatusBarHeight(mActivity);
        if (barHeight > 0) {
            //设置状态栏的高度
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mRootView.getLayoutParams();
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mActivity) + layoutParams.topMargin;
            mRootView.setLayoutParams(layoutParams);
        }
        initCoverFlow();

        bannertops = view.findViewById(R.id.bannertop);
        view.findViewById(R.id.sha).setOnClickListener(v -> ActivityUtils.startActivity(QrCodeActivity.class));
        onlinebroadcast_see_more_layout = view.findViewById(R.id.onlinebroadcast_see_more_layout);
        appointment_see_more_layout = view.findViewById(R.id.appointment_see_more_layout);
        onlinebooking_see_more_layout = view.findViewById(R.id.onlinebooking_see_more_layout);
        ly_citychoose = view.findViewById(R.id.ly_citychoose);
        tv_cityinfo = view.findViewById(R.id.tv_cityinfo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvMerchants.setLayoutManager(layoutManager);
        mRvMerchants.setAdapter(merchantsAdapter);
        merchantsAdapter.setOnItemClickListener((adapter, view, position) -> {
            switch (merchantsAdapter.getItem(position).getMerchant_type_id()) {
                //商家商城
                case "2":
                    MallShoppingHomeActivity.start(merchantsAdapter.getItem(position).getId());
                    break;
                //酒店商家
                case "3":
                    HotelDetailActivity.start(merchantsAdapter.getItem(position).getId());
                    break;
                //饭店商家
                case "4":
                    RestaurantDetailActivity.start(merchantsAdapter.getItem(position).getId());
                    break;
                //农家乐
                case "5":
                    break;
                //旅游
                case "6":
                    break;
                //美食预订
                case "7":
                    break;
                //农家乐民宿
                case "8":
                    break;
            }
        });

        initData();
        getDataFromServer();
        RequestUtil.request(mActivity, true, false,
                () -> ApiUtils.getApiService().getRestaurants(null, null, null, 1),
                result -> {
                    RestaurantAdapter adapter = new RestaurantAdapter();
                    adapter.bindToRecyclerView(view.findViewById(R.id.restaurantRecyclerView));
                    adapter.setNewData(result.data);
                    adapter.setOnItemClickListener((adapter1, view, position) ->
                            RestaurantDetailActivity.start(adapter.getItem(position).id));
                });
        RecyclerView hotelRecyclerView = view.findViewById(R.id.hotelRecyclerView);
        hotelRecyclerView.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        hoteladapter.bindToRecyclerView(hotelRecyclerView);
        hoteladapter.setOnItemClickListener((adapter, view, position) ->
                HotelDetailActivity.start(hoteladapter.getItem(position).getId()));
        recommendProductsAdapter.bindToRecyclerView(view.findViewById(R.id.goodsRecyclerView));
        recommendProductsAdapter.setOnItemClickListener((adapter, view, position) ->
                MallGoodsDetailsActivity.start(recommendProductsAdapter.getItem(position).getId()));
    }

    private void showRedPacketDialog() {
        if (mRedPacketDialogView == null) {
            mRedPacketDialogView = View.inflate(mActivity, R.layout.dialog_red_packet, null);
            TextView tv_money = mRedPacketDialogView.findViewById(R.id.tv_hbmoney);
            HttpRxObservable.getObservable(ApiUtils.getApiService().envelopes(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<EnvelopesBean>(mActivity) {
                @Override
                public void onHandleSuccess(EnvelopesBean envelopesBean) throws IOException {
                    tv_money.setText("最高可获得" + envelopesBean.getValue() + "元");
                }
            });
            mRedPacketViewHolder = new RedPacketViewHolder(mActivity, mRedPacketDialogView);
            mRedPacketDialog = new CustomDialog(mActivity, mRedPacketDialogView, R.style.custom_dialog);
            mRedPacketDialog.setCancelable(false);
        }
        mRedPacketViewHolder.setOnRedPacketDialogClickListener(new OnRedPacketDialogClickListener() {
            @Override
            public void onCloseClick() {
                mRedPacketDialog.dismiss();
            }

            @Override
            public void onOpenClick() {
                if (TextUtils.isEmpty(LoginInfoUtil.getToken())) {
                    LoginActivity.start(mActivity);
                } else {
                    //领取红包,调用接口
                    HttpRxObservable.getObservable(ApiUtils.getApiService().envelopes_add(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<Object>(mActivity) {
                        @Override
                        public void onHandleSuccess(Object o) throws IOException {
                            ToastUtil.showToast("领取成功");
                            mRedPacketDialog.dismiss();
                        }
                    });
                }

            }
        });
        mRedPacketDialog.show();
    }

    private void initCoverFlow() {
        adapter = new CoverFlowAdapter(getActivity(), this);
        mCoverFlow.setGreyItem(true); //设置灰度渐变
        mCoverFlow.setAlphaItem(true); //设置半透渐变
    }


    private void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().index()).subscribe(new BaseObserver<IndexHomeBean>(mActivity) {
            @Override
            public void onHandleSuccess(IndexHomeBean indexHomeBean) throws IOException {

                List<IndexHomeBean.BannerBean> banner = indexHomeBean.getBanner();
                //刷新数据之后，需要重新设置是否支持自动轮播
                bannertops.setAutoPlayAble(banner.size() > 1);
                bannertops.setIsClipChildrenMode(true);
                bannertops.setBannerData(banner);

                ArrayList<IndexHomeBean.MerchantTypeBean> merchant_type = indexHomeBean.getMerchant_type();
                adapter.setNewData(merchant_type);
                mCoverFlow.setAdapter(adapter);
                mCoverFlow.scrollToPosition(merchant_type.size() * 1000);
                List<IndexHomeBean.MerchantsBean> merchants = indexHomeBean.getMerchants();
                merchantsAdapter.setNewData(merchants);


                List<IndexHomeBean.NoticeBean> notice = indexHomeBean.getNotice();
                for (int i = 0; i < notice.size(); i++) {
                    View view = getLayoutInflater().inflate(R.layout.text_view, null);
                    TextView content = view.findViewById(R.id.tv_index_notice);
                    content.setText(notice.get(i).getContent());
                    view_flipper.addView(view);
                }
                view_flipper.setFlipInterval(2000);
                view_flipper.startFlipping();

            }
        });
        if (!TextUtils.isEmpty(LoginInfoUtil.getToken())) {
            HttpRxObservable.getObservable(ApiUtils.getApiService().new_user(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(new BaseObserver<NewuserBean>(mActivity) {
                @Override
                public void onHandleSuccess(NewuserBean newuserBean) throws IOException {
                    if (newuserBean.getVal() == 1) {
                        showRedPacketDialog();
                    }

                }
            });
        }

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelHomeList("", "", "", "", "", 1))
                .subscribe(new BaseObserver<ListData<HotelHomeBean>>(mActivity) {
                    @Override
                    public void onHandleSuccess(ListData<HotelHomeBean> hotelHomeBeans) throws IOException {
                        for (int i = 0; i < hotelHomeBeans.size(); i++) {
                            if (i > 2) return;
                            hoteladapter.addData(hotelHomeBeans.get(i));
                        }
                    }
                });
        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallInfo())
                .subscribe(new BaseObserver<MallHomeDataBean>(mActivity) {

                    @Override
                    public void onHandleSuccess(MallHomeDataBean bean) {
                        List<MallHomeDataBean.RecommendGoodsBean> recommend_goods = bean.getRecommend_goods();
                        recommendProductsAdapter.setNewData(recommend_goods);
                    }
                });

    }

    private void initData() {


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(mActivity) / 2);
        bannertops.setLayoutParams(layoutParams);
        initBanner(bannertops);

        /**
         *在线直播
         */
        onlinebroadcast_see_more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ToastUtil.showToast("功能持续完成中......");
            }
        });
        /**
         * 外卖
         */
        appointment_see_more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), BookingorderActivity.class));
            }
        });
        /**
         * 在线预约订单(饭店预订)
         */
        onlinebooking_see_more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.startActivity(RestaurantHomeActivity.class);
            }
        });
        /**
         * 城市选择
         */
        ly_citychoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CityPicker.from(getContext())
                        .enableAnimation(enable)
                        .setAnimationStyle(anim)
                        .setLocatedCity(null)
                        .setHotCities(hotCities)
                        .setOnPickListener(new OnPickListener() {
                            @Override
                            public void onPick(int position, City data) {
                                tv_cityinfo.setText(String.format("%s", data.getName()));
                                //  tv_cityinfo.setText(String.format("当前城市：%s，%s", data.getName(), data.code()));
//                                Toast.makeText(
//                                        getApplicationContext(),
//                                        String.format("点击的数据：%s，%s", data.getName(), data.code()),
//                                        Toast.LENGTH_SHORT)
//                                        .show();
                            }

                            @Override
                            public void onCancel() {
                                // Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onLocate() {
                                //开始定位，这里模拟一下定位
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        CityPicker.from(getContext()).locateComplete(new LocatedCity("重庆", "广东", "101280601"), LocateState.SUCCESS);
                                    }
                                }, 3000);
                            }
                        })
                        .show();
            }
        });
    }

    /**
     * 初始化XBanner
     */
    private void initBanner(XBanner banner) {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {
                //判断是点击了那个选项
                switch (position) {
                    case 0:
                        break;
                    case 1:
//                        startActivity(new Intent(getContext(), HotelActivity.class));
                        break;
                    case 2:
//                        startActivity(new Intent(getContext(),));
                        break;
                }

            }
        });

        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                IndexHomeBean.BannerBean model1 = (IndexHomeBean.BannerBean) model;
                Glide.with(mActivity).load(UrlUtil.getImageUrl(model1.getImg())).apply(new RequestOptions()
                        .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) view);
            }
        });
    }


    @Override
    public void clickItem(int pos) {
        mCoverFlow.smoothScrollToPosition(pos);
        int index = pos % adapter.merchant_type.size();
        switch (index) {
            /**
             * 商城
             */
            case 0:
                startActivity(new Intent(getContext(), MallMainActivity.class));
                break;
            /**
             * 酒店
             */
            case 1:
                startActivity(new Intent(getContext(), HotelActivity.class));
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
        }
    }

    @OnClick(R.id.ll_see_more)
    public void clickSeeMore() {
        if (getActivity() instanceof AppHomeActivity) {
            ((AppHomeActivity) getActivity()).getNavigationBar().selectTab(1);
        }
    }

    @OnClick(R.id.more_hotel_btn)
    void onMoreHotelBtnClick() {
        ActivityUtils.startActivity(HotelActivity.class);
    }

    @OnClick(R.id.moreGoodsBtn)
    void onMoreGoodsBtnClick() {
        ActivityUtils.startActivity(MallMainActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_homepage;
    }
    /**
     *  加载弹窗
     */

}
