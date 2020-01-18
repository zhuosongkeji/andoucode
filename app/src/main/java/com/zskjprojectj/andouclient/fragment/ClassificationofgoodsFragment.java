package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.azoft.carousellayoutmanager.CarouselLayoutManager;
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener;
import com.azoft.carousellayoutmanager.CenterScrollListener;
import com.azoft.carousellayoutmanager.ItemTransformation;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.library.banner.BannerLayout;
import com.example.library.banner.layoutmanager.BannerLayoutManager;
import com.zhuosongkj.android.library.util.ListUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ClassificationofgoodsActivity;
import com.zskjprojectj.andouclient.adapter.CategoryLevelAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 在线商城商品分类
 */
public class ClassificationofgoodsFragment extends BaseFragment {
    private CategoryLevelAdapter level1Adapter = new CategoryLevelAdapter(R.layout.layout_category_level_1_item);
    private CategoryLevelAdapter level2Adapter = new CategoryLevelAdapter(R.layout.layout_category_level_2_item);
    private CategoryLevelAdapter level3Adapter = new CategoryLevelAdapter(R.layout.layout_category_level_3_item);

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        final CarouselLayoutManager layoutManager1 = new CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true);
        layoutManager1.setPostLayoutListener(new CarouselZoomPostLayoutListener() {
            @Override
            public ItemTransformation transformChild(View child, float itemPositionToCenterDiff, int orientation) {
                final float scale = (float) (2 * (2 * -StrictMath.atan(Math.abs(itemPositionToCenterDiff / 4) + 1.0) / Math.PI + 1));
                return new ItemTransformation(scale, scale, 0, 0);
            }
        });
        layoutManager1.setMaxVisibleItems(5);
        layoutManager1.addOnItemSelectionListener(adapterPosition -> {
            if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                MallGoodsCateBean bean = level1Adapter.getItem(adapterPosition);
                bean.towcate.addAll(bean.towcate);
                level2Adapter.setNewData(bean.towcate);
                level3Adapter.setNewData(new ArrayList<>());
            }
        });
        final RecyclerView recyclerView1 = view.findViewById(R.id.level1RecyclerView);
        recyclerView1.addOnScrollListener(new CenterScrollListener());
        recyclerView1.setLayoutManager(layoutManager1);
        recyclerView1.setHasFixedSize(true);
        level1Adapter.bindToRecyclerView(recyclerView1);


        final BannerLayout recyclerView2 = view.findViewById(R.id.recyclerBanner);
        recyclerView2.setAdapter(level2Adapter);
        recyclerView2.mLayoutManager.setOnPageChangeListener(new BannerLayoutManager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                MallGoodsCateBean bean = level2Adapter.getItem(position);
                HttpRxObservable.getObservable(ApiUtils.getApiService().getMallGoodsCate(bean.id)).subscribe(new BaseObserver<List<MallGoodsCateBean>>(mAty) {
                    @Override
                    public void onHandleSuccess(List<MallGoodsCateBean> mallGoodsCateBeans) throws IOException {
                        mallGoodsCateBeans.addAll(mallGoodsCateBeans);
                        mallGoodsCateBeans.addAll(mallGoodsCateBeans);
                        level3Adapter.setNewData(mallGoodsCateBeans);
                    }
                });
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        level2Adapter.setOnItemClickListener((adapter, view1, position) -> {
            MallGoodsCateBean bean = level2Adapter.getItem(position);
            ClassificationofgoodsActivity.getCataId(bean.id);
        });

        RecyclerView recyclerView = view.findViewById(R.id.level3RecyclerView);
        level3Adapter.bindToRecyclerView(recyclerView);
        level3Adapter.setOnItemClickListener((adapter, view1, position) -> {
            MallGoodsCateBean bean = level3Adapter.getItem(position);
            ClassificationofgoodsActivity.getCataId(bean.id);
        });
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_classificationofgoods;
    }

    @Override
    protected void getDataFromServer() {

        //请求商品分类
        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallGoodsCate()).subscribe(new BaseObserver<List<MallGoodsCateBean>>(mAty) {
            @Override
            public void onHandleSuccess(List<MallGoodsCateBean> mallGoodsCateBeans) throws IOException {
                mallGoodsCateBeans.addAll(mallGoodsCateBeans);
                level1Adapter.setNewData(mallGoodsCateBeans);
            }
        });


    }

    @Override
    protected void initData() {

    }
}
