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
 * time: 2019/12/20 15:25
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class Catagory3Adapter extends BaseQuickAdapter<Category1Bean, BaseViewHolder> {
    private int mSelectedIndex = 0;
    public Catagory3Adapter(int layoutResId, @Nullable List<Category1Bean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Category1Bean item) {

        if (mSelectedIndex==0){

            List<Category1Bean.Category2Bean.Category3Bean> category3Beans = item.getCategory2Beans().get(0).getCategory3Beans();
            for (int i = 0; i < category3Beans.size(); i++) {
                helper.setText(R.id.catagory2_content, category3Beans.get(i).getContent3());
            }
        }else {
            List<Category1Bean.Category2Bean.Category3Bean> category3Beans = item.getCategory2Beans().get(mSelectedIndex).getCategory3Beans();
            for (int i = 0; i < category3Beans.size(); i++) {
                helper.setText(R.id.catagory2_content, category3Beans.get(i).getContent3());
            }
        }


        helper.addOnClickListener(R.id.catagory3_content);
        int layoutPosition = helper.getLayoutPosition();
        if (mSelectedIndex==layoutPosition){
            helper.setTextColor(R.id.catagory3_content, Color.parseColor("#5ED3AE"));
        }else {
            helper.setTextColor(R.id.catagory3_content, Color.parseColor("#969696"));
        }
    }
    public void select(int index) {
        mSelectedIndex = index;
        notifyDataSetChanged();
    }
}
