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
 * time: 2020/1/6 14:48
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelStarAdapter extends BaseQuickAdapter<HotelSearchConditionBean.StarBean, BaseViewHolder> {
    public static int SELECTOR_POSITION = -1;

    public HotelStarAdapter(int layoutResId, @Nullable List<HotelSearchConditionBean.StarBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HotelSearchConditionBean.StarBean item) {
        helper.setText(R.id.tv_price, item.getName());
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
                onItemGetContent.content(hotelprice);
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
        void content(String content);
    }
    private onItemGetContent onItemGetContent;

    public void setOnItemGetContent(HotelStarAdapter.onItemGetContent onItemGetContent) {
        this.onItemGetContent = onItemGetContent;
    }
}
