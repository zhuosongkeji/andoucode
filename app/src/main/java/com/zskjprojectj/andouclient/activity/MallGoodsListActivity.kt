package com.zskjprojectj.andouclient.activity

import android.graphics.Color
import android.os.Bundle
import androidx.core.os.bundleOf
import com.blankj.utilcode.util.ActivityUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.SimpleCallback
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity
import com.zskjprojectj.andouclient.activity.mall.MallSearchGoodsActivity
import com.zskjprojectj.andouclient.adapter.MallGoodsListAdapter
import com.zskjprojectj.andouclient.entity.mall.MallGoodsListBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.CustomPartShadowPopupView
import com.zskjprojectj.andouclient.utils.KEY_IS_RECOMMEND
import com.zskjprojectj.andouclient.utils.KEY_IS_SPECIAL
import kotlinx.android.synthetic.main.activity_mall_goods_list.*


class MallGoodsListActivity : BaseActivity() {

    private var priceSort: String? = null
    private var volumeSort: String? = null
    private var startSort: String? = null
    private var popupView: CustomPartShadowPopupView? = null
    val adapter = MallGoodsListAdapter()
    var pageLoadUtil: PageLoadUtil<MallGoodsListBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mSearchEditText.setOnClickListener { MallSearchGoodsActivity.start() }
        adapter.setOnItemClickListener { _, _, position -> MallGoodsDetailsActivity.start(adapter.getItem(position)?.id) }
        pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil?.load {
            ApiUtils.getApiService().mallGoodsList(
                    "",
                    intent.getStringExtra(KEY_DATA),
                    intent.getStringExtra(KEY_IS_RECOMMEND),
                    intent.getStringExtra(KEY_IS_SPECIAL),
                    priceSort,
                    volumeSort,
                    startSort,
                    pageLoadUtil?.page ?: 1
            )
        }
        ll_price_comprehensive.setOnClickListener {
            mTvSelectorStar.setTextColor(Color.parseColor("#5ED3AE"))
            mIvSelectorImg.setImageResource(R.mipmap.icon_cate_img)
            if (popupView == null) {
                popupView = XPopup.Builder(mActivity)
                        .atView(it)
                        .autoOpenSoftInput(false)
                        .setPopupCallback(object : SimpleCallback() {
                            override fun onShow() {
                            }

                            override fun onDismiss() {
                                mTvSelectorStar.setTextColor(Color.parseColor("#646464"))
                                mIvSelectorImg.setImageResource(R.mipmap.ic_busiess_xiala)
                                popupView = null
                            }
                        })
                        .asCustom(CustomPartShadowPopupView(mActivity, 1)) as CustomPartShadowPopupView
                popupView?.show()
                popupView?.setOnclickItem { sort ->
                    startSort = sort
                    pageLoadUtil?.refresh()
                }

            } else if (popupView != null && popupView?.isShow == true) {
                popupView?.dismiss()
            }
        }
        ll_selector_sales.setOnClickListener {
            mCapacitySort.setTextColor(Color.parseColor("#5ED3AE"))
            mIvCapacitySort.setImageResource(R.mipmap.icon_cate_img)
            if (popupView == null) {
                popupView = XPopup.Builder(mActivity)
                        .atView(it)
                        .autoOpenSoftInput(false)
                        .setPopupCallback(object : SimpleCallback() {
                            override fun onShow() {

                            }

                            override fun onDismiss() {
                                mCapacitySort.setTextColor(Color.parseColor("#646464"))
                                mIvCapacitySort.setImageResource(R.mipmap.ic_busiess_xiala)
                                popupView = null
                            }
                        })
                        .asCustom(CustomPartShadowPopupView(mActivity, 2)) as CustomPartShadowPopupView
                popupView?.show()
                popupView?.setOnclickItem { sort ->
                    volumeSort = sort
                    pageLoadUtil?.refresh()
                }
            } else if (popupView != null && popupView?.isShow == true) {
                popupView?.dismiss()
            }
        }
        ll_selector_price.setOnClickListener {
            mTvScreen.setTextColor(Color.parseColor("#5ED3AE"))
            mIvScreen.setImageResource(R.mipmap.icon_cate_img)
            if (popupView == null) {
                popupView = XPopup.Builder(mActivity)
                        .atView(it)
                        .autoOpenSoftInput(false)
                        .setPopupCallback(object : SimpleCallback() {
                            override fun onShow() {

                            }

                            override fun onDismiss() {
                                mTvScreen.setTextColor(resources.getColor(R.color.color_common_font))
                                mIvScreen.setImageResource(R.mipmap.ic_busiess_xiala)
                                popupView = null
                            }
                        })
                        .asCustom(CustomPartShadowPopupView(mActivity, 3)) as CustomPartShadowPopupView
                popupView?.show()
                popupView?.setOnclickItem { sort ->
                    priceSort = sort
                    pageLoadUtil?.refresh()
                }
            } else if (popupView != null && popupView?.isShow == true) {
                popupView?.dismiss()
            }
        }
    }

    override fun getContentView() = R.layout.activity_mall_goods_list

    companion object {
        fun start(isRecommend: String? = null, isSpecial: String? = null, categoryId: String? = null) {
            ActivityUtils.startActivity(
                    bundleOf(
                            Pair(KEY_IS_RECOMMEND, isRecommend),
                            Pair(KEY_IS_SPECIAL, isSpecial),
                            Pair(KEY_DATA, categoryId)),
                    MallGoodsListActivity::class.java)
        }
    }
}
