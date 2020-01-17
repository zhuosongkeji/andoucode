package com.zskjprojectj.andouclient.adapter.restaurant;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.RestaurantCategory;

public class FilterCategoryAdapter extends BaseAdapter<RestaurantCategory> {
    public FilterCategoryAdapter() {
        super(R.layout.layout_filter_category_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, RestaurantCategory item) {
        helper.setText(R.id.nameTxt, item.name);
    }
}
