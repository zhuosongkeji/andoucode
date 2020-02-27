package com.zskjprojectj.andouclient.utils

import android.text.TextUtils

import com.zskjprojectj.andouclient.utils.UrlUtil
import com.zskjprojectj.andouclient.base.BaseUrl

object UrlUtil {
    fun getImageUrl(path: String?): String {
        if (path == null)
            return ""
        if (path.startsWith("http://") || path.startsWith("https://")) {
            return path
        }
        if (TextUtils.isEmpty(path))
            return ""
        var newpath = path
        if (!path.startsWith("/")) {
            newpath = "/$path"
        }
        return BaseUrl.BASE_URL + newpath
    }
}
