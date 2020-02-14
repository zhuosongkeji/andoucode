package com.zskjprojectj.andouclient.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MerchantHomeTypeBean;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/19 15:12
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MerchantAdapter extends BaseQuickAdapter<MerchantHomeTypeBean.MerchantTypeBean, BaseViewHolder> {
    public MerchantAdapter() {
        super(R.layout.merchantview);
    }

    @Override
    protected void convert(BaseViewHolder helper, MerchantHomeTypeBean.MerchantTypeBean item) {
        helper.setText(R.id.tv_popu_unlimited,item.getType_name());
    }
}
