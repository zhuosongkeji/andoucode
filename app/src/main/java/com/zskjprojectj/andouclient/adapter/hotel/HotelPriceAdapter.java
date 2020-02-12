package com.zskjprojectj.andouclient.adapter.hotel;

import android.widget.TextView;

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
 * startTime: 2019/12/12 17:12
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
     */

    public static int SELECTOR_POSITION = -1;

    public HotelPriceAdapter(int layoutResId, @Nullable List<HotelSearchConditionBean.PriceRangeBean> data) {
        super(layoutResId, data);
    }


    @Override
    protected void convert(BaseViewHolder helper, HotelSearchConditionBean.PriceRangeBean item) {
        String price = item.getStart() + "~" + item.getEnd();
        helper.setText(R.id.tv_price, price);
        TextView mPrice = helper.getView(R.id.tv_price);
        int p = helper.getLayoutPosition();
        if (SELECTOR_POSITION == p) {
            mPrice.setSelected(true);
        } else {
            mPrice.setSelected(false);
        }

        if (mPrice.isSelected()){
            String hotelprice = mPrice.getText().toString();
            if (onItemGetContent!=null){
                onItemGetContent.content(item.getStart(),item.getEnd());
            }
        }

    }

    public void onChange(int position) {
        SELECTOR_POSITION = position;
        notifyDataSetChanged();
    }


    public void cancle(int position) {
        SELECTOR_POSITION = position;
        notifyDataSetChanged();
    }


    public interface onItemGetContent{
        void content(String star_price,String end_price);
    }
    private onItemGetContent onItemGetContent;

    public void setOnItemGetContent(HotelPriceAdapter.onItemGetContent onItemGetContent) {
        this.onItemGetContent = onItemGetContent;
    }
}
