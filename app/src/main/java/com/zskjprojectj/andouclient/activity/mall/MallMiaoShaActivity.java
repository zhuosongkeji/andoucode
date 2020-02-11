package com.zskjprojectj.andouclient.activity.mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.mall.MallMiaoShaFragment;
import com.zskjprojectj.andouclient.fragment.mall.MiaoSha;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class MallMiaoShaActivity extends BaseActivity {
    ArrayList<Fragment> fragments = new ArrayList<>();

    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabLayout)
    SlidingTabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BarUtils.transparentStatusBar(mActivity);
        BarUtils.setStatusBarLightMode(mActivity, false);
        ActionBarUtil.setTitle(mActivity, "限时秒杀", false);
        ActionBarUtil.getBackground(mActivity, false).setAlpha(0);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.postDelayed(() -> {
            LinearLayout parent = (LinearLayout) tabLayout.getChildAt(0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                TextView tabTitle = child.findViewById(R.id.tv_tab_title);
                tabTitle.setSingleLine(false);
                tabTitle.setLineSpacing(SizeUtils.dp2px(5), 1f);
            }
        }, 1);
        onBind(MiaoSha.getTests());
    }

    private void onBind(ArrayList<MiaoSha> miaoShas) {
        String[] titles = new String[miaoShas.size()];
        int index = 0;
        for (int i = 0; i < miaoShas.size(); i++) {
            MiaoSha miaoSha = miaoShas.get(i);
            titles[i] = miaoSha.time + "\n" + miaoSha.state.title;
            fragments.add(new MallMiaoShaFragment(miaoSha));
            if (miaoSha.state == MiaoSha.State.JIN_XING_ZHONG){
                index = i;
            }
        }
        tabLayout.setViewPager(
                viewPager,
                titles,
                mActivity,
                fragments);
        tabLayout.setCurrentTab(index);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mall_miao_sha;
    }
}
