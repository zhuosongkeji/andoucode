package com.zskjprojectj.andouclient.adapter;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.entity.MerchantListBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 17:34
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MerchantListAdapter extends BaseQuickAdapter<MerchantListBean, BaseViewHolder> {
    public MerchantListAdapter(int layoutResId, @Nullable List<MerchantListBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MerchantListBean item) {

    }
}
