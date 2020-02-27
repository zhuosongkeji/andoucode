package com.zskjprojectj.andouclient.adapter.restaurant;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.interfaces.OnSelectListener;
import com.willy.ratingbar.ScaleRatingBar;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zhuosongkj.android.library.ui.HeaderItemDecoration;
import com.zhuosongkj.android.library.util.GlideUtil;
import com.zhuosongkj.android.library.util.ListUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.model.Food;
import com.zskjprojectj.andouclient.model.Restaurant;
import com.zskjprojectj.andouclient.utils.ScreenUtil;
import com.zskjprojectj.andouclient.utils.UrlUtil;

public class RestaurantAdapter extends BaseAdapter<Restaurant> implements HeaderItemDecoration.StickyHeaderInterface {
    public RestaurantAdapter() {
        super(R.layout.layout_restaurant_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, Restaurant item) {
        helper.setText(R.id.nameTxt, item.name)
                .setText(R.id.likeTxt, item.praise_num)
                .setVisible(R.id.vipImg, item.isVip());
        ((ScaleRatingBar) helper.itemView.findViewById(R.id.ratingBar)).setRating(item.stars_all);
        changeImageLayout(helper, ListUtil.isEmpty(item.cai) ? 1 : item.cai.size());
        if (ListUtil.isEmpty(item.cai)) {
            GlideUtil.load(((Activity) helper.itemView.getContext()),
                    UrlUtil.INSTANCE.getImageUrl(item.banner_img), R.mipmap.ic_placeholder,
                    helper.itemView.findViewById(R.id.img1), SizeUtils.dp2px(4));
        } else {
            for (int i = 0; i < item.cai.size(); i++) {
                Food food = item.cai.get(i);
                int imageViewRes;
                switch (i) {
                    case 0:
                        imageViewRes = R.id.img1;
                        break;
                    case 1:
                        imageViewRes = R.id.img2;
                        break;
                    default:
                        imageViewRes = R.id.img3;
                        break;
                }
                GlideUtil.load(((Activity) helper.itemView.getContext()),
                        UrlUtil.INSTANCE.getImageUrl(food.image), R.mipmap.ic_placeholder,
                        helper.itemView.findViewById(imageViewRes), SizeUtils.dp2px(4));
            }
        }
    }

    private void changeImageLayout(BaseViewHolder helper, int size) {
        View itemContainer = helper.itemView.findViewById(R.id.itemContainer);
        int itemViewWidth = ScreenUtil.getScreenWidth(helper.itemView.getContext())
                - itemContainer.getPaddingLeft() - itemContainer.getPaddingRight();
        int margin = SizeUtils.dp2px(5);
        ViewGroup.LayoutParams img1layoutParams = helper.itemView.findViewById(R.id.img1).getLayoutParams();
        img1layoutParams.width = 0;
        img1layoutParams.height = 0;
        ((LinearLayout.LayoutParams) img1layoutParams).setMarginEnd(0);
        ViewGroup.LayoutParams img2LayoutParams = helper.itemView.findViewById(R.id.img2).getLayoutParams();
        img2LayoutParams.width = 0;
        img2LayoutParams.height = 0;
        ((LinearLayout.LayoutParams) img2LayoutParams).bottomMargin = 0;
        ViewGroup.LayoutParams img3LayoutParams = helper.itemView.findViewById(R.id.img3).getLayoutParams();
        img3LayoutParams.width = 0;
        img3LayoutParams.height = 0;
        if (size == 1) {
            img1layoutParams.width = itemViewWidth;
            img1layoutParams.height = img1layoutParams.width * 3 / 7;
        } else if (size == 2) {
            itemViewWidth -= margin;
            img1layoutParams.width = itemViewWidth / 2;
            img1layoutParams.height = img1layoutParams.width * 3 / 7;
            ((LinearLayout.LayoutParams) img1layoutParams).setMarginEnd(margin);
            img2LayoutParams.width = itemViewWidth / 2;
            img2LayoutParams.height = img1layoutParams.width * 3 / 7;
        } else if (size >= 3) {
            itemViewWidth -= margin;
            ((LinearLayout.LayoutParams) img1layoutParams).setMarginEnd(margin);
            img1layoutParams.width = itemViewWidth * 21 / 34;
            img1layoutParams.height = img1layoutParams.width * 16 / 21;
            img2LayoutParams.width = itemViewWidth * 13 / 34;
            img2LayoutParams.height = img1layoutParams.height / 2 - margin;
            ((LinearLayout.LayoutParams) img2LayoutParams).bottomMargin = margin;
            img3LayoutParams.width = img2LayoutParams.width;
            img3LayoutParams.height = img2LayoutParams.height + margin;
        }
    }

    @Override
    public int getHeaderPositionForItem(int itemPosition) {
        return 0;
    }

    @Override
    public int getHeaderLayout(int headerPosition) {
        return R.layout.layout_restaurant_filter_view;
    }

    @Override
    public void bindHeaderData(View header, int headerPosition) {

    }

    @Override
    public boolean isHeader(int itemPosition) {
        if (getData().size() == 0) {
            return false;
        }
        return getItem(itemPosition).isHeader;
    }
}