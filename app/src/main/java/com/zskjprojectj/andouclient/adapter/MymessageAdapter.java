package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MymessageBean;
import com.zskjprojectj.andouclient.utils.UrlUtil;

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
        if (item.getMessageStatus().equals("1")) {
            helper.setImageResource(R.id.img_status, R.mipmap.yiduicon);

        } else {
            helper.setImageResource(R.id.img_status, R.mipmap.weiduicon);
        }
        //Glide.with(mContext).load(UrlUtil.getImageUrl(item.getLogo_img())).into((ImageView) helper.getView(R.id.busiess_tupian1_image));

    }
}
