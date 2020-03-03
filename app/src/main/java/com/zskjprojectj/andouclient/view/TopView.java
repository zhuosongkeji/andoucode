package com.zskjprojectj.andouclient.view;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.Util;


/**
 * 顶部title
 * 封装的顶部
 * 根据不同情况右边显示不显示，
 * 显示图片和文字选择
 */
public class TopView extends RelativeLayout {
    private Context mContext;
    public ImageView mBackImage;
    private TextView mTitleView, mRightTitleView;
    private ImageView mRightMenu;

    LayoutParams mBackParams;
    LayoutParams mTitleParams;
    LayoutParams mMenuParams;
    LayoutParams mRightTitleParams;
    public final static int WARP_CONTENT = LayoutParams.WRAP_CONTENT;
    public final static int MATCH_PARENT = LayoutParams.MATCH_PARENT;

    public TopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopView(Context context) {
        this(context, null);
    }

    String mTitle;
    String mRighttitle;
    int mLeftImage;
    int rightImage;
    int dp8;

    public TopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        if (isInEditMode())
            return;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TopView, defStyleAttr, 0);
        for (int i = 0; i < array.length(); i++) {
            int attr = array.getIndex(i);
            switch (attr) {
                /**
                 * 左边图标
                 */
                case R.styleable.TopView_topview_left:
                    mLeftImage = array.getResourceId(attr, -1);
                    break;
                /**
                 * 中间文字
                 */
                case R.styleable.TopView_topview_title:
                    mTitle = array.getString(attr);
                    break;
                /**
                 * 右边图片的情况
                 */
                case R.styleable.TopView_topview_right:
                    rightImage = array.getResourceId(attr, -1);
                    break;
                /**
                 * 右边文字的情况
                 */
                case R.styleable.TopView_topview_righttitle:
                    mRighttitle = array.getString(attr);
                    break;
                default:
                    break;
            }
        }
        //设置内容
        dp8 = Util.dp2px(getResources(), 8);
        mBackImage = new ImageView(mContext);
        mTitleView = new TextView(mContext);
        mRightTitleView = new TextView(mContext);
        mRightMenu = new ImageView(mContext);

        //设置位置
        mBackParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mTitleParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mMenuParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mRightTitleParams = new LayoutParams(WARP_CONTENT, WARP_CONTENT);
        mTitleParams.addRule(CENTER_IN_PARENT);
        mRightTitleParams.addRule(ALIGN_PARENT_RIGHT);
        mRightTitleParams.addRule(CENTER_VERTICAL);
        mMenuParams.addRule(ALIGN_PARENT_RIGHT);
        mMenuParams.addRule(CENTER_VERTICAL);
        mRightMenu.setPadding(dp8, 0, dp8, 0);
        mBackParams.addRule(CENTER_VERTICAL);
        mBackImage.setPadding(dp8, 0, dp8, 0);
        //添加位置
        addView(mBackImage, mBackParams);
        addView(mTitleView, mTitleParams);
        addView(mRightTitleView, mRightTitleParams);
        addView(mRightMenu, mMenuParams);
        mRightTitleView.setText(mRighttitle);
        mRightTitleView.setPadding(dp8, 0, dp8, 0);
        mTitleView.setText(mTitle);
        mTitleView.setMaxLines(1);
        mTitleView.setPadding(5 * dp8, 0, 5 * dp8, 0);
        if (rightImage > 0) {
            mRightMenu.setImageResource(rightImage);
        }
        if (mLeftImage > 0) {
            mBackImage.setImageResource(mLeftImage);
        }
        /**
         * 设置标题背景颜色
         */
        setBackgroundResource(R.color.colorNavy);
        //设置标题颜色
        mTitleView.setTextColor(ContextCompat.getColor(context, R.color.white));
        mRightTitleView.setTextColor(ContextCompat.getColor(context, R.color.white));
        array.recycle();
    }

    public void setTitle(String text) {
        mTitleView.setText(text);
    }

    public void setRightTitle(String text) {
        mRightTitleView.setText(text);
    }

    public void setRightMenuRes(int resId) {
        this.mRightMenu.setImageResource(resId);
    }

    public void setBackImageRes(int resid) {
        this.mBackImage.setImageResource(resid);
    }

    //右边图片点击事件
    public void setRightMenuListener(OnClickListener mClick) {
        if (mClick != null) {
            mRightMenu.setOnClickListener(mClick);
        }
    }

    //设置右边文字点击事件
    public void setRightTextListener(OnClickListener mTextClick) {
        if (mTextClick != null) {
            mRightTitleView.setOnClickListener(mTextClick);
        }
    }

    public void setBackOnClickListener(OnClickListener mL) {
        if (mL != null) {
            mBackImage.setOnClickListener(mL);
        }
    }

    /**
     * 設置返回按鈕
     */
    public void setFinishActivity(final Activity mActivity) {
        mBackImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mActivity.finish();
            }
        });
    }

}
