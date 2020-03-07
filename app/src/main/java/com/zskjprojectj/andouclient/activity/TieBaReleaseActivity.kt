package com.zskjprojectj.andouclient.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.UploadPicAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.event.PostSuccessEvent
import com.zskjprojectj.andouclient.model.TieBaType
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_tie_ba_release.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.greenrobot.eventbus.EventBus
import java.io.File


class TieBaReleaseActivity : BaseActivity() {

    val adapter = UploadPicAdapter(6)
    var selectedType: TieBaType? = null
    var postId = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "发布贴吧信息")
        adapter.bindToRecyclerView(recyclerView)
        adapter.activity = mActivity
        typeContainer.setOnClickListener {
            RequestUtil.request(mActivity, true, false,
                    { ApiUtils.getApiService().tieBaTypes() },
                    {
                        val picker = OptionsPickerBuilder(mActivity) { option1, _, _, _ ->
                            selectedType = it.data[option1]
                            typeTxt.text = selectedType?.type_name
                        }.build<TieBaType>()
                        picker.setPicker(it.data)
                        picker.show()
                    })
        }
        submitBtn.setOnClickListener {
            when {
                selectedType == null -> {
                    ToastUtils.showShort("请选择发帖类型")
                }
                TextUtils.isEmpty(titleTxt.text.toString()) -> {
                    ToastUtils.showShort("请输入标题!")
                }
                TextUtils.isEmpty(contentTxt.text.toString()) -> {
                    ToastUtils.showShort("请输入内容!")
                }
                !RegexUtils.isMobileSimple(phoneTxt.text.toString()) -> {
                    ToastUtils.showShort("请输入正确手机号!")
                }
                else -> {
                    val builder = MultipartBody.Builder()
                    builder.setType(MultipartBody.FORM)
                    builder.addFormDataPart("uid", LoginInfoUtil.getUid())
                    builder.addFormDataPart("title", titleTxt.text.toString())
                    builder.addFormDataPart("content", contentTxt.text.toString())
                    builder.addFormDataPart("type_id", selectedType!!.id.toString())
                    builder.addFormDataPart("contact_info", phoneTxt.text.toString())
                    builder.addFormDataPart("top_post", if (yes.isChecked) "1" else "0")
                    adapter.selectedPics.forEach {
                        val file = File(it.path)
                        builder.addFormDataPart("images[]", file.name, file.asRequestBody("multipart/form-data".toMediaTypeOrNull()))
                    }
                    val requestBody = builder.build()
                    RequestUtil.request(mActivity, true, false,
                            {
                                ApiUtils.getApiService().releaseTieBa(requestBody)
                            },
                            {
                                postId = it.data.post_id
                                if (yes.isChecked) {
                                    TieBaPayActivity.start(mActivity, postId, 666)
                                } else {
                                    ToastUtils.showShort("发帖成功!")
                                    TieBaDetailsActivity.start(postId.toString())
                                    EventBus.getDefault().post(PostSuccessEvent())
                                    finish()
                                }
                            })
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        adapter.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 666 && resultCode == Activity.RESULT_OK) {
            ToastUtils.showShort("发帖成功!")
            EventBus.getDefault().post(PostSuccessEvent())
            TieBaDetailsActivity.start(postId.toString())
            finish()
        }
    }

    override fun getContentView() = R.layout.activity_tie_ba_release
}
