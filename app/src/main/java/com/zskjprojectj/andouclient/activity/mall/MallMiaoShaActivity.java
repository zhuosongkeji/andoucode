package com.zskjprojectj.andouclient.activity.mall;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.blankj.utilcode.util.BarUtils;
import com.blankj.utilcode.util.SizeUtils;
import com.flyco.tablayout.SlidingTabLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.mall.MallMiaoShaFragment;
import com.zskjprojectj.andouclient.fragment.mall.MiaoSha;

import java.util.ArrayList;

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
        viewPager.setOffscreenPageLimit(miaoShas.size());
        String[] titles = new String[miaoShas.size()];
        for (int i = 0; i < miaoShas.size(); i++) {
            MiaoSha miaoSha = miaoShas.get(i);
            titles[i] = miaoSha.startTime;
            MallMiaoShaFragment mallMiaoShaFragment = new MallMiaoShaFragment(miaoSha);
            int finalI = i;
            mallMiaoShaFragment.setOnStateReceiveListener(state -> {
                TextView titleTxt = tabLayout.getTitleView(finalI);
                if (!titleTxt.getText().toString().contains("\n")) {
                    titleTxt.setText(titleTxt.getText() + "\n" + state.title);
                }
                if (miaoSha.getState() == MiaoSha.State.JIN_XING_ZHONG) {
                    tabLayout.setCurrentTab(finalI);
                }
            });
            fragments.add(mallMiaoShaFragment);
        }
        tabLayout.setViewPager(viewPager, titles, mActivity, fragments);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mall_miao_sha;
    }
}
