package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MeShopforthegoodsBean;

import java.util.List;

public class MeShopforthegoodsAdapter extends BaseQuickAdapter<MeShopforthegoodsBean, BaseViewHolder> {
    public MeShopforthegoodsAdapter(int layoutResId, @Nullable List<MeShopforthegoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeShopforthegoodsBean item) {
        helper.addOnClickListener(R.id.btn_orderforgoodsdetails);
        helper.addOnClickListener(R.id.btn_orderforgoodschecklogistics);
        helper.addOnClickListener(R.id.btn_suregetgoods);
    }
}
