package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MymessageBean;

import java.util.List;

/**
 * 我的消息适配器
 */
public class MymessageAdapter extends BaseQuickAdapter<MymessageBean, BaseViewHolder> {
    public MymessageAdapter() {
        super(R.layout.item_mymessage);
    }

    @Override
    protected void convert(BaseViewHolder helper, MymessageBean item) {
        helper.setText(R.id.tv_messagetitle,item.getTitle());
        helper.setText(R.id.tv_messagecontent,item.getCreated_at());
    }
}
