package com.zhuosongkj.android.library.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.BarUtils;
import com.zhuosongkj.android.library.R;
import com.zhuosongkj.android.library.app.BaseActivity;

public class ActionBarUtil {

    public static void setTitle(BaseActivity activity, String title) {
        setTitle(activity, title, true);
    }

    public static void setTitle(BaseActivity activity, String title, boolean topOfContentView) {
        setupActionBar(activity, topOfContentView);
        TextView titleTxt = getTitleTxt(activity);
        titleTxt.setText(title);
    }

    public static void setRightAction(BaseActivity activity, String actionStr, View.OnClickListener onClickListener) {
        setupActionBar(activity, true);
        TextView rightActionTxt = getRightActionTxt(activity);
        rightActionTxt.setText(actionStr);
        rightActionTxt.setOnClickListener(onClickListener);
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

    private static TextView getRightActionTxt(BaseActivity activity) {
        return activity.findViewById(R.id.rightActionTxt);
    }

    public static View getBackground(BaseActivity activity, boolean topOfContentView) {
        setupActionBar(activity, topOfContentView);
        return activity.findViewById(R.id.actionBarBackground);
    }

    public static void setBackEnable(BaseActivity activity, boolean enable) {
        activity.findViewById(R.id.backBtn).setVisibility(enable ? View.VISIBLE : View.INVISIBLE);
    }
}
