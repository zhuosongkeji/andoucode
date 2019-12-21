package com.zskjprojectj.andouclient.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

import androidx.annotation.Nullable;

import recycler.coverflow.RecyclerCoverFlow;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.utils
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/21 11:13
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class ScrollRecyclerView extends RecyclerCoverFlow {

    private float mLastXIntercept = 0f;
    private float mLastYIntercept = 0f;

    public ScrollRecyclerView(Context context) {
        super(context);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {


        int x = (int) e.getX();
        int y = (int) e.getY();

        switch (e.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                getParent().requestDisallowInterceptTouchEvent(true);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //横坐标位移增量
                float deltaX = x - mLastXIntercept;
                //纵坐标位移增量
                float deltaY = y - mLastYIntercept;
                if (Math.abs(deltaX) < Math.abs(deltaY)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                break;
            }
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        return super.dispatchTouchEvent(e);
    }
}
