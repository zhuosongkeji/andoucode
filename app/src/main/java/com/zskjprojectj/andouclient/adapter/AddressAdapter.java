package com.zskjprojectj.andouclient.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.amap.api.services.core.PoiItem;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.listener.OnItemClickLisenter;

import java.util.List;

/**
 * 地址的适配器
 * Created by XiaoFu on 2018-01-11 15:00.
 * 注释：
 */

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyHolder> {
    private Context mContext;
    private List<PoiItem> mList;
    private int selectPosition = -1;
    private OnItemClickLisenter mOnItemClickLisenter;

    public AddressAdapter(Context context, List<PoiItem> list) {
        this.mContext = context;
        this.mList = list;
    }

    public void setList(List<PoiItem> list) {
        this.mList = list;
        selectPosition = -1;
        notifyDataSetChanged();
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectPositon(){
        return selectPosition;
    }

    public void setOnItemClickLisenter(OnItemClickLisenter onItemClickLisenter) {
        this.mOnItemClickLisenter = onItemClickLisenter;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyHolder myHolder = new MyHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address_info, parent, false));
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        holder.itemView.setTag(position);
        PoiItem poiItem = mList.get(position);
        if (position == selectPosition) {
            holder.mCheckBox.setChecked(true);
        } else {
            holder.mCheckBox.setChecked(false);
        }
        holder.mTvTitle.setText(poiItem.getTitle());
        holder.mTvMessage.setText(poiItem.getProvinceName() + poiItem.getCityName() + poiItem.getAdName() + poiItem.getSnippet());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (Integer) view.getTag();
                setSelectPosition(position);
                if (null != mOnItemClickLisenter) {
                    mOnItemClickLisenter.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        TextView mTvTitle;
        TextView mTvMessage;
        CheckBox mCheckBox;


        public MyHolder(View itemView) {
            super(itemView);
            mTvTitle = itemView.findViewById(R.id.tv_title);
            mTvMessage = itemView.findViewById(R.id.tv_message);
            mCheckBox = itemView.findViewById(R.id.checkBox);
        }
    }

}
