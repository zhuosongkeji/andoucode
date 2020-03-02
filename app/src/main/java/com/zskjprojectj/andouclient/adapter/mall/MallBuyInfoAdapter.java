package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.entity.mall.MallSettlementBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/2 10:30
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallBuyInfoAdapter extends BaseQuickAdapter<MallSettlementBean.DetailsBean, BaseViewHolder> {


    public MallBuyInfoAdapter(int layoutResId, @Nullable List<MallSettlementBean.DetailsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallSettlementBean.DetailsBean item) {

        Glide.with(mContext)
                .load(UrlUtil.INSTANCE.getImageUrl(item.getImg()))
                .apply(new RequestOptions()
                        .placeholder(R.mipmap.ic_placeholder)
                )
                .into((ImageView) helper.getView(R.id.mOrderImg));

        helper.setText(R.id.tv_online_order_name, item.getName());
        helper.setText(R.id.tv_online_order_price, "¥" + item.getPrice());

        StringBuffer buffer = new StringBuffer();
        List<String> attr_value = item.getAttr_value();
        for (String s : attr_value) {
            buffer.append(s).append("-");
        }
        String stringOption = buffer.substring(0, buffer.length() - 1);
        helper.setText(R.id.tv_online_order_option, stringOption);
        helper.setText(R.id.tv_online_order_number, "X" + item.getNum());


    }
}
