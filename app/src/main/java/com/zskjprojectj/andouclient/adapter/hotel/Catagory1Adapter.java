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
 * startTime: 2019/12/20 15:23
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Catagory1Adapter extends BaseQuickAdapter<CategoryBean, BaseViewHolder> {

    private int mSelectedIndex = 0;


    public Catagory1Adapter(int layoutResId, @Nullable List<CategoryBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, CategoryBean item) {
        helper.setText(R.id.catagory1_content,item.getContent());
        helper.addOnClickListener(R.id.catagory1_content);
        int layoutPosition = helper.getLayoutPosition();
        if (mSelectedIndex==layoutPosition){
            helper.setTextColor(R.id.catagory1_content, Color.parseColor("#5ED3AE"));
        }else {
            helper.setTextColor(R.id.catagory1_content, Color.parseColor("#969696"));
        }

    }

    public void select(int index) {
        mSelectedIndex = index;
        notifyDataSetChanged();
    }
}
