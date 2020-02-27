package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.UrlUtil.getImageUrl
import kotlinx.android.synthetic.main.activity_invitation.*

class InvitationActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().invitationsvip(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                {
                    Glide.with(mActivity).load(getImageUrl(it.data.avator))
                            .into(img_toptouxiang)
                    Glide.with(mActivity).load(getImageUrl(it.data.qrcode))
                            .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                            .into((findViewById<View>(R.id.img_code) as ImageView))
                    tv_username.text = it.data.name
                })
    }

    override fun getContentView(): Int {
        return R.layout.activity_invitation
    }
}