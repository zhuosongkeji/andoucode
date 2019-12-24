package com.zskjprojectj.andouclient.fragment.mall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;


import com.stx.xhb.xbanner.XBanner;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.adapter.mall.RecommendProductsAdapter;
import com.zskjprojectj.andouclient.adapter.mall.SpecialProductsAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.mall.DataBean;
import com.zskjprojectj.andouclient.http.ApiException;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.ScreenUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

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


    @BindView(R.id.root_view)
    RelativeLayout mRootView;


    @BindView(R.id.onlinebanner)
    XBanner onlinebanner;

    //推荐产品
    @BindView(R.id.rv_recommend_products)
    RecyclerView mRecommendProducts;

    //特价产品
    @BindView(R.id.rv_special_products)
    RecyclerView mSpecialProducts;

    private List<DataBean.BannerBean> banner;
    private List<DataBean.RecommendGoodsBean> recommend_goods;
    private List<DataBean.BargainGoodsBean> bargain_goods;

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
                        //推荐产品
                        recommend_goods = bean.getRecommend_goods();
                        //特价产品
                        bargain_goods = bean.getBargain_goods();

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

        //推荐产品
        mRecommendProducts.setLayoutManager(new GridLayoutManager(getActivity(),2));
        RecommendProductsAdapter recommendProductsAdapter=new RecommendProductsAdapter(R.layout.fragment_mall_goods_details_view,recommend_goods);
        recommendProductsAdapter.openLoadAnimation();
        recommendProductsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), MallGoodsDetailsActivity.class));
            }
        });


        //特价产品
        mSpecialProducts.setLayoutManager(new GridLayoutManager(getActivity(),2));
        SpecialProductsAdapter specialProductsAdapter=new SpecialProductsAdapter(R.layout.fragment_mall_goods_details_view,bargain_goods);
        specialProductsAdapter.openLoadAnimation();
        specialProductsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getActivity(), MallGoodsDetailsActivity.class));
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
