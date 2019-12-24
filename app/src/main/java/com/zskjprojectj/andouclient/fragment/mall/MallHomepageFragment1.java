package com.zskjprojectj.andouclient.fragment.mall;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;


import com.stx.xhb.xbanner.XBanner;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MainActivity;
import com.zskjprojectj.andouclient.adapter.mall.MultipleMallHomeAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.TuchongEntity;
import com.zskjprojectj.andouclient.entity.mall.DataBean;
import com.zskjprojectj.andouclient.entity.mall.MallItemDataBean;
import com.zskjprojectj.andouclient.http.ApiException;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.ScreenUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
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

    private ArrayList<MallItemDataBean> dataBeansList;

    @BindView(R.id.root_view)
    RelativeLayout mRootView;

    @BindView(R.id.rv_recycler)
    RecyclerView mRecycler;

    @BindView(R.id.onlinebanner)
    XBanner onlinebanner;

    private List<DataBean.BannerBean> banner;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        getBarDistance(mRootView);
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_onlinemall1;
    }

    @Override
    protected void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallInfo())
                .subscribe(new BaseObserver<DataBean>(mAty) {

                    @Override
                    public void onHandleSuccess(DataBean bean) {

                        banner = bean.getBanner();
                        //刷新数据之后，需要重新设置是否支持自动轮播
                        onlinebanner.setAutoPlayAble(banner.size() > 1);
                        onlinebanner.setIsClipChildrenMode(true);
                        onlinebanner.setBannerData(banner);
                    }

                    @Override
                    public void onHandleError(ApiException apiExc) {
                        super.onHandleError(apiExc);
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

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

                switch (view.getId()) {
                    case R.id.tv_recommend_product:
                        ToastUtil.showToast("文字");
                        break;
                    case R.id.tv_see_more:
                        ToastUtil.showToast("dddd");
                        break;
                }
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
                //1、此处使用的Glide加载图片，可自行替换自己项目中的图片加载框架
                //2、返回的图片路径为Object类型，你只需要强转成你传输的类型就行，切记不要胡乱强转！
                DataBean.BannerBean model1 = (DataBean.BannerBean) model;
                String url = BaseUrl.BASE_URL + model1.getImg();
                Glide.with(mAty).load(url).apply(new RequestOptions()
                        .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) view);

            }
        });
    }


    @OnClick(R.id.img_back)
    public void clickBack() {
        getActivity().finish();
    }
}
