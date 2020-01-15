package com.zskjprojectj.andouclient.adapter.mall;

import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
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
 * time: 2019/12/24 15:58
 * des:商城推荐产品
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class RecommendProductsAdapter extends BaseQuickAdapter<MallHomeDataBean.RecommendGoodsBean,BaseViewHolder> {


    public RecommendProductsAdapter(int layoutResId, @Nullable List<MallHomeDataBean.RecommendGoodsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MallHomeDataBean.RecommendGoodsBean item) {

        // 加载推荐产品图片
        Glide.with(mContext).load(UrlUtil.getImageUrl(item.getImg()))
                .transition(new DrawableTransitionOptions().crossFade())
                .into((ImageView) helper.getView(R.id.iv_products_image));

        //推荐产品介绍
        helper.setText(R.id.tv_products_des,item.getName());
        //推荐产品价格
        helper.setText(R.id.tv_products_price,item.getPrice());




    }
}
