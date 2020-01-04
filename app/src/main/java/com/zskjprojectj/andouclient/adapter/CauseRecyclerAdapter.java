package com.zskjprojectj.andouclient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.RefundReasonBean;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/4 13:38
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CauseRecyclerAdapter extends BaseQuickAdapter<RefundReasonBean, BaseViewHolder> {
    public CauseRecyclerAdapter() {
        super(R.layout.refund_reason_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, RefundReasonBean item) {

        helper.setText(R.id.tv_refund,item.getName());

    }
}
