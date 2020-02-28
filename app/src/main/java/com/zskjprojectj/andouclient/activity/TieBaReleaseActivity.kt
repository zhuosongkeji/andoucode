package com.zskjprojectj.andouclient.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.SelectPicAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import kotlinx.android.synthetic.main.activity_tie_ba_release.*

class TieBaReleaseActivity : BaseActivity() {

    val adapter = SelectPicAdapter(6)
    private var is_top: Boolean = true

    override fun getContentView() = R.layout.activity_tie_ba_release

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "发布贴吧信息")
        adapter.bindToRecyclerView(recyclerView)
        submitBtn.setOnClickListener {
            is_top = yes.isChecked
            when {
                TextUtils.isEmpty(titleTxt.text.toString()) -> {
                    ToastUtils.showShort("请输入标题!")
                }
                TextUtils.isEmpty(contentTxt.text.toString()) -> {
                    ToastUtils.showShort("请输入内容!")
                }
                TextUtils.isEmpty(phoneTxt.text.toString()) -> {
                    ToastUtils.showShort("请输入手机号!")
                }
                else -> {
                    RequestUtil.request(mActivity, true, true,
                            {
                                ApiUtils.getApiService().releaseTieBa(LoginInfoUtil.getUid(),
                                        titleTxt.text.toString(),
                                        contentTxt.text.toString(),
                                        null,
                                        "1",
                                        phoneTxt.text.toString(),
                                        is_top)
                            },
                            {
                                ToastUtils.showShort("发帖成功")
                                finish()
                            })
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adapter.onActivityResult(requestCode, resultCode, data)
    }
}
