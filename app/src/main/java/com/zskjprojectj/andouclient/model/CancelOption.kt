package com.zskjprojectj.andouclient.model

import com.contrarywind.interfaces.IPickerViewData

class CancelOption(val id: String, val name: String) : IPickerViewData {
    override fun getPickerViewText() = name
}
