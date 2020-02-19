package com.zskjprojectj.andouclient.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.entity.TieZi
import kotlinx.android.synthetic.main.item_squarefragment.view.*

class SquareAdapter : BaseAdapter<TieZi>(R.layout.item_squarefragment) {

    val adapter = SquareImgAdapter()
    val commentAdapter = SquareCommentAdapter()


    override fun convert(helper: BaseViewHolder, item: TieZi) {
        bindTieZi(mContext, helper.itemView, item, adapter, true)
        helper.itemView.commentRecyclerView.adapter = commentAdapter
        commentAdapter.setNewData(item.comments)
    }
}

fun bindTieZi(context: Context, view: View, item: TieZi,
              adapter: SquareImgAdapter, commentVisible: Boolean) {
    view.nameTxt.text = item.name
    view.contentTxt.text = item.content
    view.dateTxt.text = item.time
    view.likeTxt.text = item.like.toString()
    view.commentTxt.text = item.comment.toString()
    view.shareTxt.text = item.share.toString()
    view.titleTxt.text = item.title
    Glide.with(context).load(item.avatar)
            .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
            .into(view.avatarImg)
    view.recyclerView.adapter = adapter
    adapter.setNewData(item.imgs)
    view.commentRecyclerView.visibility = if (commentVisible) View.VISIBLE else View.GONE
}
