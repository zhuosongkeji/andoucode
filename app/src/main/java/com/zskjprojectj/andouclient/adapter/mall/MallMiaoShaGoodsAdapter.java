package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.MiaoShaGoods;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class MallMiaoShaGoodsAdapter extends BaseAdapter<MiaoShaGoods> {

    public MallMiaoShaGoodsAdapter() {
        super(R.layout.goods_miaosha_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MiaoShaGoods item) {
        helper.setText(R.id.priceTxt, "¥" + FormatUtil.getMoneyString(item.kill_price));
        helper.itemView.postDelayed(() -> {
            ImageView img = helper.itemView.findViewById(R.id.img);
            img.getLayoutParams().height = img.getWidth();
            Glide.with(helper.itemView.getContext())
                    .load(UrlUtil.INSTANCE.getImageUrl(item.img))
                    .into((ImageView) helper.getView(R.id.img));
        }, 1);
    }
}
