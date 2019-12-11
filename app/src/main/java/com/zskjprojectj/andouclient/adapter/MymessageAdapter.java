package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.MymessageBean;

import java.util.List;

/**
 * 我的消息适配器
 */
public class MymessageAdapter extends BaseQuickAdapter<MymessageBean, BaseViewHolder> {
    public MymessageAdapter(int layoutResId, @Nullable List<MymessageBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MymessageBean item) {

    }
}
