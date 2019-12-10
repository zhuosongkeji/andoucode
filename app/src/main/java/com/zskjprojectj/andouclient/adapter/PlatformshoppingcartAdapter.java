package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.PlatformshoppingcartBean;
import com.zskjprojectj.andouclient.utils.GlideTool;

import java.util.List;

public class PlatformshoppingcartAdapter extends BaseQuickAdapter<PlatformshoppingcartBean, BaseViewHolder> {
    public PlatformshoppingcartAdapter(int layoutResId, @Nullable List<PlatformshoppingcartBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PlatformshoppingcartBean item) {
        helper.setText(R.id.tv_shopcartname,item.getShopname());
        helper.setText(R.id.tv_shopcartname,item.getGoodsintroduction());
        helper.setText(R.id.tv_cartgoodprice,item.getGoodsprice());
    }
}
