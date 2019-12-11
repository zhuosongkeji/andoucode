package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.BalanceofprepaidpaywayBean;

import java.util.List;

/**
 * 支付方式适配器
 */
public class BalanceofprepaidPaywayAdapter extends BaseQuickAdapter<BalanceofprepaidpaywayBean, BaseViewHolder> {
    public BalanceofprepaidPaywayAdapter(int layoutResId, @Nullable List<BalanceofprepaidpaywayBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalanceofprepaidpaywayBean item) {
        helper.setText(R.id.tv_paywayname,item.getPaywayname());
    }
}
