package com.zskjprojectj.andouclient.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import kotlinx.android.synthetic.main.squareimg_view.view.*

class SquareImgAdapter : BaseAdapter<String>(R.layout.squareimg_view) {
    override fun convert(helper: BaseViewHolder, item: String) {
        Glide.with(mContext).load(item)
                .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into(helper.itemView.img)
    }
}
