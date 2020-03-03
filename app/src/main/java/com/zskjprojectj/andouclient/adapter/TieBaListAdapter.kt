package com.zskjprojectj.andouclient.adapter

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.chad.library.adapter.base.BaseViewHolder
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.TieBa
import com.zskjprojectj.andouclient.utils.Constants
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.dialog_share_tieba.view.*
import kotlinx.android.synthetic.main.item_squarefragment.view.*
import java.io.ByteArrayOutputStream

class TieBaListAdapter : BaseAdapter<TieBa>(R.layout.item_squarefragment) {

    override fun convert(helper: BaseViewHolder, item: TieBa) {
        val adapter = SquareImgAdapter()
        val commentAdapter = SquareCommentAdapter()
        bindTieZi(mContext, helper.itemView, item, adapter, item.comment_count.toInt() > 0)
        helper.itemView.commentRecyclerView.adapter = commentAdapter
        helper.itemView.commentRecyclerView.setOnTouchListener { _, event -> helper.itemView.onTouchEvent(event) }
        commentAdapter.setNewData(item.comments)
    }
}

fun bindTieZi(context: Context, view: View, item: TieBa?,
              adapter: SquareImgAdapter, commentVisible: Boolean) {
    view.nameTxt.text = item?.name
    view.contentTxt.text = item?.content
    view.dateTxt.text = item?.created_at
    view.likeTxt.text = item?.vote
    view.isTop.visibility = if (item?.top_post == 1) View.VISIBLE else View.GONE
    view.likeView.isSelected = item?.is_vote == "1"
    view.likeView.setOnClickListener {
        RequestUtil.request(context as BaseActivity?, true, false,
                {
                    ApiUtils.getApiService().tieBaLike(
                            LoginInfoUtil.getUid(),
                            item?.id,
                            if (it.isSelected) "0" else "1")
                },
                {
                    RequestUtil.request(context, true, false, {
                        ApiUtils.getApiService().tieBaDetail(item?.id, 1)
                    }, {
                        item?.vote = it.data.vote
                        view.likeTxt.text = item?.vote
                    })
                })
    }
    view.commentTxt.text = item?.comment_count
    view.shareTxt.text = item?.share
    view.titleTxt.text = item?.title
    Glide.with(context).load(item?.avator)
            .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
            .into(view.avatarImg)
    view.recyclerView.adapter = adapter
    view.recyclerView.setOnTouchListener { _, event -> view.onTouchEvent(event) }
    adapter.setNewData(item?.images)
    view.commentRecyclerView.visibility = if (commentVisible) View.VISIBLE else View.GONE
    view.shareView.setOnClickListener {
        val shareDialog = Dialog(context, R.style.BottomDialog)
        shareDialog.window?.decorView?.setPadding(0, 0, 0, 0)
        shareDialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        shareDialog.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        val dialogContentView = LayoutInflater.from(context).inflate(R.layout.dialog_share_tieba, null)
        shareDialog.setContentView(dialogContentView)
        shareDialog.window?.setGravity(Gravity.BOTTOM)
        shareDialog.setCanceledOnTouchOutside(true)
        shareDialog.window?.setWindowAnimations(R.style.BottomDialog_Animation)
        shareDialog.show()

        dialogContentView.weixin.setOnClickListener {
            requestShare(context, item?.id, SendMessageToWX.Req.WXSceneSession)
            shareDialog.dismiss()
        }
        dialogContentView.weixinquan.setOnClickListener {
            requestShare(context, item?.id, SendMessageToWX.Req.WXSceneTimeline)
            shareDialog.dismiss()
        }
        dialogContentView.qq.setOnClickListener {

        }
        dialogContentView.qqquan.setOnClickListener {

        }
    }
}

fun requestShare(context: Context, postId: String?, type: Int) {
    RequestUtil.request(context as BaseActivity, true, false,
            { ApiUtils.getApiService().tieBaShare(postId) },
            {
                Glide.with(context)
                        .asBitmap()
                        .load(it.data.img)
                        .into(object : SimpleTarget<Bitmap>() {
                            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                val api = WXAPIFactory.createWXAPI(context, Constants.APP_ID, false)// 检查手机或者模拟器是否安装了微信
                                if (!api.isWXAppInstalled) {
                                    ToastUtils.showLong("您还没有安装微信")
                                    return
                                }
                                val webpage = WXWebpageObject()
                                webpage.webpageUrl = it.data.link
                                val msg = WXMediaMessage(webpage)
                                msg.title = it.data.title
                                msg.description = it.data.desc
                                val os = ByteArrayOutputStream()
                                resource.compress(Bitmap.CompressFormat.JPEG, 100, os)
                                msg.thumbData = os.toByteArray()
                                val req = SendMessageToWX.Req()
                                req.transaction = "webpage"
                                req.message = msg
                                req.scene = type
                                api.sendReq(req)
                            }
                        })
            })
}
