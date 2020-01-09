package com.zskjprojectj.andouclient.adapter.restaurant;

import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SizeUtils;
import com.chad.library.adapter.base.BaseViewHolder;
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

public class HomeAdapter extends BaseAdapter<Restaurant> implements HeaderItemDecoration.StickyHeaderInterface {
    public HomeAdapter() {
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
                    UrlUtil.getImageUrl(item.door_img), R.mipmap.ic_placeholder,
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
                        UrlUtil.getImageUrl(food.image), R.mipmap.ic_placeholder,
                        helper.itemView.findViewById(imageViewRes), SizeUtils.dp2px(4));
            }
        }
    }

    private void changeImageLayout(BaseViewHolder helper, int size) {
        int itemViewWidth = ScreenUtil.getScreenWidth(helper.itemView.getContext()) - SizeUtils.dp2px(30);
        ImageView img1 = helper.itemView.findViewById(R.id.img1);
        img1.getLayoutParams().width = 0;
        img1.getLayoutParams().height = 0;
        img1.setPadding(0, 0, 0, 0);
        ImageView img2 = helper.itemView.findViewById(R.id.img2);
        img2.getLayoutParams().width = 0;
        img2.getLayoutParams().height = 0;
        ImageView img3 = helper.itemView.findViewById(R.id.img3);
        img3.getLayoutParams().width = 0;
        img3.getLayoutParams().height = 0;
        if (size == 1) {
            img1.getLayoutParams().width = itemViewWidth;
            img1.getLayoutParams().height = img1.getLayoutParams().width * 3 / 7;
        } else if (size == 2) {
            img1.getLayoutParams().width = itemViewWidth / 2;
            img1.getLayoutParams().height = img1.getLayoutParams().width * 3 / 7;
            img1.setPadding(SizeUtils.dp2px(5), 0, 0, 0);
            img2.getLayoutParams().width = itemViewWidth / 2;
            img2.getLayoutParams().height = img1.getLayoutParams().width * 3 / 7;
        } else if (size >= 3) {
            img1.getLayoutParams().width = itemViewWidth * 21 / 34;
            img1.getLayoutParams().height = img1.getLayoutParams().width * 16 / 21;
            img2.getLayoutParams().width = itemViewWidth * 13 / 34;
            img2.getLayoutParams().height = img1.getLayoutParams().height / 2;
            img2.setPadding(0, SizeUtils.dp2px(5), 0, 0);
            img3.getLayoutParams().width = img2.getLayoutParams().width;
            img3.getLayoutParams().height = img2.getLayoutParams().height;
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