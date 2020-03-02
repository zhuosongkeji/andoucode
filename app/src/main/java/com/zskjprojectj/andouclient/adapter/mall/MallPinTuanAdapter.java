package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.TimeUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.PinTuanDetails;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;


public class MallPinTuanAdapter extends BaseAdapter<PinTuanDetails.TeamListBean> {
    Timer timer = new Timer();
    long offset;
    private TextView hourTxt;
    private TextView minuteTxt;
    private TextView secondTxt;
    private String endTime;

    public MallPinTuanAdapter() {
        super(R.layout.mall_pintuan_view);
    }

    public void setEndTime(String endtime) {
        this.endTime = endtime;
    }


    @Override
    protected void convert(BaseViewHolder helper, PinTuanDetails.TeamListBean item) {

        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.getCaptain_avatar())).into((ImageView) helper.getView(R.id.IvHeadPic));
        helper.setText(R.id.tv_pintuan_person, item.getLeft_member() + "人");
        helper.addOnClickListener(R.id.mBuyNow);

        hourTxt = helper.getView(R.id.hourTxt);
        minuteTxt = helper.getView(R.id.minuteTxt);
        secondTxt = helper.getView(R.id.secondTxt);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                helper.getView(R.id.timerContainer).postDelayed(() -> countTime(), 1);
            }
        }, 0, 1000);
    }


    private void countTime() {
        Date date = TimeUtils.string2Date(endTime, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        long endTime = calendar.getTime().getTime();
        long currentTime = System.currentTimeMillis();
        offset = (endTime - currentTime) / 1000;
        if (offset > 0) {
            secondTxt.setText(String.valueOf(offset % 60));
            minuteTxt.setText(String.valueOf(offset / 60 % 60));
            hourTxt.setText(String.valueOf(offset / 60 / 60));
            offset -= 1;
        }
    }


}
