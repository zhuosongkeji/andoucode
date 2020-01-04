package com.zskjprojectj.andouclient.adapter;

import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.IndexHomeBean;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.adapter
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/3 16:40
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class merchantsCategoryAdapter extends BaseQuickAdapter<IndexHomeBean.MerchantsBean, BaseViewHolder> {

    public merchantsCategoryAdapter() {
        super(R.layout.merchants_category_item);
    }

    @Override
    protected void convert(BaseViewHolder helper, IndexHomeBean.MerchantsBean item) {
        String url = BaseUrl.BASE_URL + "/"+item.getLogo_img();
        Log.d(TAG, "url===: "+url);
        Glide.with(mContext).load(url).apply(new RequestOptions()
        .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into((ImageView) helper.getView(R.id.check_in_busiess1_image));

        helper.setText(R.id.check_in_busiessname1_textview,item.getName());

    }
}
