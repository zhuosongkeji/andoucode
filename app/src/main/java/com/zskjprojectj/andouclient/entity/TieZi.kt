package com.zskjprojectj.andouclient.entity

import com.zskjprojectj.andouclient.model.Comment
import java.io.Serializable

class TieZi(val avatar: String, val name: String, val content: String, val time: String,
            val like: Int, val comment: Int, val share: Int, val title: String, val imgs: ArrayList<String>,
            val comments: ArrayList<Comment>) : Serializable {
}
