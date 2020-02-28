package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.bigkoo.pickerview.builder.OptionsPickerBuilder
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.SelectPicAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.TieBaType
import kotlinx.android.synthetic.main.activity_tie_ba_release.*

class TieBaReleaseActivity : BaseActivity() {

    val adapter = SelectPicAdapter(6)
    var selectedType: TieBaType? = null
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
    }
}
