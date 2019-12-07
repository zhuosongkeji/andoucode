package com.zskjprojectj.andouclient.adapter.hotel;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailCommentBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 11:35
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class CommentAdapter extends BaseQuickAdapter<HotelDetailCommentBean, BaseViewHolder> {

    public CommentAdapter(int layoutResId, @Nullable List<HotelDetailCommentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelDetailCommentBean item) {

    }
}
