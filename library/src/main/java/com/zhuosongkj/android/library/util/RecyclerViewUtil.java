package com.zhuosongkj.android.library.util;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

public class RecyclerViewUtil {
    public static void smoothScrollToPositionTop(Context context, RecyclerView foodRecyclerView, int position) {
        RecyclerView.SmoothScroller smoothScroller = new LinearSmoothScroller(context) {
            @Override
            protected int getVerticalSnapPreference() {
                return LinearSmoothScroller.SNAP_TO_START;
            }
        };
        smoothScroller.setTargetPosition(position);
        foodRecyclerView.getLayoutManager().startSmoothScroll(smoothScroller);
    }

    public static LinearLayoutManager getLinearLayoutParams(RecyclerView recyclerView) {
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof LinearLayoutManager) {
            return (LinearLayoutManager) manager;
        }
        return null;
    }

    public static void disableItemAnimator(RecyclerView recyclerView){
        RecyclerView.ItemAnimator animator = recyclerView.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }
    }
}
