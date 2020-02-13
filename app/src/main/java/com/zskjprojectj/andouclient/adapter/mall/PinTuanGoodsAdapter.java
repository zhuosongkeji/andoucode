package com.zskjprojectj.andouclient.adapter.mall;

import android.graphics.Paint;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.PinTuanGoods;

public class PinTuanGoodsAdapter extends BaseAdapter<PinTuanGoods> {
    public PinTuanGoodsAdapter() {
        super(R.layout.layout_pin_tuan_goods_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PinTuanGoods item) {
        helper.setText(R.id.titleTxt, item.name)
                .setText(R.id.peopleTxt, item.joinPeople + "已拼团")
                .setProgress(R.id.progressBar, item.progress)
                .setText(R.id.newPriceTxt, FormatUtil.getMoneyString(item.pintuanPrice));
        TextView textView = helper.getView(R.id.oldPriceTxt);
        textView.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        textView.setText("￥" + FormatUtil.getMoneyString(item.price));
        Glide.with(mContext)
                .load(item.img)
                .into((ImageView) helper.getView(R.id.img));
    }
}
