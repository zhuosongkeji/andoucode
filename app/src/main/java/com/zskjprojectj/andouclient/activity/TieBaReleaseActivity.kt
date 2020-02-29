package com.zskjprojectj.andouclient.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.UploadPicAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.TieBaType
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_tie_ba_release.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


class TieBaReleaseActivity : BaseActivity() {

    val adapter = UploadPicAdapter(6)
    var selectedType: TieBaType? = null
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
                TextUtils.isEmpty(phoneTxt.text.toString()) -> {
                    ToastUtils.showShort("请输入手机号!")
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
                        builder.addFormDataPart("images[]", file.name, RequestBody.create(MediaType.parse("multipart/form-data"), file))
                    }
                    val requestBody = builder.build()
                    RequestUtil.request(mActivity, true, false,
                            {
                                ApiUtils.getApiService().releaseTieBa(requestBody)
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

    override fun getContentView() = R.layout.activity_tie_ba_release
}
