package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.BrowsingBean;

import java.util.List;

/**
 * 浏览痕迹适配器
 */
public class BrowsingAdapter extends BaseQuickAdapter<BrowsingBean, BaseViewHolder> {
    public BrowsingAdapter(int layoutResId, @Nullable List<BrowsingBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BrowsingBean item) {

    }
}
