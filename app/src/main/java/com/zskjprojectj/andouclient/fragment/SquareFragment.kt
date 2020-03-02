package com.zskjprojectj.andouclient.fragment

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.blankj.utilcode.util.ToastUtils
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.SquareAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.Constants
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_shared_dialog_view.view.*
import kotlinx.android.synthetic.main.dialog_share_tieba.view.*
import kotlinx.android.synthetic.main.dialog_share_tieba.view.qq
import kotlinx.android.synthetic.main.dialog_share_tieba.view.weixin
import kotlinx.android.synthetic.main.framgent_pin_tuan.*


open class SquareFragment : BaseFragment() {

    val adapter = SquareAdapter()
    override fun getContentView() = R.layout.fragment_square
    val weixin=SendMessageToWX.Req.WXSceneSession
    val weixinQuan=SendMessageToWX.Req.WXSceneTimeline

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.setOnItemClickListener { _, _, position ->
            TieBaDetailsActivity.start(adapter.getItem(position)?.id)
        }
        adapter.setOnItemChildClickListener { _, view, position ->
          val shareDialog = Dialog(mActivity, R.style.BottomDialog)
            shareDialog.window?.decorView?.setPadding(0, 0, 0, 0)
            shareDialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
            shareDialog.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
            val dialogContentView = LayoutInflater.from(mActivity).inflate(R.layout.dialog_share_tieba, null)
            shareDialog.setContentView(dialogContentView)
            shareDialog.window?.setGravity(Gravity.BOTTOM)
            shareDialog.setCanceledOnTouchOutside(true)
            shareDialog.window?.setWindowAnimations(R.style.BottomDialog_Animation)
            shareDialog.show()

            dialogContentView.weixin.setOnClickListener {
                requestShare(adapter.getItem(position)?.id,weixin)
            }
            dialogContentView.weixinquan.setOnClickListener {
                requestShare(adapter.getItem(position)?.id,weixinQuan)
            }
            dialogContentView.qq.setOnClickListener {

            }
            dialogContentView.qqquan.setOnClickListener {

            }

        }
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load {
            ApiUtils.getApiService().tieBaList(
                    LoginInfoUtil.getUid(),
                    null,
                    pageLoadUtil.page)
        }
    }

    private fun requestShare(postId:String?,type:Int) {
        RequestUtil.request(mActivity,true,false,
                {ApiUtils.getApiService().tieBaShare(postId)},
                {
                    val api = WXAPIFactory.createWXAPI(mActivity, Constants.APP_ID, false)// 检查手机或者模拟器是否安装了微信
                    if (!api.isWXAppInstalled) {
                        ToastUtils.showLong("您还没有安装微信")
                        return@request
                    }
                    //初始化一个 WXTextObject 对象，填写分享的文本内容
                    val textObj = WXWebpageObject()
                    textObj.webpageUrl = it.data.link

                    //用 WXTextObject 对象初始化一个 WXMediaMessage 对象
                    val msg = WXMediaMessage()
                    msg.mediaObject = textObj
                    msg.title=it.data.title
                    msg.description = it.data.desc


                    val req = SendMessageToWX.Req()
                    req.transaction = "tieBa"
                    req.message = msg
                    req.scene = type
                    //调用api接口，发送数据到微信
                    api.sendReq(req)
                })
    }
}
