package com.zskjprojectj.andouclient.activity;

import android.graphics.Color;
import android.os.Bundle;


import androidx.fragment.app.Fragment;


import com.next.easynavigation.view.EasyNavigationBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.fragment.ClassificationofgoodsFragment;
import com.zskjprojectj.andouclient.fragment.MallFocusonFragment;
import com.zskjprojectj.andouclient.fragment.MallInfoFragment;
import com.zskjprojectj.andouclient.fragment.mall.MallHomepageFragment1;

import java.util.ArrayList;
import java.util.List;

/**
 * 商城首页
 */
public class MallMainActivity extends BaseActivity {


    private EasyNavigationBar navigationBar;
    //定义字体颜色
    private int normalTextColor= Color.parseColor("#646464");
    private int selectTextColor=Color.parseColor("#5ED3AE");
    private String[] tabText = {"首页", "分类", "关注", "消息"};
    //未选中icon
    private int[] normalIcon = {R.mipmap.home_icon_uncheck, R.mipmap.classification_icon_uncheck, R.mipmap.shopping_cart_uncheck, R.mipmap.me_icon_uncheck};
    //选中时icon
    private int[] selectIcon = {R.mipmap.home_icon_check, R.mipmap.classification_icon_check,  R.mipmap.shopping_cart_check, R.mipmap.me_icon_check};
    private List<Fragment> fragments = new ArrayList<>();
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mallmain);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
      //  topView.setTitle("在线商城");
}

    @Override
    protected void initViews() {
        navigationBar = findViewById(R.id.navigationBar);
        fragments.add(new MallHomepageFragment1());
        fragments.add(new ClassificationofgoodsFragment());
        fragments.add(new MallFocusonFragment());
        fragments.add(new MallInfoFragment());
        navigationBar.titleItems(tabText)
                .normalIconItems(normalIcon)
                .selectIconItems(selectIcon)
                .fragmentList(fragments)
                .normalTextColor(normalTextColor)
                .selectTextColor(selectTextColor)
                .fragmentManager(getSupportFragmentManager())
                .canScroll(true)
                .build();
    }

    @Override
    public void getDataFromServer() {

    }


    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    public EasyNavigationBar getNavigationBar() {
        return navigationBar;
    }
}
