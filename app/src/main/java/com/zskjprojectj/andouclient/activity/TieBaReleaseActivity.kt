package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import android.text.TextUtils
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.SelectPicAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.TieBaType
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import kotlinx.android.synthetic.main.activity_tie_ba_release.*

class TieBaReleaseActivity : BaseActivity() {

    val adapter = SelectPicAdapter(6)
    var selectedType: TieBaType? = null
    private var is_top: String = ""

    override fun getContentView() = R.layout.activity_tie_ba_release

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "发布贴吧信息")
        adapter.bindToRecyclerView(recyclerView)
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
            if(yes.isChecked){
                is_top="1"
            }else{
                is_top="0"
            }
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
                    RequestUtil.request(mActivity, true, true,
                            {
                                ApiUtils.getApiService().releaseTieBa(LoginInfoUtil.getUid(),
                                        titleTxt.text.toString(),
                                        contentTxt.text.toString(),
                                        null,
                                        selectedType?.id,
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
}
