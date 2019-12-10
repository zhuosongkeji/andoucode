package com.zskjprojectj.andouclient.fragment.mall;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.stx.xhb.xbanner.XBanner;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MultipleMallHomeAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.TuchongEntity;
import com.zskjprojectj.andouclient.entity.mall.MallItemDataBean;
import com.zskjprojectj.andouclient.utils.ScreenUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/9 19:55
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallHomepageFragment1 extends BaseFragment {

    private RecyclerView mRecycler;
    private ArrayList<MallItemDataBean> dataBeansList;
    private XBanner onlinebanner;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mRecycler = view.findViewById(R.id.rv_recycler);

        onlinebanner = view.findViewById(R.id.onlinebanner);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_onlinemall1;
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
        dataBeansList = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
//            dataBeansList.add(new MallItemDataBean(MallItemDataBean.SEARCH, MallItemDataBean.SEARCH_SPAN_SIZE));
//            dataBeansList.add(new MallItemDataBean(MallItemDataBean.BANNER, MallItemDataBean.BANNER_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.MENU, MallItemDataBean.MENU_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.PLAN, MallItemDataBean.PLAN_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.PLAN, MallItemDataBean.PLAN_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.NEWGOODS, MallItemDataBean.NEWGOODS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.RECOMMEND, MallItemDataBean.RECOMMEND_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.RECOMMEND, MallItemDataBean.RECOMMEND_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
            dataBeansList.add(new MallItemDataBean(MallItemDataBean.GOODSDETAILS, MallItemDataBean.GOODSDETAILS_SPAN_SIZE));
        }


        MultipleMallHomeAdapter adapter = new MultipleMallHomeAdapter(getActivity(), dataBeansList);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 4);
        mRecycler.setLayoutManager(manager);
        adapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                return dataBeansList.get(i).getSpanSize();
            }
        });

        mRecycler.setAdapter(adapter);

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

//                Toast.makeText(ItemClickActivity.this, "onItemClick" + position, Toast.LENGTH_SHORT).show();
                ToastUtil.showToast("aaaaa" + position);

            }
        });

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("dddd");
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
