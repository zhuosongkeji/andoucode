package com.zskjprojectj.andouclient.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.UrlUtil;
import com.zskjprojectj.andouclient.base.BaseUrl;
import com.zskjprojectj.andouclient.entity.IndexHomeBean;

import java.util.ArrayList;
import java.util.List;

public class CoverFlowAdapter extends RecyclerView.Adapter<CoverFlowAdapter.ViewHolder> {

    private Context mContext;
    private int[] mColors = {R.drawable.item1, R.drawable.item2, R.drawable.item3, R.drawable.home_mall_pic, R.drawable.home_hotel_pic,
            R.drawable.item5, R.drawable.item6};

    private onItemClick clickCb;


    public ArrayList<IndexHomeBean.MerchantTypeBean> merchant_type = new ArrayList<>();

    public CoverFlowAdapter(Context c) {
        mContext = c;
    }

    public CoverFlowAdapter(Context c, onItemClick cb) {
        mContext = c;
        clickCb = cb;
    }

    public void setOnClickLstn(onItemClick cb) {
        this.clickCb = cb;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.fragment_coverflow_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String url = UrlUtil.INSTANCE.getImageUrl(merchant_type.get(position % merchant_type.size()).getImg());
        Log.d("wangbin", "url: " + url);
        Glide.with(mContext).load(url).apply(new RequestOptions()
                .placeholder(R.mipmap.ic_placeholder)).into(holder.imageView);

        holder.itemView.setOnClickListener(v -> {
            if (clickCb != null) {
                clickCb.clickItem(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (merchant_type.size() == 0) {
            return 0;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imgg);
        }
    }

    public interface onItemClick {
        void clickItem(int pos);
    }

    public void setNewData(ArrayList<IndexHomeBean.MerchantTypeBean> merchant_type) {
        this.merchant_type = merchant_type;
        notifyDataSetChanged();
    }
}
