package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.MerchantListBean;
import com.zskjprojectj.andouclient.model.Merchant;

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
public class MerchantListAdapter extends BaseQuickAdapter<Merchant, BaseViewHolder> {
    public MerchantListAdapter() {
        super(R.layout.merchant_item_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, Merchant item) {
        Glide.with(helper.itemView)
                .load(item.logo_img)
                .into((ImageView) helper.itemView.findViewById(R.id.busiess_tupian1_image));
        helper.setText(R.id.busiess_name1_textview, item.name)
                .setText(R.id.busiess_dianzancount1_textview, item.praise_num + "")
                .setText(R.id.busiess_address1_textview, item.address);
    }
}
