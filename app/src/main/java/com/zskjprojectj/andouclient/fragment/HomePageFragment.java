package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.google.gson.Gson;


import com.stx.xhb.xbanner.XBanner;
import com.stx.xhb.xbanner.entity.LocalImageInfo;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.BookingorderActivity;
import com.zskjprojectj.andouclient.activity.QrCodeActivity;
import com.zskjprojectj.andouclient.activity.WebViewActivity;
import com.zskjprojectj.andouclient.activity.hotel.HotelActivity;
import com.zskjprojectj.andouclient.activity.LiveActivity;
import com.zskjprojectj.andouclient.activity.MainActivity;
import com.zskjprojectj.andouclient.activity.MallMainActivity;
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantHomeActivity;
import com.zskjprojectj.andouclient.adapter.CoverFlowAdapter;
import com.zskjprojectj.andouclient.adapter.merchantsCategoryAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.IndexHomeBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.GlideTool;
import com.zskjprojectj.andouclient.utils.ScreenUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 * 首页的碎片对象
 *
 * @author yizhubao
 */
public class HomePageFragment extends BaseFragment implements CoverFlowAdapter.onItemClick {

    @BindView(R.id.coverflow)
    RecyclerCoverFlow mCoverFlow;

    @BindView(R.id.rv_merchants)
    RecyclerView mRvMerchants;


    @BindView(R.id.view_flipper)
    ViewFlipper view_flipper;

    private boolean enable;
    private List<HotCity> hotCities;
    private int anim;
    private int theme;
    private TextView tv_cityinfo;
    private XBanner bannertops;
    private LinearLayout rootView;
    private LinearLayout onlinebroadcast_see_more_layout, appointment_see_more_layout, onlinebooking_see_more_layout, ly_citychoose;
    private CoverFlowAdapter adapter;
    private merchantsCategoryAdapter merchantsAdapter = new merchantsCategoryAdapter();


    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        initCoverFlow();
        bannertops = view.findViewById(R.id.bannertop);
        rootView = view.findViewById(R.id.root_view);
        view.findViewById(R.id.sha).setOnClickListener(v -> ActivityUtils.startActivity(QrCodeActivity.class));
        getBarDistance(rootView);

//        //设置状态栏的高度
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rootView.getLayoutParams();
//        layoutParams.topMargin = BarUtils.getStatusBarHeight(getActivity()) + layoutParams.topMargin;
//        rootView.setLayoutParams(layoutParams);

        onlinebroadcast_see_more_layout = view.findViewById(R.id.onlinebroadcast_see_more_layout);
        appointment_see_more_layout = view.findViewById(R.id.appointment_see_more_layout);
        onlinebooking_see_more_layout = view.findViewById(R.id.onlinebooking_see_more_layout);
        ly_citychoose = view.findViewById(R.id.ly_citychoose);
        tv_cityinfo = view.findViewById(R.id.tv_cityinfo);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRvMerchants.setLayoutManager(layoutManager);
        mRvMerchants.setAdapter(merchantsAdapter);


    }

    private void initCoverFlow() {
        adapter = new CoverFlowAdapter(getActivity(), this);
        mCoverFlow.setGreyItem(true); //设置灰度渐变
        mCoverFlow.setAlphaItem(true); //设置半透渐变


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mCoverFlow.smoothScrollToPosition(2);
            }
        }, 500);

        mCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
//                ((TextView)findViewById(R.id.index)).setText((position+1)+"/"+mList.getLayoutManager().getItemCount());
            }
        });
    }

    /**
     * 这里是加载的首页碎片布局,布局是怎么样的页面就是怎么样的
     *
     * @return
     */
    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_homepage;
    }

    @Override
    protected void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().index()).subscribe(new BaseObserver<IndexHomeBean>(mAty) {
            @Override
            public void onHandleSuccess(IndexHomeBean indexHomeBean) throws IOException {

                List<IndexHomeBean.BannerBean> banner = indexHomeBean.getBanner();
                //刷新数据之后，需要重新设置是否支持自动轮播
                bannertops.setAutoPlayAble(banner.size() > 1);
                bannertops.setIsClipChildrenMode(true);
                bannertops.setBannerData(banner);

                List<IndexHomeBean.MerchantTypeBean> merchant_type = indexHomeBean.getMerchant_type();
                adapter.setNewData(merchant_type);
                mCoverFlow.setAdapter(adapter);

                List<IndexHomeBean.MerchantsBean> merchants = indexHomeBean.getMerchants();
                merchantsAdapter.setNewData(merchants);


                List<IndexHomeBean.NoticeBean> notice = indexHomeBean.getNotice();
                Log.d(TAG, "oaaaaaaaa: " + notice.size());
                for (int i = 0; i < notice.size(); i++) {
                    View view = getLayoutInflater().inflate(R.layout.text_view, null);
                    TextView content = view.findViewById(R.id.tv_index_notice);
                    content.setText(notice.get(i).getContent());
                    Log.d(TAG, "notice" + notice.get(i).getContent());
                    view_flipper.addView(view);
                }
                view_flipper.setFlipInterval(2000);
                view_flipper.startFlipping();

            }
        });


    }

    @Override
    protected void initData() {


        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(mAty) / 2);
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
                                //  tv_cityinfo.setText(String.format("当前城市：%s，%s", data.getName(), data.getCode()));
//                                Toast.makeText(
//                                        getApplicationContext(),
//                                        String.format("点击的数据：%s，%s", data.getName(), data.getCode()),
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
                Glide.with(mAty).load(BaseUrl.BASE_URL + model1.getImg()).apply(new RequestOptions()
                        .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) view);
            }
        });
    }


    @Override
    public void clickItem(int pos) {

        switch (pos) {
            case 0:
                startActivity(new Intent(getContext(), MallMainActivity.class));
                break;
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
        if (getActivity() instanceof MainActivity) {
            ((MainActivity) getActivity()).getNavigationBar().selectTab(1);
        }
    }
}
