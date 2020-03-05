package com.zskjprojectj.andouclient.model

import java.io.Serializable

class Comment(val avatar: String, val name: String, val content: String, val to: User?) : Serializable