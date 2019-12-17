package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MeShopsendgoodsBean;

import java.util.List;

public class MeShopsendgoodsAdapter extends BaseQuickAdapter<MeShopsendgoodsBean, BaseViewHolder> {
    public MeShopsendgoodsAdapter(int layoutResId, @Nullable List<MeShopsendgoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeShopsendgoodsBean item) {
        helper.addOnClickListener(R.id.btn_ordersenddetails);
    }
}
