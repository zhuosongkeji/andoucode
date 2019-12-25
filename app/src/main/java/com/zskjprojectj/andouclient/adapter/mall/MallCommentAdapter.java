package com.zskjprojectj.andouclient.adapter.mall;

import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.mall.MallCommentBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/11 15:49
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallCommentAdapter extends BaseQuickAdapter<MallCommentBean, BaseViewHolder> {
    public MallCommentAdapter(int layoutResId, @Nullable List<MallCommentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallCommentBean item) {
        Glide.with(mContext).load(BaseUrl.BASE_URL+item.getAvator())
                .transition(new DrawableTransitionOptions().crossFade())
                .into((ImageView) helper.getView(R.id.iv_headPic));
        helper.setText(R.id.tv_name,item.getName());
        helper.setText(R.id.tv_time,item.getCreated_at());
        helper.setText(R.id.tv_des,item.getContent());
        Log.d(TAG, "convert: "+item.getStars());


    }
}
