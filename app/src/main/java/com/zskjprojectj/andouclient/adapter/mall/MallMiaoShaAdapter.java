package com.zskjprojectj.andouclient.adapter.mall;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.fragment.mall.MiaoSha;
import com.zskjprojectj.andouclient.model.MiaoShaGoods;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class MallMiaoShaAdapter extends BaseAdapter<MiaoShaGoods> {
    public MiaoSha miaoSha;

    public MallMiaoShaAdapter() {
        super(R.layout.miaosha_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MiaoShaGoods item) {
        helper.setText(R.id.tv_title, item.name)
                .setText(R.id.tv_progress, "已抢" + item.kill_percent + "%")
                .setProgress(R.id.progressbar, item.kill_percent)
                .setText(R.id.tv_price, "￥" + FormatUtil.getMoneyString(item.kill_price))
                .setText(R.id.original_price, "￥" + FormatUtil.getMoneyString(item.price))
                .setGone(R.id.hot_buy, miaoSha.getState() == MiaoSha.State.JIN_XING_ZHONG);
        if (miaoSha.getState() != MiaoSha.State.JIN_XING_ZHONG) {
            helper.setText(R.id.tv_buy_now, miaoSha.getState().title);
        } else {
            helper.setText(R.id.tv_buy_now, "马上抢");
        }
        TextView textView = helper.getView(R.id.original_price);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        helper.addOnClickListener(R.id.tv_buy_now);
        Glide.with(mContext).load(UrlUtil.getImageUrl(item.img)).into((ImageView) helper.getView(R.id.miaoshaimg));
    }
}
