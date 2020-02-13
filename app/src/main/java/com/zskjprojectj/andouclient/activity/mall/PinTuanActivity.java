package com.zskjprojectj.andouclient.activity.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.bumptech.glide.Glide;
import com.flyco.tablayout.SlidingTabLayout;
import com.google.android.material.appbar.AppBarLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.PinTuanMustAdapter;
import com.zskjprojectj.andouclient.model.PinTuan;
import com.zskjprojectj.andouclient.model.PinTuanGoods;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.util.ArrayList;

import butterknife.BindView;

public class PinTuanActivity extends BaseActivity {

    @BindView(R.id.mustRecyclerView)
    RecyclerView mustRecyclerView;

    @BindView(R.id.fixContainer)
    View fixContainer;

    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;
    @BindView(R.id.viewPager)
    ViewPager viewPager;

    PinTuanMustAdapter pinTuanMustAdapter = new PinTuanMustAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.transparentStatusBar(mActivity);
        BarUtils.setStatusBarLightMode(mActivity, false);
        ActionBarUtil.setTitle(mActivity, "超值团购", false);
        ActionBarUtil.getBackground(mActivity, false).setAlpha(0);
        pinTuanMustAdapter.bindToRecyclerView(mustRecyclerView);
        ((AppBarLayout) findViewById(R.id.appBarLayout))
                .addOnOffsetChangedListener((appBarLayout, verticalOffset) ->
                        ActionBarUtil.getBackground(mActivity, false)
                                .setAlpha(Math.abs(verticalOffset) * 0.01f));
        fixContainer.setPadding(fixContainer.getPaddingLeft(), BarUtils.getStatusBarHeight(), fixContainer.getPaddingEnd(), 0);
        onBind(PinTuan.getTest());
    }

    private void onBind(PinTuan pinTuan) {
        pinTuanMustAdapter.setNewData(pinTuan.mustGoods);
        for (int i = 0; i < pinTuan.recommendGoods.size(); i++) {
            PinTuanGoods goods = pinTuan.recommendGoods.get(i);
            if (i == 0) {
                bindRecommendPinTuanGoods(goods, findViewById(R.id.recommendGoods1));
            } else if (i == 1) {
                bindRecommendPinTuanGoods(goods, findViewById(R.id.recommendGoods2));
            } else {
                bindRecommendPinTuanGoods(goods, findViewById(R.id.recommendGoods3));
            }
        }
        String[] titles = new String[pinTuan.types.size()];
        ArrayList<Fragment> pinTuanFragments = new ArrayList<>();
        for (int i = 0; i < pinTuan.types.size(); i++) {
            PinTuan.PinTuanType pinTuanType = pinTuan.types.get(i);
            titles[i] = pinTuanType.title;
            pinTuanFragments.add(new PinTuanFragment(pinTuanType));
        }
        tabLayout.setViewPager(viewPager, titles, mActivity, pinTuanFragments);
    }

    private void bindRecommendPinTuanGoods(PinTuanGoods goods, View view) {
        Glide.with(mActivity)
                .load(UrlUtil.getImageUrl(goods.img))
                .into((ImageView) view.findViewById(R.id.recommendImg));
        ((TextView) view.findViewById(R.id.recommendTitleTxt)).setText(goods.name);
        ((TextView) view.findViewById(R.id.recommendPriceTxt)).setText(FormatUtil.getMoneyString(goods.pintuanPrice));
        ((TextView) view.findViewById(R.id.recommendPeopleTxt)).setText(goods.people + "人团");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_pin_tuan;
    }
}
