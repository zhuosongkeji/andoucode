package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.KeyboardUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.SquareCommentAdapter
import com.zskjprojectj.andouclient.adapter.SquareImgAdapter
import com.zskjprojectj.andouclient.adapter.bindTieZi
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.TieBa
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_tiebadetails_view.*
import kotlinx.android.synthetic.main.item_squarefragment.view.*

class TieBaDetailsActivity : BaseActivity() {

    val adapter = SquareCommentAdapter()
    val imgAdapter = SquareImgAdapter()
    var id = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "贴吧详情")
        id = intent.getStringExtra(KEY_DATA)
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load({
            ApiUtils.getApiService().tieBaDetail(id, pageLoadUtil.page)
        }, { _, result ->
            if (result is TieBa)
                bindData(result)
        })
        adapter.setOnItemClickListener { _, _, position ->
            showCommentContainer("回复 ：${adapter.getItem(position)?.name}", adapter.getItem(position)?.id)
        }
    }

    private fun showCommentContainer(hint: String, replyCommentId: String?) {
        commentContainer.visibility = View.VISIBLE
        commentEdt.hint = hint
        commentEdt.requestFocus()
        KeyboardUtils.showSoftInput(commentEdt)
        sendComment.setOnClickListener {
            RequestUtil.request(mActivity, true, false,
                    {
                        ApiUtils.getApiService().replyComment(
                                LoginInfoUtil.getUid(),
                                id,
                                commentEdt.text.toString(),
                                replyCommentId
                        )
                    },
                    {
                        commentContainer.visibility = View.GONE
                        commentEdt.setText("")
                        KeyboardUtils.hideSoftInput(commentEdt)
                    })
        }
    }

    override fun onBackPressed() {
        if (commentContainer.visibility == View.VISIBLE) {
            commentContainer.visibility = View.GONE
            commentEdt.setText("")
            KeyboardUtils.hideSoftInput(commentEdt)
        } else {
            super.onBackPressed()
        }
    }

    fun bindData(tieZi: TieBa?) {
        val view = layoutInflater.inflate(R.layout.item_squarefragment, null)
        view.commentBtn.setOnClickListener {
            showCommentContainer("你想说什么就说吧", null)
        }
        bindTieZi(mActivity, view, tieZi, imgAdapter, false)
        adapter.addHeaderView(view)
        adapter.addHeaderView(layoutInflater.inflate(R.layout.layout_comment_text, null))
    }

    override fun getContentView() = R.layout.activity_tiebadetails_view

    companion object {
        fun start(id: String?) {
            ActivityUtils.startActivity(
                    bundleOf(Pair(KEY_DATA, id)), TieBaDetailsActivity::class.java)
        }
    }
}