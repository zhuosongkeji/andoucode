package com.zskjprojectj.andouclient.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MainActivity;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.CategoryBean;
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean;

import java.util.List;

/**
 * author：wangzihang
 * date： 2017/8/8 19:15
 * desctiption：
 * e-mail：wangzihang@xiaohongchun.com
 */

public class HomeItemAdapter extends BaseAdapter {

    private Context context;
    private List<MallGoodsCateBean.TowcateBean> foodDatas;

    public HomeItemAdapter(Context context, List<MallGoodsCateBean.TowcateBean> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }


    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MallGoodsCateBean.TowcateBean towcateBean = foodDatas.get(position);
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (SimpleDraweeView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(towcateBean.getName());

        Glide.with(context).load(BaseUrl.BASE_URL+towcateBean.getImg()).into(viewHold.iv_icon);

        return convertView;


    }

    private static class ViewHold {
        private TextView tv_name;
        private SimpleDraweeView iv_icon;
    }

}
