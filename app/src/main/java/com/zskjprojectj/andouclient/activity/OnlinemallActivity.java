package com.zskjprojectj.andouclient.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.TuchongEntity;
import com.zskjprojectj.andouclient.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 在线商城
 */
public class OnlinemallActivity extends BaseActivity {
    private XBanner onlinebanner;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_onlinemall);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("在线商城");
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(mAt) / 2);
        onlinebanner.setLayoutParams(layoutParams);
        initBanner(onlinebanner);
    }

    @Override
    protected void initViews() {
        onlinebanner=findViewById(R.id.onlinebanner);
    }

    @Override
    public void getDataFromServer() {
        String url = "https://api.tuchong.com/2/wall-paper/app";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
                Toast.makeText(mAt, "加载广告数据失败", Toast.LENGTH_SHORT).show();
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
                onlinebanner.setAutoPlayAble(data.size() > 1);
                onlinebanner.setIsClipChildrenMode(true);
                onlinebanner.setBannerData(R.layout.layout_fresco_imageview, data);

            }
        });
    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    /**
     * 初始化XBanner
     */
    private void initBanner(XBanner banner)
    {
        //设置广告图片点击事件
        banner.setOnItemClickListener(new XBanner.OnItemClickListener() {
            @Override
            public void onItemClick(XBanner banner, Object model, View view, int position) {

                //判断是点击了那个选项
                switch (position)
                {
                    case 0:
                        break;
                    //跳转到酒店预订模块
                    case 1:
                        break;
                }

            }
        });

        //加载广告图片
        banner.loadImage(new XBanner.XBannerAdapter() {
            @Override
            public void loadBanner(XBanner banner, Object model, View view, int position) {
                //此处适用Fresco加载图片，可自行替换自己的图片加载框架
                SimpleDraweeView draweeView = (SimpleDraweeView) view;
                TuchongEntity.FeedListBean.EntryBean listBean = ((TuchongEntity.FeedListBean.EntryBean) model);
                String url = "https://photo.tuchong.com/" + listBean.getImages().get(0).getUser_id() + "/f/" + listBean.getImages().get(0).getImg_id() + ".jpg";
                draweeView.setImageURI(Uri.parse(url));
//                加载本地图片展示
//          ((ImageView)view).setImageResource(((LocalImageInfo) model).getXBannerUrl());
            }
        });
    }
}
