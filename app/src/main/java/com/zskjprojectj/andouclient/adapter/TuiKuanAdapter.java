package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.OrderDetail;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class TuiKuanAdapter extends BaseAdapter<OrderDetail.Goodsdetail> {

    public TuiKuanAdapter() {
        super(R.layout.item_tui_kuan_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, OrderDetail.Goodsdetail item) {
        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.img)).apply(new RequestOptions()
                .placeholder(R.mipmap.ic_placeholder)).into((ImageView) helper.getView(R.id.img_picleft));
        helper.setText(R.id.tv_goods_details,getSpec(item.attr_value));
        helper.setText(R.id.tv_goods_name,item.name);
        helper.setText(R.id.tv_goods_price,"¥" + item.price);
        helper.setText(R.id.tv_goods_num,"X" + item.num);
    }

    private String getSpec(String[] attr_value) {
        StringBuilder builder = new StringBuilder();
        for (String s : attr_value) {
            builder.append(s).append("+");
        }
        return builder.substring(0, builder.length() - 1);
    }
}
