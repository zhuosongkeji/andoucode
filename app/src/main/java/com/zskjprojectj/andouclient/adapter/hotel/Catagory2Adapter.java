package com.zskjprojectj.andouclient.adapter.hotel;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.CategoryBean;

import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/20 15:24
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Catagory2Adapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {
    private int mSelectedIndex = 0;
    private List<CategoryBean> data;

    public Catagory2Adapter(int layoutResId, @Nullable List<CategoryBean> data) {
        super(layoutResId, data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {

        if (mSelectedIndex==0){
            List<CategoryBean> category2Beans = data.get(0).categories;
            for (int i = 0; i < category2Beans.size(); i++) {
                helper.setText(R.id.catagory2_content, category2Beans.get(i).getContent());
            }
        }else {
            List<CategoryBean> category2Beans = data.get(mSelectedIndex).categories;
            for (int i = 0; i < category2Beans.size(); i++) {
                helper.setText(R.id.catagory2_content, category2Beans.get(i).getContent());
            }
        }

        helper.addOnClickListener(R.id.catagory2_content);
        int layoutPosition = helper.getLayoutPosition();
        if (mSelectedIndex == layoutPosition) {
            helper.setTextColor(R.id.catagory2_content, Color.parseColor("#5ED3AE"));
        } else {
            helper.setTextColor(R.id.catagory2_content, Color.parseColor("#969696"));
        }
    }

    public void select(int index) {
        mSelectedIndex = index;
        notifyDataSetChanged();
    }
}
