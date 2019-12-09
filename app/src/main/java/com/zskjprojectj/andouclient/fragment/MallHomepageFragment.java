package com.zskjprojectj.andouclient.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.wihaohao.PageGridView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.BookorderBean;
import com.zskjprojectj.andouclient.entity.TuchongEntity;
import com.zskjprojectj.andouclient.utils.ScreenUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 在线商城主页
 */
public class MallHomepageFragment extends BaseFragment {
    List<BookorderBean> mList;
    private String[] proName = {"美妆个护","进口尖货","服饰内衣","鞋包配饰","家纺家电","居家百货",
            "休闲美食","玩具早教",};
    private int[] proicon = {R.mipmap.icon_meizhuang,R.mipmap.icon_jianhuo,R.mipmap.icon_fushi,R.mipmap.icon_xiebao,R.mipmap.icon_jianfang,R.mipmap.icon_baihuo,
            R.mipmap.icon_meishi,R.mipmap.icon_zaojiao};
    private PageGridView<BookorderBean> mPageGridView;
    private XBanner onlinebanner;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        onlinebanner=view.findViewById(R.id.onlinebanner);
        mPageGridView =view.findViewById(R.id.vp_grid_view);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_onlinemall;
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
                onlinebanner.setAutoPlayAble(data.size() > 1);
                onlinebanner.setIsClipChildrenMode(true);
                onlinebanner.setBannerData(R.layout.layout_fresco_imageview, data);

            }
        });
    }

    @Override
    protected void initData() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(mAty) / 2);
        onlinebanner.setLayoutParams(layoutParams);
        initBanner(onlinebanner);
        initDatas();
        mPageGridView.setData(mList);
        mPageGridView.setOnItemClickListener(new PageGridView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                // Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });

    }

    /**
     * 初始化导航菜单
     */
    private void initDatas() {
        mList=new ArrayList<>();
        for(int i=0;i<proName.length;i++){
            mList.add(new BookorderBean(proName[i],proicon[i]));
        }
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
