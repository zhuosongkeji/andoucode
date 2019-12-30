package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.view.SimpleDraweeView;
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
import com.zskjprojectj.andouclient.activity.hotel.HotelActivity;
import com.zskjprojectj.andouclient.activity.LiveActivity;
import com.zskjprojectj.andouclient.activity.MainActivity;
import com.zskjprojectj.andouclient.activity.MallMainActivity;
import com.zskjprojectj.andouclient.activity.OnlineBookingorderActivity;
import com.zskjprojectj.andouclient.adapter.CoverFlowAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.TuchongEntity;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.GlideTool;
import com.zskjprojectj.andouclient.utils.ScreenUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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
    private boolean enable;
    private List<HotCity> hotCities;
    private int anim;
    private int theme;
    private TextView tv_cityinfo;
    private XBanner bannertops;
    private LinearLayout check_in_business_seemore_layout, rootView;
    private LinearLayout onlinebroadcast_see_more_layout, appointment_see_more_layout, onlinebooking_see_more_layout, ly_citychoose;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        initCoverFlow();
        bannertops = view.findViewById(R.id.bannertop);
        rootView = view.findViewById(R.id.root_view);

        getBarDistance(rootView);

//        //设置状态栏的高度
//        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) rootView.getLayoutParams();
//        layoutParams.topMargin = BarUtils.getStatusBarHeight(getActivity()) + layoutParams.topMargin;
//        rootView.setLayoutParams(layoutParams);

        check_in_business_seemore_layout = view.findViewById(R.id.check_in_business_seemore_layout);
        onlinebroadcast_see_more_layout = view.findViewById(R.id.onlinebroadcast_see_more_layout);
        appointment_see_more_layout = view.findViewById(R.id.appointment_see_more_layout);
        onlinebooking_see_more_layout = view.findViewById(R.id.onlinebooking_see_more_layout);
        ly_citychoose = view.findViewById(R.id.ly_citychoose);
        tv_cityinfo = view.findViewById(R.id.tv_cityinfo);
    }

    private void initCoverFlow() {

        mCoverFlow.setGreyItem(true); //设置灰度渐变
        mCoverFlow.setAlphaItem(true); //设置半透渐变
        mCoverFlow.setAdapter(new CoverFlowAdapter(getActivity(), this));
        mCoverFlow.smoothScrollToPosition(3);
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
        String url = "https://api.tuchong.com/2/wall-paper/app";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mAty, "加载广告数据失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResponse(String response, int id) {
                TuchongEntity advertiseEntity = new Gson().fromJson(response, TuchongEntity.class);
                List<TuchongEntity.FeedListBean> others = advertiseEntity.getFeedList();
                List<TuchongEntity.FeedListBean.EntryBean> data = new ArrayList<>();
                for (int i = 0; i < others.size(); i++) {
                    TuchongEntity.FeedListBean feedListBean = others.get(i);
                    if ("post".equals(feedListBean.getType())) {
                        data.add(feedListBean.getEntry());
                    }
                }

                //刷新数据之后，需要重新设置是否支持自动轮播
                bannertops.setAutoPlayAble(data.size() > 1);
                bannertops.setIsClipChildrenMode(true);
                bannertops.setBannerData(R.layout.layout_fresco_imageview, data);

            }
        });
    }

    @Override
    protected void initData() {
        //Fresco初始化
        Fresco.initialize(mAty);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(mAty) / 2);
        bannertops.setLayoutParams(layoutParams);
        initBanner(bannertops);
        //加载本地图片
//        initLocalImage();
        check_in_business_seemore_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).getNavigationBar().selectTab(1);
                }
            }
        });
        /**
         *在线直播
         */
        onlinebroadcast_see_more_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), LiveActivity.class));
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
                //startActivity(new Intent(getContext(), OnlineBookingorderActivity.class));
                ToastUtil.showToast("功能持续完成中......");
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
                    //跳转到商城模块
                    case 0:
                        // startActivity(new Intent(getContext(), OnlinemallActivity.class));
//                        startActivity(new Intent(getContext(), MallMainActivity.class));
                        //  Toast.makeText(mAty, "点击了第" + (position + 1) + "图片", Toast.LENGTH_SHORT).show();
                        break;
                    //跳转到酒店预约模块
                    case 1:
//                        startActivity(new Intent(getContext(), HotelActivity.class));
                        break;
                    //跳转饭店预约模块
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
                //此处适用Fresco加载图片，可自行替换自己的图片加载框架
                ImageView draweeView = (ImageView) view;
                TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean.EntryBean) model);
                String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                draweeView.setImageURI(Uri.parse(url));
                GlideTool.glideRoundImg(getActivity(), url, draweeView, ScreenUtil.dp2px(getActivity(), 8));
//                加载本地图片展示
//                ((ImageView) view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
            }
        });
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

        bannertops.setAutoPlayAble(true);
        //刷新数据之后，需要重新设置是否支持自动轮播
        bannertops.setAutoPlayAble(data.size() > 1);
        bannertops.setIsClipChildrenMode(true);
        bannertops.setBannerData(data);
//                bannertops.setBannerData(R.layout.layout_fresco_imageview, data);
    }


    @Override
    public void clickItem(int pos) {
//        ToastUtil.showToast("图"+pos);

        switch (pos % 7) {
            case 0:
                ToastUtil.showToast("图1");
                break;
            case 1:
                ToastUtil.showToast("图2");
                break;
            case 2:
                ToastUtil.showToast("图3");
                break;
            case 3:
//                ToastUtil.showToast("商城");
                startActivity(new Intent(getContext(), MallMainActivity.class));
                break;
            case 4:
//                ToastUtil.showToast("酒店");
                startActivity(new Intent(getContext(), HotelActivity.class));
                break;
            case 5:
                ToastUtil.showToast("图6");
                break;
            case 6:
                ToastUtil.showToast("图7");
                break;

        }
    }
}
