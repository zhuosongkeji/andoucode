package com.zskjprojectj.andouclient.adapter

import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.entity.Square
import kotlinx.android.synthetic.main.dialog_only_extra_prompt.view.*
import kotlinx.android.synthetic.main.item_squarefragment.view.*

class SquareAdapter : BaseAdapter<Square>(R.layout.item_squarefragment) {

    val adapter = SquareImgAdapter()
    val commentAdapter=SquareCommentAdapter()


    override fun convert(helper: BaseViewHolder, item: Square) {
        helper.itemView.nameTxt.text = item.name
        helper.itemView.contentTxt.text = item.content
        helper.itemView.dateTxt.text = item.time
        helper.itemView.likeTxt.text = item.like.toString()
        helper.itemView.commentTxt.text = item.comment.toString()
        helper.itemView.shareTxt.text = item.share.toString()
        helper.itemView.titleTxt.text = item.title
        Glide.with(mContext).load(item.avatar)
                .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into(helper.itemView.avatarImg)

        helper.itemView.recyclerView.adapter = adapter
        adapter.setNewData(item.imgs)
        helper.itemView.commentRecyclerView.adapter=commentAdapter
        commentAdapter.setNewData(item.comments)
    }
}
