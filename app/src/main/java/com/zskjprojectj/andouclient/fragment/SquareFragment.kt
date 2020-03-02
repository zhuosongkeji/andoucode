package com.zskjprojectj.andouclient.fragment

import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.util.Util
import com.facebook.imageutils.BitmapUtil
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject
import com.tencent.mm.opensdk.openapi.WXAPIFactory
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.TieBaDetailsActivity
import com.zskjprojectj.andouclient.adapter.TieBaListAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.Constants
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.dialog_share_tieba.view.*
import kotlinx.android.synthetic.main.framgent_pin_tuan.*
import java.io.ByteArrayOutputStream


open class SquareFragment : BaseFragment() {

    val adapter = TieBaListAdapter()
    override fun getContentView() = R.layout.fragment_square
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.setOnItemClickListener { _, _, position ->
            TieBaDetailsActivity.start(adapter.getItem(position)?.id)
        }
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil.load {
            ApiUtils.getApiService().tieBaList(
                    LoginInfoUtil.getUid(),
                    null,
                    pageLoadUtil.page)
        }
    }

}
