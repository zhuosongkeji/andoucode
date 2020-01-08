package com.zskjprojectj.andouclient.adapter;

import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.willy.ratingbar.ScaleRatingBar;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.MyFocusonBean;

import java.util.List;

public class MyFocusonAdapter extends BaseQuickAdapter<MyFocusonBean,BaseViewHolder>{
    public MyFocusonAdapter(int layoutResId, @Nullable List<MyFocusonBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MyFocusonBean item) {
        helper.setText(R.id.focuson_name1_textview,item.getName()).setText(R.id.busiess_address1_textview,item.getAddress()).setText(R.id.tv_focusonnum,item.getTel()).setText(R.id.focuson_dianzancount1_textview,item.getPraise_num());
        Glide.with(mContext).load(BaseUrl.BASE_URL + item.getLogo_img()).into((ImageView) helper.getView(R.id.focuson_tupian1_image));
        helper.addOnClickListener(R.id.btn_focusoncheckmerchants);
        ScaleRatingBar scaleRatingBar=  helper.getView(R.id.simpleRatingBar);
        String stars_all = item.getStars_all();
        float starNum = Float.parseFloat(stars_all);
        scaleRatingBar.setRating(starNum);
    }
}
