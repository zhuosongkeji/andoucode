package com.zskjprojectj.andouclient.view;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zskjprojectj.andouclient.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RedPacketViewHolder {
    @BindView(R.id.iv_close)
    ImageView mIvClose;

//    @BindView(R.id.iv_avatar)
//    ImageView mIvAvatar;
//
//    @BindView(R.id.tv_name)
//    TextView mTvName;
//
//    @BindView(R.id.tv_msg)
//    TextView mTvMsg;
//    @BindView(R.id.tv_money)
//    TextView mTvmoney;
   @BindView(R.id.iv_open)
    ImageView mIvOpen;
    private Context mContext;
    private OnRedPacketDialogClickListener mListener;
//    private int[] mImgResIds = new int[]{
//            R.mipmap.icon_open_red_packet1,
//            R.mipmap.icon_open_red_packet2,
//            R.mipmap.icon_open_red_packet3,
//            R.mipmap.icon_open_red_packet4,
//            R.mipmap.icon_open_red_packet5,
//            R.mipmap.icon_open_red_packet6,
//            R.mipmap.icon_open_red_packet7,
//            R.mipmap.icon_open_red_packet7,
//            R.mipmap.icon_open_red_packet8,
//            R.mipmap.icon_open_red_packet9,
//            R.mipmap.icon_open_red_packet4,
//            R.mipmap.icon_open_red_packet10,
//            R.mipmap.icon_open_red_packet11,
//    };
//    private FrameAnimation mFrameAnimation;
    public RedPacketViewHolder(Context context, View view) {
        mContext = context;
        ButterKnife.bind(this, view);
    }
    @OnClick({R.id.iv_close, R.id.iv_open})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
               // stopAnim();
                if (mListener != null) {
                    mListener.onCloseClick();
                }
                break;

            case R.id.iv_open:
//                if (mFrameAnimation != null) {
//                    //如果正在转动，则直接返回
//                    return;
//                }
//
//                startAnim();

                if (mListener != null) {
                    mListener.onOpenClick();
                }
                break;
        }
    }
//    public void setData(RedPacketEntity entity) {
//        RequestOptions options = new RequestOptions();
//        options.centerCrop()
//                .circleCrop();
//
//        Glide.with(mContext)
//                .load(entity.avatar)
//                .apply(options)
//                .into(mIvAvatar);
//
//        mTvName.setText(entity.name);
//        mTvMsg.setText(entity.remark);
//        mTvmoney.setText(entity.money);
//    }
//    public void startAnim() {
//        handler.sendEmptyMessageDelayed(100,1000);
//        mFrameAnimation = new FrameAnimation(mIvOpen, mImgResIds, 125, true);
//        mFrameAnimation.setAnimationListener(new FrameAnimation.AnimationListener() {
//            @Override
//            public void onAnimationStart() {
//                Log.i("", "start");
//            }
//
//            @Override
//            public void onAnimationEnd() {
//                Log.i("", "end");
//            }
//
//            @Override
//            public void onAnimationRepeat() {
//                Log.i("", "repeat");
//            }
//
//            @Override
//            public void onAnimationPause() {
//                mIvOpen.setBackgroundResource(R.mipmap.icon_open_red_packet1);
//            }
//        });
//    }
//    public void stopAnim() {
//        if (mFrameAnimation != null) {
//            mFrameAnimation.release();
//            mFrameAnimation = null;
//        }
//    }
    public void setOnRedPacketDialogClickListener(OnRedPacketDialogClickListener listener) {
        mListener = listener;
    }
//    private Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//            if(msg.what==100){
//                if(count>0){
//                    count--;
//                    handler.sendEmptyMessageDelayed(100,1000);
//                }else {
//                    mTvmoney.setVisibility(View.VISIBLE);
//                    mIvOpen.setVisibility(View.GONE);
//                }
//            }
//        }
//    };
}
