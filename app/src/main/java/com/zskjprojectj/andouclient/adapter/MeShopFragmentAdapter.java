package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MeShopFragmentBean;

import java.util.List;

/**
 * 个人中心商城订单全部订单适配器对象
 */
public class MeShopFragmentAdapter extends BaseQuickAdapter<MeShopFragmentBean, BaseViewHolder> {
    public MeShopFragmentAdapter(int layoutResId, @Nullable List<MeShopFragmentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeShopFragmentBean item) {
        helper.addOnClickListener(R.id.btn_orderdetails);
    }
}
