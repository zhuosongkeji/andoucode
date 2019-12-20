package com.zskjprojectj.andouclient.adapter.hotel;

import android.graphics.Color;

import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.entity.hotel.Category1Bean;

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
public class Catagory2Adapter extends BaseQuickAdapter<Category1Bean, BaseViewHolder> {
    private int mSelectedIndex = 0;
    private List<Category1Bean> data;

    public Catagory2Adapter(int layoutResId, @Nullable List<Category1Bean> data) {
        super(layoutResId, data);
        this.data=data;
    }

    @Override
    protected void convert(BaseViewHolder helper, Category1Bean item) {

        if (mSelectedIndex==0){
            List<Category1Bean.Category2Bean> category2Beans = data.get(0).getCategory2Beans();
            for (int i = 0; i < category2Beans.size(); i++) {
                helper.setText(R.id.catagory2_content, category2Beans.get(i).getContent2());
            }
        }else {
            List<Category1Bean.Category2Bean> category2Beans = data.get(mSelectedIndex).getCategory2Beans();
            for (int i = 0; i < category2Beans.size(); i++) {
                helper.setText(R.id.catagory2_content, category2Beans.get(i).getContent2());
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
