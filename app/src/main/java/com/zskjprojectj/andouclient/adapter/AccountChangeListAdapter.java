package com.zskjprojectj.andouclient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.BalanceLog;

public class AccountChangeListAdapter extends BaseQuickAdapter<BalanceLog, BaseViewHolder> {

    public AccountChangeListAdapter() {
        super(R.layout.item_balancesubsidiary);
    }

    @Override
    protected void convert(BaseViewHolder helper, BalanceLog item) {
        helper.setText(R.id.tv_title, item.describe);
        helper.setText(R.id.mTvPrice, (item.state == 1 ? "+" : "-") + item.price + "");
        helper.setText(R.id.tv_time, item.create_time + "");
    }
}
