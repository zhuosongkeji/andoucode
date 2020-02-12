package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/24 16:11
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class SpecialProductsAdapter extends BaseQuickAdapter<MallHomeDataBean.BargainGoodsBean, BaseViewHolder> {
    public SpecialProductsAdapter(int layoutResId, @Nullable List<MallHomeDataBean.BargainGoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallHomeDataBean.BargainGoodsBean item) {

        // 加载特价产品图片
        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getImg()))
                .transition(new DrawableTransitionOptions().crossFade())
                .apply(new RequestOptions().placeholder(R.drawable.default_image))
                .into((ImageView) helper.getView(R.id.iv_products_image));

        //特价产品介绍
        helper.setText(R.id.tv_products_des, item.getName());
        //特价产品价格
        helper.setText(R.id.tv_products_price, item.getPrice());


    }
}
