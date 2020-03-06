package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;

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
public class MallShoppingHomeAdapter extends BaseQuickAdapter<MallShoppingHomeBean.GoodsBean, BaseViewHolder> {
    public MallShoppingHomeAdapter() {
        super(R.layout.activity_mall_shopping_item_view);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallShoppingHomeBean.GoodsBean item) {
        Glide.with(mContext).load(UrlUtil.INSTANCE.getImageUrl(item.getImg())).into((ImageView) helper.getView(R.id.iv_goods_img));
        helper.setText(R.id.tv_goods_name,item.getNameX());
        helper.setText(R.id.tv_goods_price,"¥"+item.getPrice());
    }

}
