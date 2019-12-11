package com.zskjprojectj.andouclient.adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.stx.xhb.xbanner.XBanner;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.entity.mall.MallItemDataBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/10 11:28
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MultipleMallHomeAdapter extends BaseMultiItemQuickAdapter<MallItemDataBean, BaseViewHolder> {

    private Context mContext;

    public MultipleMallHomeAdapter(Context context, List<MallItemDataBean> data) {
        super(data);

        this.mContext=context;

//        addItemType(MallItemDataBean.BANNER, R.layout.fragment_mall_banner_view);
//        addItemType(MallItemDataBean.SEARCH, R.layout.fragment_mall_search_view);
        addItemType(MallItemDataBean.MENU, R.layout.fragment_mall_menu_view);
        addItemType(MallItemDataBean.PLAN, R.layout.fragment_mall_activity_plan);
        addItemType(MallItemDataBean.NEWGOODS, R.layout.fragment_mall_new_goods_view);
        addItemType(MallItemDataBean.RECOMMEND, R.layout.fragment_mall_recommend_menu_view);
        addItemType(MallItemDataBean.GOODSDETAILS, R.layout.fragment_mall_goods_details_view);


    }

    @Override
    protected void convert(BaseViewHolder helper, MallItemDataBean item) {

        switch (helper.getItemViewType()) {

            case MallItemDataBean.MENU:
                initMenu(helper, item);
                break;
            case MallItemDataBean.RECOMMEND:
                initRecommend(helper, item);
                break;
        }
    }



    private void initRecommend(BaseViewHolder helper, MallItemDataBean item) {

        helper.addOnClickListener(R.id.tv_see_more);
        helper.addOnClickListener(R.id.tv_recommend_product);


    }


    /**
     * 商城首页菜单
     * @param helper
     * @param item
     */
    private void initMenu(BaseViewHolder helper, MallItemDataBean item) {
        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, MallGoodsDetailsActivity.class);
                mContext.startActivity(intent);

            }
        });


    }
}
