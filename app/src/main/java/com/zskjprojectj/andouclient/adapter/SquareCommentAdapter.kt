package com.zskjprojectj.andouclient.adapter

import android.text.Html
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.model.Comment
import kotlinx.android.synthetic.main.layout_square_comment_item.view.*


class SquareCommentAdapter : BaseAdapter<Comment>(R.layout.layout_square_comment_item) {
    override fun convert(helper: BaseViewHolder, item: Comment) {
        val builder = StringBuilder("<font color='#5ED3AE'>${item.name}</font>")
        if (item.to != null) {
            builder.append(" 回复 <font color='#5ED3AE'>${item.to.name}</font>")
        }
        builder.append("<font color='#5ED3AE'> : </font>${item.content}")
        helper.itemView.contentTxt.text = Html.fromHtml(builder.toString())

        Glide.with(mContext).load(item.avatar)
                .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into(helper.itemView.img)
    }
}