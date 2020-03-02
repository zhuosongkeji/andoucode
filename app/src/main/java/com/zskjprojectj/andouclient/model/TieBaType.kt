package com.zskjprojectj.andouclient.model

import com.contrarywind.interfaces.IPickerViewData

class TieBaType(val id: Long, val type_name: String) : IPickerViewData {
    override fun getPickerViewText() = type_name
}
