package com.zskjprojectj.andouclient.adapter;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.MycollectionBean;

import java.util.List;

public class MycollectionAdapter extends BaseQuickAdapter<MycollectionBean, BaseViewHolder> {
    public MycollectionAdapter(int layoutResId, @Nullable List<MycollectionBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MycollectionBean item) {
        helper.setText(R.id.collection_name_textview,item.getName())
                .setText(R.id.collection_price_textview,"￥"+item.getPrice());
        Log.d(TAG, "convert: "+BaseUrl.BASE_URL + item.getImg());
        Glide.with(mContext).load(BaseUrl.BASE_URL + item.getImg()).into((ImageView) helper.getView(R.id.collection_tupian_image));
        helper.addOnClickListener(R.id.btn_check_the_goods);
    }
}
