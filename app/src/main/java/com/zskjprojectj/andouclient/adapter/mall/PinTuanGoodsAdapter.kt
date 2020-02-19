package com.zskjprojectj.andouclient.adapter.mall

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseViewHolder
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zhuosongkj.android.library.util.FormatUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.model.PinTuanGoods

class PinTuanGoodsAdapter : BaseAdapter<PinTuanGoods>(R.layout.layout_pin_tuan_goods_list_item) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: PinTuanGoods) {
        helper.setText(R.id.titleTxt, item.name)
                .setText(R.id.peopleTxt, item.joinPeople.toString() + "已拼团")
                .setProgress(R.id.progressBar, item.progress)
                .setText(R.id.newPriceTxt, FormatUtil.getMoneyString(item.price))
        val textView = helper.getView<TextView>(R.id.oldPriceTxt)
        textView.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
        textView.text = "￥${FormatUtil.getMoneyString(item.oldPrice)}"
        Glide.with(mContext)
                .load(item.img)
                .into((helper.getView<View>(R.id.img) as ImageView))
        helper.addOnClickListener(R.id.tv_buy_now)
    }
}