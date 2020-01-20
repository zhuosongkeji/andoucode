package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;


import androidx.fragment.app.Fragment;


import com.next.easynavigation.view.EasyNavigationBar;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.ClassificationofgoodsFragment;
import com.zskjprojectj.andouclient.fragment.MallShoppingFragment;
import com.zskjprojectj.andouclient.fragment.MallInfoFragment;
import com.zskjprojectj.andouclient.fragment.mall.MallHomepageFragment1;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城首页
 */
public class MallMainActivity extends BaseActivity {


    private EasyNavigationBar navigationBar;
    //定义字体颜色
    private int normalTextColor = Color.parseColor("#646464");
    private int selectTextColor = Color.parseColor("#5ED3AE");
    private String[] tabText = {"首页", "分类", "购物车"};//我的
    //未选中icon
    private int[] normalIcon = {R.mipmap.home_icon_uncheck, R.mipmap.classification_icon_uncheck, R.mipmap.shopping_cart_uncheck};//, R.mipmap.me_icon_uncheck
    //选中时icon
    private int[] selectIcon = {R.mipmap.home_icon_check, R.mipmap.classification_icon_check, R.mipmap.shopping_cart_check};//, R.mipmap.me_icon_check
    private List<Fragment> fragments = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        initData();
    }

    private void initData() {


        String id = getIntent().getStringExtra("id");
        if ("MallShopping".equals(id)) {
            navigationBar.selectTab(2);

        }



        String tag = getIntent().getStringExtra("tag");
        if ("backHome".equals(tag)) {
            navigationBar.selectTab(0);
        }
    }

    private void initViews() {
        navigationBar = findViewById(R.id.navigationBar);
        //首页
        fragments.add(new MallHomepageFragment1());
        //分类
        fragments.add(new ClassificationofgoodsFragment());
        //购物车
        fragments.add(new MallShoppingFragment());
//        fragments.add(new MallInfoFragment());
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .normalTextColor(normalTextColor)
                .selectTextColor(selectTextColor)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .onTabClickListener(new EasyNavigationBar.OnTabClickListener() {
                    @Override
                    public boolean onTabClickEvent(View view, int position) {
                        switch (position) {
                            case 0:
                                ActionBarUtil.setVisible(mActivity, false);
                                break;
                            case 1:
                                ActionBarUtil.setTitle(mActivity, "商品分类");
                                break;
                            case 2:
                                ActionBarUtil.setTitle(mActivity, "购物车");
                                break;

                        }
                        return false;
                    }
                })
                .build();
        navigationBar.selectTab(0);
    }


    @Override
    protected int getContentView() {
        return R.layout.activity_mallmain;
    }


}
