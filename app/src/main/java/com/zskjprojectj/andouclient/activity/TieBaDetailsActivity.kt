package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA
import com.zskjprojectj.andouclient.adapter.SquareCommentAdapter
import com.zskjprojectj.andouclient.adapter.SquareImgAdapter
import com.zskjprojectj.andouclient.adapter.bindTieZi
import com.zskjprojectj.andouclient.entity.TieZi
import com.zskjprojectj.andouclient.model.Comment
import kotlinx.android.synthetic.main.activity_tiebadetails_view.*

class TieBaDetailsActivity : BaseActivity() {

    val adapter = SquareCommentAdapter()
    val imgAdapter = SquareImgAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "贴吧详情")
        adapter.bindToRecyclerView(recyclerView)
        bindData(intent.getSerializableExtra(KEY_DATA) as TieZi)
    }

    fun bindData(tieZi: TieZi) {
        val view = layoutInflater.inflate(R.layout.item_squarefragment, null)
        bindTieZi(mActivity, view, tieZi, imgAdapter, false)
        adapter.addHeaderView(view)
        adapter.addHeaderView(layoutInflater.inflate(R.layout.layout_comment_text, null))
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
        adapter.addData(Comment.test)
    }

    override fun getContentView() = R.layout.activity_tiebadetails_view

    companion object {
        fun start(data: TieZi?) {
            ActivityUtils.startActivity(
                    bundleOf(Pair(KEY_DATA, data)), TieBaDetailsActivity::class.java)
        }
    }
}