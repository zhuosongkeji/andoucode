package com.zskjprojectj.andouclient.adapter.restaurant;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.FoodCategory;

public class FoodCategoryListAdapter extends BaseAdapter<FoodCategory> {
    public FoodCategoryListAdapter() {
        super(R.layout.layout_food_catgory_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, FoodCategory item) {
        helper.setText(R.id.nameTxt, item.name)
                .setVisible(R.id.indicatorImg, isSelect(item));
        helper.itemView.setSelected(isSelect(item));
    }


}