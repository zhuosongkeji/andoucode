package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.PinTuanGoods;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class PinTuanMustAdapter extends BaseAdapter<PinTuanGoods> {
    public PinTuanMustAdapter() {
        super(R.layout.layout_pin_tuan_must_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, PinTuanGoods item) {
        helper.setText(R.id.priceTxt, FormatUtil.getMoneyString(item.pintuanPrice))
                .setText(R.id.titleTxt, item.name)
                .setText(R.id.peopleCountTxt, item.people + "人团");
        helper.itemView.postDelayed(() -> {
            ImageView img = helper.itemView.findViewById(R.id.img);
            img.getLayoutParams().height = img.getWidth();
            Glide.with(helper.itemView.getContext())
                    .load(UrlUtil.getImageUrl(item.img))
                    .into((ImageView) helper.getView(R.id.img));
        }, 1);
    }
}
