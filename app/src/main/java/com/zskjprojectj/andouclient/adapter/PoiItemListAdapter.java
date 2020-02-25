package com.zskjprojectj.andouclient.adapter;

import com.amap.api.services.core.PoiItem;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.adapter.BaseAdapter;
import com.zskjprojectj.andouclient.R;


public class PoiItemListAdapter extends BaseAdapter<PoiItem> {

    private int selectPosition = -1;

    public PoiItemListAdapter() {
        super(R.layout.mapresult_view);
    }

    public void setSelectPosition(int position) {
        this.selectPosition = position;
        notifyDataSetChanged();
    }

    public int getSelectPositon() {
        return selectPosition;
    }

    @Override
    protected void convert(BaseViewHolder helper, PoiItem item) {
        helper.setChecked(R.id.checkBox, helper.getAdapterPosition() == selectPosition)
                .setText(R.id.tv_title, item.getTitle())
                .setText(R.id.tv_message, item.getProvinceName()
                        + item.getCityName()
                        + item.getAdName()
                        + item.getSnippet());
    }
}
