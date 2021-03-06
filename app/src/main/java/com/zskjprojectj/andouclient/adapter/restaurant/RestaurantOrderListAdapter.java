package com.zskjprojectj.andouclient.adapter.restaurant;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zhuosongkj.android.library.util.FormatUtil;
import com.zhuosongkj.android.library.util.ViewUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.RestaurantOrder;
import com.zskjprojectj.andouclient.utils.UrlUtil;

import java.math.BigDecimal;

import retrofit2.http.Url;

public class RestaurantOrderListAdapter extends BaseAdapter<RestaurantOrder> {
    public RestaurantOrderListAdapter() {
        super(R.layout.layout_restaurant_order_list_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, RestaurantOrder item) {
        int count = 0;
        BigDecimal amount = new BigDecimal(0);
        ViewGroup foodContainer = helper.itemView.findViewById(R.id.foodContainer);
        foodContainer.removeAllViews();
        for (Food food : item.foods) {
            count += food.num;
            amount = amount.add(new BigDecimal(food.getAmount()));
            View foodView = LayoutInflater.from(helper.itemView.getContext())
                    .inflate(R.layout.layout_order_list_food_item, null);
            ViewUtil.setText(foodView, R.id.foodNameTxt, food.name);
            ViewUtil.setText(foodView, R.id.foodCountTxt, String.valueOf(food.num));
            ViewUtil.setText(foodView, R.id.foodAmountTxt, FormatUtil.getMoneyString(food.getAmount()));
            foodContainer.addView(foodView);
        }
        helper.setText(R.id.nameTxt, item.name)
                .setText(R.id.orderNumTxt, item.order_sn)
                .setText(R.id.statusTxt, RestaurantOrder.getState(item.status).stateStr)
                .setText(R.id.totalCountTxt, String.valueOf(count))
                .setText(R.id.amountTxt, FormatUtil.getMoneyString(new BigDecimal(item.prices).subtract(new BigDecimal(item.integral)).doubleValue()))
                .setGone(R.id.reviewTxtBtn, item.status == RestaurantOrder.STATE.DAI_PING_JIA.stateInt)
                .setGone(R.id.payTxtBtn, item.status == RestaurantOrder.STATE.DAI_ZHI_FU.stateInt)
                .setGone(R.id.cancelBtn, item.status == RestaurantOrder.STATE.DAI_SHI_YONG.stateInt)
                .addOnClickListener(R.id.reviewTxtBtn)
                .addOnClickListener(R.id.payTxtBtn)
                .addOnClickListener(R.id.cancelBtn)
                .addOnClickListener(R.id.detailBtn);
        Glide.with(helper.itemView.getContext())
                .load(UrlUtil.INSTANCE.getImageUrl(item.logo_img))
                .apply(new RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into((ImageView) helper.itemView.findViewById(R.id.logoImg));
    }
}
