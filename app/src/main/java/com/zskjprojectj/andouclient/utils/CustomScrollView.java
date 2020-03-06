package com.zskjprojectj.andouclient.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.utils
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/21 11:28
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CustomScrollView extends ScrollView {
    private float mLastXIntercept = 0f;
    private float mLastYIntercept = 0f;

    public CustomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean intercepted = false;
        float x = ev.getX();
        float y = ev.getY();
        int action = ev.getAction() & MotionEvent.ACTION_MASK;
        switch (action) {
            case MotionEvent.ACTION_DOWN: {
                intercepted = false;
                //初始化mActivePointerId
                super.onInterceptTouchEvent(ev);
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                //横坐标位移增量
                float deltaX = x - mLastXIntercept;
                //纵坐标位移增量
                float deltaY = y - mLastYIntercept;
                intercepted = Math.abs(deltaX) < Math.abs(deltaY);
                break;
            }
            case MotionEvent.ACTION_UP: {
                intercepted = false;
                break;
            }
        }
        mLastXIntercept = x;
        mLastYIntercept = y;
        return intercepted;
    }
}
