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
        BarUtils.setStatusBarColor(mActivity, Color.TRANSPARENT);
        ActionBarUtil.setTitle(mActivity, "限时秒杀",true);
//        ActionBarUtil.getBackground(mActivity,false).setAlpha(0);
        fragments.add(new MallMiaoShaFragment());
        fragments.add(new MallMiaoShaFragment());
        fragments.add(new MallMiaoShaFragment());
        fragments.add(new MallMiaoShaFragment());
        fragments.add(new MallMiaoShaFragment());
        tabLayout.setViewPager(
                viewPager,
                new String[]{"08:00\n已开抢", "09:00\n已开抢", "10:00\n抢购进行中", "11:00\n即将开场","12:00\n即将开场"},
                mActivity,
                fragments);
        tabLayout.post(() -> {
            LinearLayout parent = (LinearLayout) tabLayout.getChildAt(0);
            for (int i = 0; i < parent.getChildCount(); i++) {
                View child = parent.getChildAt(i);
                TextView tabTitle = child.findViewById(R.id.tv_tab_title);
                tabTitle.setSingleLine(false);
                tabTitle.setLineSpacing(SizeUtils.dp2px(5),1f);
            }
        });
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_mall_miao_sha;
    }
}
