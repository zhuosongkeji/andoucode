package com.zskjprojectj.andouclient.model

import com.zhuosongkj.android.library.model.IListData
import java.util.ArrayList

class MessageListResponse( val list: MessageList?) : IListData<Message> {
    override fun getDataList(): ArrayList<Message> {
        return list?.list ?: arrayListOf()
    }
}

class MessageList(val join_msg: String = "",val list: ArrayList<Message>)

class Message(val info_id: Long,
              val post_id: Long,
              val avator: String,
              val from_user: String,
              val title: String,
              var read: Int = 0,
              val created_at: String)
