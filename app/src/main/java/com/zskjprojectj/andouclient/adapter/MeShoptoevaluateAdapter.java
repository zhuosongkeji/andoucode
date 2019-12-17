package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MeShoptoevaluateBean;

import java.util.List;

public class MeShoptoevaluateAdapter extends BaseQuickAdapter<MeShoptoevaluateBean, BaseViewHolder> {
    public MeShoptoevaluateAdapter(int layoutResId, @Nullable List<MeShoptoevaluateBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeShoptoevaluateBean item) {
            helper.addOnClickListener(R.id.btn_toevaluate);
    }
}
