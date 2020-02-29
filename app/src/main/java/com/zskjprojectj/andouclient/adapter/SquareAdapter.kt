package com.zskjprojectj.andouclient.adapter

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.entity.TieZi
import com.zskjprojectj.andouclient.model.TieBa
import kotlinx.android.synthetic.main.item_squarefragment.view.*

class SquareAdapter : BaseAdapter<TieBa>(R.layout.item_squarefragment) {

    override fun convert(helper: BaseViewHolder, item: TieBa) {
        val adapter = SquareImgAdapter()
        val commentAdapter = SquareCommentAdapter()
        bindTieZi(mContext, helper.itemView, item, adapter, item.comment_count.toInt()>0)
        helper.itemView.commentRecyclerView.adapter = commentAdapter
        commentAdapter.setNewData(item.comments)
        helper.addOnClickListener(R.id.likeView)
    }
}

fun bindTieZi(context: Context, view: View, item: TieBa?,
              adapter: SquareImgAdapter, commentVisible: Boolean) {
    view.nameTxt.text = item?.name
    view.contentTxt.text = item?.content
//    view.dateTxt.text = item.time
    view.likeTxt.text = item?.vote
    view.commentTxt.text = item?.comment_count
    view.shareTxt.text = item?.share
    view.titleTxt.text = item?.title
    Glide.with(context).load(item?.avator)
            .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
            .into(view.avatarImg)
    view.recyclerView.adapter = adapter
    adapter.setNewData(item?.images)
    view.commentRecyclerView.visibility = if (commentVisible) View.VISIBLE else View.GONE
}
