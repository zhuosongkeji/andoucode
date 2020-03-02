package com.zskjprojectj.andouclient.adapter

import android.graphics.Color
import com.amap.api.services.core.PoiItem
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R

class PoiItemListAdapter : BaseAdapter<PoiItem>(R.layout.mapresult_view) {
    override fun convert(helper: BaseViewHolder, item: PoiItem) {
        helper.itemView.isSelected = helper.adapterPosition == 0
        helper.setText(R.id.tv_title, item.title)
                .setTextColor(R.id.tv_title,
                        if (helper.adapterPosition == 0)
                            Color.parseColor("#61D2AE")
                        else
                            Color.parseColor("#ff333333"))
                .setText(R.id.tv_message, item.provinceName
                        + item.cityName
                        + item.adName
                        + item.snippet)
    }
}