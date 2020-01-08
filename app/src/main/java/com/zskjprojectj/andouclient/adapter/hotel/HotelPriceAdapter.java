package com.zskjprojectj.andouclient.adapter.hotel;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/12 17:12
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelPriceAdapter extends BaseQuickAdapter<HotelSearchConditionBean.PriceRangeBean, BaseViewHolder> {
    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param layoutResId      The layout resource id of each item.
     * @param sectionHeadResId The section head layout id for each item
     * @param data             A new list is created out of this one to avoid mutable list
     *
     *
     */

    public static int SELECTOR_POSITION=-1;
    public static int SELECTOR_POSITION1=1;
    public static int SELECTOR_POSITION2=2;

    public HotelPriceAdapter(int layoutResId, @Nullable List<HotelSearchConditionBean.PriceRangeBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HotelSearchConditionBean.PriceRangeBean item) {
        String price = item.getStart() + "~" + item.getEnd();
        helper.setText(R.id.tv_price,price);
//
//        TextView mPrice = helper.getView(R.id.tv_price);
//
//        int p = helper.getLayoutPosition();
//
//
//
//
//        if (0<p&&p<8){
//            if (SELECTOR_POSITION1==p){
//                mPrice.setSelected(true);
//            }else {
//                mPrice.setSelected(false);
//            }
//        }
//
//
//        if (8<p&&p<13){
//            if (SELECTOR_POSITION2==p){
//                mPrice.setSelected(true);
//            }else {
//                mPrice.setSelected(false);
//            }
//        }

    }

    public void onChange(int position){
        SELECTOR_POSITION=position;
        notifyDataSetChanged();
    }

    public void onChange1(int position){
        SELECTOR_POSITION1=position;
        notifyDataSetChanged();
    }

    public void onChange2(int position){
        SELECTOR_POSITION2=position;
        notifyDataSetChanged();
    }

    public  void cancle(int position){
        SELECTOR_POSITION1=position;
        SELECTOR_POSITION2=position;
        notifyDataSetChanged();
    }

}
