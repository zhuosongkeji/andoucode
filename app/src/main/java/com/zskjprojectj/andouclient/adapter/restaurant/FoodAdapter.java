package com.zskjprojectj.andouclient.adapter.restaurant;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class FoodAdapter extends BaseAdapter<Food> {
    public FoodAdapter() {
        super(R.layout.layout_food_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Food item) {
        helper.setGone(R.id.headerTxt, item.isHeader)
                .setText(R.id.headerTxt, item.category.name)
                .setText(R.id.nameTxt, item.name)
                .setText(R.id.descriptionTxt, item.remark)
                .setText(R.id.priceTxt, item.price)
                .setText(R.id.numTxt, String.valueOf(item.num))
                .setVisible(R.id.subBtn, item.num != 0)
                .setVisible(R.id.numTxt, item.num != 0)
                .addOnClickListener(R.id.subBtn)
                .addOnClickListener(R.id.addBtn);
        Glide.with(helper.itemView.getContext())
                .load(UrlUtil.INSTANCE.getImageUrl(item.image))
                .into((ImageView) helper.itemView.findViewById(R.id.img));
    }
}
