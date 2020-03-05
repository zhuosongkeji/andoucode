package com.zskjprojectj.andouclient.adapter

import android.text.Html
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.model.Comment
import com.zskjprojectj.andouclient.model.TieBa
import com.zskjprojectj.andouclient.utils.UrlUtil
import kotlinx.android.synthetic.main.layout_square_comment_item.view.*


class SquareCommentAdapter : BaseAdapter<TieBa.CommentsBean>(R.layout.layout_square_comment_item) {
    override fun convert(helper: BaseViewHolder, item: TieBa.CommentsBean) {
        val builder = StringBuilder("<font color='#5ED3AE'>${item.name}</font>")
        if (item.to != null) {
            builder.append(" 回复 <font color='#5ED3AE'>${item.to}</font>")
        }
        builder.append("<font color='#5ED3AE'> : </font>${item.content}")
        helper.itemView.contentTxt.text = Html.fromHtml(builder.toString())
        Glide.with(mContext).load(UrlUtil.getImageUrl(item.avator))
                .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into(helper.itemView.img)
    }
}