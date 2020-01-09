package com.zskjprojectj.andouclient.adapter.restaurant;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Food;

public class FoodAdapter extends BaseAdapter<Food> {
    public FoodAdapter() {
        super(R.layout.layout_food_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Food item) {
        helper.setGone(R.id.headerTxt, item.isHeader)
                .setText(R.id.headerTxt, item.category.name);
    }
}
