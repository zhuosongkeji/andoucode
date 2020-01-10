package com.zskjprojectj.andouclient.utils;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;

public class ActionBarUtil {

    public static void setTitle(BaseActivity activity, String title) {
        setTitle(activity, title, true);
    }

    public static void setTitle(BaseActivity activity, String title, boolean topOfContentView) {
        setupActionBar(activity, topOfContentView);
        TextView titleTxt = getTitleTxt(activity);
        titleTxt.setText(title);
    }

    private static void setupActionBar(BaseActivity activity, boolean topOfContentView) {
        ViewGroup actionBarContainer = activity.findViewById(R.id.actionBarContainer);
        if (actionBarContainer.getChildCount() == 0) {
            LayoutInflater.from(activity).inflate(R.layout.layout_action_bar, actionBarContainer);
            if (!topOfContentView) {
                RelativeLayout.LayoutParams layoutParams =
                        (RelativeLayout.LayoutParams) activity.findViewById(R.id.contentViewContainer).getLayoutParams();
                layoutParams.removeRule(RelativeLayout.BELOW);
                View actionBarContentView = activity.findViewById(R.id.actionBarContentView);
                actionBarContentView.getLayoutParams().height += BarUtils.getStatusBarHeight();
                actionBarContentView.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0);
            }
        }
        actionBarContainer.findViewById(R.id.backBtn).setOnClickListener(view -> activity.onBackPressed());
    }

    private static TextView getTitleTxt(BaseActivity activity) {
        return activity.findViewById(R.id.actionBarTitleTxt);
    }

    public static View getBackground(BaseActivity activity, boolean topOfContentView) {
        setupActionBar(activity, topOfContentView);
        return activity.findViewById(R.id.actionBarBackground);
    }

    public static void setBackEnable(BaseActivity activity, boolean enable) {
        activity.findViewById(R.id.backBtn).setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }
}
