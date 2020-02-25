package com.zskjprojectj.andouclient.fragment.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;


import com.stx.xhb.xbanner.XBanner;
import com.wihaohao.PageGridView;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MallGoodsListActivity;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.activity.mall.MallMiaoShaActivity;
import com.zskjprojectj.andouclient.activity.mall.MallSearchGoodsActivity;
import com.zskjprojectj.andouclient.activity.mall.PinTuanActivity;
import com.zskjprojectj.andouclient.adapter.mall.RecommendProductsAdapter;
import com.zskjprojectj.andouclient.adapter.mall.SpecialProductsAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean;
import com.zskjprojectj.andouclient.http.ApiException;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.ScreenUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class MallHomepageFragment1 extends BaseFragment {


    @BindView(R.id.onlinebanner)
    XBanner onlinebanner;

    //推荐产品
    @BindView(R.id.rv_recommend_products)
    RecyclerView mRecommendProducts;

    //特价产品
    @BindView(R.id.rv_special_products)
    RecyclerView mSpecialProducts;

    @BindView(R.id.vp_grid_view)
    PageGridView mGridView;


    private List<MallHomeDataBean.BannerBean> banner;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_onlinemall1;
    }

    @Override
    protected void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallInfo())
                .subscribe(new BaseObserver<MallHomeDataBean>(mAty) {

                    @Override
                    public void onHandleSuccess(MallHomeDataBean bean) {

                        banner = bean.getBanner();
                        //分类
                        List<MallHomeDataBean.CategoryBean> category = bean.getCategory();
//                        initCategoryRecycler(category);

                        mGridView.setData(category);
                        mGridView.setOnItemClickListener(new PageGridView.OnItemClickListener() {
                            @Override
                            public void onItemClick(int position) {
                                MallGoodsListActivity.Companion.start(null, null, category.get(position).getId());
                            }
                        });

                        //推荐产品
                        List<MallHomeDataBean.RecommendGoodsBean> recommend_goods = bean.getRecommend_goods();
                        initRecommendAdapter(recommend_goods);
                        //特价产品
                        List<MallHomeDataBean.BargainGoodsBean> bargain_goods = bean.getBargain_goods();
                        initBargainAdapter(bargain_goods);

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


    private void initBargainAdapter(List<MallHomeDataBean.BargainGoodsBean> bargain_goods) {
        //特价产品
        mSpecialProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        SpecialProductsAdapter specialProductsAdapter = new SpecialProductsAdapter(R.layout.fragment_mall_goods_details_view, bargain_goods);
        specialProductsAdapter.openLoadAnimation();
        specialProductsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MallGoodsDetailsActivity.start(bargain_goods.get(position).getId(),"PUTONG");
            }
        });

        mSpecialProducts.setAdapter(specialProductsAdapter);
    }

    private void initRecommendAdapter(List<MallHomeDataBean.RecommendGoodsBean> recommend_goods) {
        //推荐产品
        mRecommendProducts.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        RecommendProductsAdapter recommendProductsAdapter = new RecommendProductsAdapter(R.layout.fragment_mall_goods_details_view, recommend_goods);
        recommendProductsAdapter.openLoadAnimation();
        recommendProductsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

                MallGoodsDetailsActivity.start(recommend_goods.get(position).getId(),"PUTONG");
            }
        });

        mRecommendProducts.setAdapter(recommendProductsAdapter);

    }

    @Override
    protected void initData() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ScreenUtil.getScreenWidth(mAty) / 2);
        onlinebanner.setLayoutParams(layoutParams);
        initBanner(onlinebanner);
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
                MallHomeDataBean.BannerBean model1 = (MallHomeDataBean.BannerBean) model;
                String url = UrlUtil.getImageUrl(model1.getImg());
                Glide.with(mAty).load(url).apply(new RequestOptions()
                        .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) view);

            }
        });
    }


    @OnClick({R.id.rl_search, R.id.img_back, R.id.tv_recommend_see_more, R.id.tv_special_see_more, R.id.search_image})
    public void clickBack(View view) {

        switch (view.getId()) {

            case R.id.rl_search:
                MallSearchGoodsActivity.Companion.start();
                break;
            case R.id.img_back:
                mAty.finish();
                break;
            //推荐
            case R.id.tv_recommend_see_more:
                MallGoodsListActivity.Companion.start("1", null, null);
                break;
            //特价
            case R.id.tv_special_see_more:
                MallGoodsListActivity.Companion.start(null, "1", null);
                break;
            //搜索按钮
            case R.id.search_image:
                break;
        }


    }

    @OnClick(R.id.miaoShaEntry)
    void onMiaoShaEntryClick() {
        ActivityUtils.startActivity(MallMiaoShaActivity.class);
    }

    @OnClick(R.id.pinTuanEntry)
    void onPinTuanEntryClick() {
        ActivityUtils.startActivity(PinTuanActivity.class);
    }


}
