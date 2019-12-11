package com.zskjprojectj.andouclient.adapter.mall;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.mall.MallDetailCommentBean;

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
public class MallCommentAdapter extends BaseQuickAdapter<MallDetailCommentBean, BaseViewHolder> {
    public MallCommentAdapter(int layoutResId, @Nullable List<MallDetailCommentBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallDetailCommentBean item) {

    }
}
