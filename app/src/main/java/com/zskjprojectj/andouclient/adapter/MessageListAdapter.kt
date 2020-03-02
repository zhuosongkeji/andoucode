package com.zskjprojectj.andouclient.adapter

import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.Message
import kotlinx.android.synthetic.main.layout_message_list_item.view.*

class MessageListAdapter : BaseAdapter<Message>(R.layout.layout_message_list_item) {
    var controlText: String = ""

    override fun convert(helper: BaseViewHolder, item: Message) {
        Glide.with(mContext).load(item.avator).into(helper.itemView.avatarImg)
        helper.setText(R.id.fromUserNameTxt, item.from_user)
                .setText(R.id.controlTxt, controlText)
                .setText(R.id.postTitleTxt, "《${item.title}》")
                .setText(R.id.dateTxt, item.created_at)
                .setGone(R.id.unreadImg, item.read == 0)
    }
}
