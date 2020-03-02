package com.zskjprojectj.andouclient.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.zskjprojectj.andouclient.R

class GuideAdapter(fragmentManager: FragmentManager) : PagerAdapter() {
    val imgs = arrayListOf(R.mipmap.bg_guid_01, R.mipmap.bg_guid_02, R.mipmap.bg_guid_03)
    var onStartClickListener: View.OnClickListener? = null
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageView = ImageView(container.context)
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        container.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        Glide.with(container.context).load(imgs[position]).into(imageView)
        if (position == 2) {
            imageView.setOnClickListener(onStartClickListener)
        }
        return imageView
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }

    override fun getCount() = imgs.size
}