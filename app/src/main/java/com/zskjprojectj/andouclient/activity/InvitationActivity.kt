package com.zskjprojectj.andouclient.activity

import android.app.AlertDialog
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.UrlUtil.getImageUrl
import kotlinx.android.synthetic.main.activity_invitation.*
import java.io.File

class InvitationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.setStatusBarColor(mActivity, Color.parseColor("#FC692B"))
        BarUtils.setStatusBarLightMode(mActivity, false)
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().invitationsvip(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                {
                    Glide.with(mActivity).load(getImageUrl(it.data.avator))
                            .into(img_toptouxiang)
                    Glide.with(mActivity).load(getImageUrl(it.data.qrcode))
                            .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                            .into(img_code)
                    tv_username.text = it.data.name
                    img_code.setOnClickListener { _ ->
                        AlertDialog.Builder(mActivity)
                                .setMessage("是否确定将邀请二维码下载到手机?")
                                .setNegativeButton("取消", null)
                                .setPositiveButton("确定") { _, _ ->
                                    Glide.with(mActivity)
                                            .asFile()
                                            .load(getImageUrl(it.data.qrcode))
                                            .into(object : SimpleTarget<File>() {
                                                override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                                                    FileUtils.copy(resource,
                                                            File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                                                                    resource.name.replace(".", "") + ".png")
                                                    ) { _, file ->
                                                        ToastUtils.showShort("邀请二维码以保存到${file.path}")
                                                        return@copy false
                                                    }
                                                }
                                            })
                                }
                                .show()
                    }
                })
    }

    override fun getContentView() = R.layout.activity_invitation
}