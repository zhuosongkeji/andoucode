package com.zskjprojectj.andouclient.model

class Comment(val avatar: String, val name: String, val content: String, val to: User?) {


    companion object {
        val test: Comment
            get() {
                val user = User()
                user.name = "是打发点"
                return Comment("http://img4.imgtn.bdimg.com/it/u=1686238767,23385511&fm=26&gp=0.jpg",
                        "张三", "测试测试测试测试测试测试测试测试测试测试测试",
                        user)
            }
    }
}