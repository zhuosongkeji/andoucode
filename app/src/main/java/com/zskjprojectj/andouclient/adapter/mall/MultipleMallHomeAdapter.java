package com.zskjprojectj.andouclient.adapter.mall;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.stx.xhb.xbanner.XBanner;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.ClassificationofgoodsActivity;
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

        this.mContext = context;

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
            case MallItemDataBean.PLAN:
                initPlan(helper, item);
                break;
            case MallItemDataBean.RECOMMEND:
                initRecommend(helper, item);
                break;
            case MallItemDataBean.GOODSDETAILS:
                initGoodsDetails(helper, item);
                break;
        }
    }

    private void initGoodsDetails(BaseViewHolder helper, MallItemDataBean item) {

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MallGoodsDetailsActivity.class);
                mContext.startActivity(intent);
            }
        });
    }

    private void initPlan(BaseViewHolder helper, MallItemDataBean item) {
        switch (helper.getLayoutPosition() % 2) {
            case 0:
                helper.setImageResource(R.id.iv_activity_plan_image, R.mipmap.icon_mall_plan1);
                break;
            case 1:
                helper.setImageResource(R.id.iv_activity_plan_image, R.mipmap.icon_mall_plan2);
                break;

        }
    }


    private void initRecommend(BaseViewHolder helper, MallItemDataBean item) {

        helper.addOnClickListener(R.id.tv_see_more);
        helper.addOnClickListener(R.id.tv_recommend_product);


    }


    /**
     * 商城首页菜单
     *
     * @param helper
     * @param item
     */
    private void initMenu(BaseViewHolder helper, MallItemDataBean item) {

//        helper.setImageResource(R.id.iv_menu_image,);
//        helper.setText(R.id.tv_menu_name,"哈哈");
        switch (helper.getLayoutPosition() % 8) {
            case 0:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu1);
                helper.setText(R.id.tv_menu_name, "美妆个护");
                break;
            case 1:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu2);
                helper.setText(R.id.tv_menu_name, "进口尖货");
                break;
            case 2:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu3);
                helper.setText(R.id.tv_menu_name, "服饰内衣");
                break;
            case 3:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu4);
                helper.setText(R.id.tv_menu_name, "鞋包配饰");
                break;
            case 4:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu5);
                helper.setText(R.id.tv_menu_name, "家纺家电");
                break;
            case 5:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu6);
                helper.setText(R.id.tv_menu_name, "居家百货");
                break;
            case 6:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu7);
                helper.setText(R.id.tv_menu_name, "休闲美食");
                break;
            case 7:
                helper.setImageResource(R.id.iv_menu_image, R.mipmap.icon_mall_menu8);
                helper.setText(R.id.tv_menu_name, "玩具早教");
                break;

            default:
                break;
        }


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ClassificationofgoodsActivity.class);
                mContext.startActivity(intent);
            }
        });


    }
}
