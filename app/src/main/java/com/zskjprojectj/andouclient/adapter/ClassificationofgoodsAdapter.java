package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.ClassificationofgoodsBean;

import java.util.List;

public class ClassificationofgoodsAdapter extends BaseQuickAdapter<ClassificationofgoodsBean, BaseViewHolder> {
    public ClassificationofgoodsAdapter(int layoutResId, @Nullable List<ClassificationofgoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ClassificationofgoodsBean item) {

    }
}
