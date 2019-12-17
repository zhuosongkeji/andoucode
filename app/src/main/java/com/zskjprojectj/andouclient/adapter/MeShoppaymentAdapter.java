package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MeShoppaymentBean;

import java.util.List;

public class MeShoppaymentAdapter extends BaseQuickAdapter<MeShoppaymentBean, BaseViewHolder> {
    public MeShoppaymentAdapter(int layoutResId, @Nullable List<MeShoppaymentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeShoppaymentBean item) {
        helper.addOnClickListener(R.id.btn_orderpaydetails);
    }
}
