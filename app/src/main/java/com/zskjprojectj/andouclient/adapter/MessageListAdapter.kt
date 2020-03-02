package com.zskjprojectj.andouclient.adapter

import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.model.MessageType

class MessageListAdapter : BaseAdapter<MessageType>(R.layout.layout_message_list_item) {
    override fun convert(helper: BaseViewHolder?, item: MessageType?) {
    }
}
