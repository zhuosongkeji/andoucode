package com.zskjprojectj.andouclient.adapter.restaurant;

import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Food;

public class CartAdapter extends BaseAdapter<Food> {
    public CartAdapter() {
        super(R.layout.layout_cart_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Food item) {
        helper.setText(R.id.nameTxt, item.name)
                .setText(R.id.numTxt, String.valueOf(item.num))
                .setVisible(R.id.subBtn, item.num != 0)
                .setVisible(R.id.numTxt, item.num != 0)
                .addOnClickListener(R.id.subBtn)
                .addOnClickListener(R.id.addBtn)
                .setText(R.id.amountTxt, FormatUtil.getMoneyString(item.getAmount()));
    }
}
