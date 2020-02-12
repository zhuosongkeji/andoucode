package com.zskjprojectj.andouclient.adapter.mall;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingbean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/10 17:23
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallShoppingAdapter extends BaseQuickAdapter<MallShoppingbean, BaseViewHolder> {
    public MallShoppingAdapter(int layoutResId, @Nullable List<MallShoppingbean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallShoppingbean item) {

    }
}
