package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.PinTuanDetails;
import com.zskjprojectj.andouclient.utils.UrlUtil;


public class MallPinTuanAdapter extends BaseAdapter<PinTuanDetails.TeamListBean> {

    public MallPinTuanAdapter() {
        super(R.layout.mall_pintuan_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, PinTuanDetails.TeamListBean item) {

        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getCaptain_avatar())).into((ImageView) helper.getView(R.id.iv_headPic));
        helper.setText(R.id.tv_pintuan_person,item.getLeft_member()+"人");
        helper.addOnClickListener(R.id.tv_buy_now);

    }
}
