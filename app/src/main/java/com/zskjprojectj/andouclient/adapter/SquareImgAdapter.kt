package com.zskjprojectj.andouclient.adapter

import android.app.Activity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseViewHolder
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.luck.picture.lib.entity.LocalMedia
import com.zhuosongkj.android.library.adapter.BaseAdapter
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.utils.GlideEngine
import com.zskjprojectj.andouclient.utils.UrlUtil
import kotlinx.android.synthetic.main.squareimg_view.view.*

class SquareImgAdapter : BaseAdapter<String>(R.layout.squareimg_view) {
    override fun convert(helper: BaseViewHolder, item: String) {
        Glide.with(mContext).load(UrlUtil.getImageUrl(item))
                .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                .into(helper.itemView.img)
        helper.itemView.setOnClickListener {
            PictureSelector.create(mContext as? Activity)
                    .themeStyle(R.style.picture_default_style)
                    .isNotPreviewDownload(true)
                    .loadImageEngine(GlideEngine.createGlideEngine())
                    .openExternalPreview(helper.adapterPosition, data.map {
                        LocalMedia(UrlUtil.getImageUrl(item), 0, PictureMimeType.ofImage(), PictureMimeType.JPEG)
                    });
        }
    }
}
