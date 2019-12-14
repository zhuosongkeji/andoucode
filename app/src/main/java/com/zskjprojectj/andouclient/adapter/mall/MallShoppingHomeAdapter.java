package com.zskjprojectj.andouclient.adapter.mall;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/13 16:34
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallShoppingHomeAdapter extends BaseQuickAdapter<MallShoppingHomeBean, BaseViewHolder> {
    public MallShoppingHomeAdapter(int layoutResId, @Nullable List<MallShoppingHomeBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallShoppingHomeBean item) {

    }
}
