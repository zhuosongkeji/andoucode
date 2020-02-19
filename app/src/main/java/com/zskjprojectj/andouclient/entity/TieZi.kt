package com.zskjprojectj.andouclient.entity

import com.zskjprojectj.andouclient.model.Comment
import java.io.Serializable

class TieZi(val avatar: String, val name: String, val content: String, val time: String,
            val like: Int, val comment: Int, val share: Int, val title: String, val imgs: ArrayList<String>,
            val comments: ArrayList<Comment>) : Serializable {

    companion object {
        val testData = arrayListOf(test, test, test, test)
        val test
            get() = TieZi("http://a4.att.hudong.com/21/09/01200000026352136359" +
                    "091694357.jpg",
                    "测试名曾",
                    "测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试测试" +
                            "测试测试测试测试测试测试测试测试测试测试",
                    "2010-2-17",
                    120,
                    30,
                    45,
                    "贴吧标题测试",
                    arrayListOf("http://a1.att.hudong.com/05/00/01300000194285122188000535877.jpg",
                            "http://a1.att.hudong.com/05/00/01300000194285122188000535877.jpg",
                            "http://a1.att.hudong.com/05/00/01300000194285122188000535877.jpg",
                            "http://a1.att.hudong.com/05/00/01300000194285122188000535877.jpg"),
                    arrayListOf(Comment.test,
                            Comment.test,
                            Comment.test)
            )
    }
}
