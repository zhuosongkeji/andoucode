package com.zskjprojectj.andouclient.activity.mall

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.SimpleCallback
import com.lxj.xpopup.util.KeyboardUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA
import com.zskjprojectj.andouclient.adapter.MallGoodsListAdapter
import com.zskjprojectj.andouclient.entity.mall.MallGoodsListBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.CustomPartShadowPopupView
import com.zskjprojectj.andouclient.utils.KEY_IS_RECOMMEND
import com.zskjprojectj.andouclient.utils.KEY_IS_SPECIAL
import kotlinx.android.synthetic.main.activity_mall_goods_list.ll_price_comprehensive
import kotlinx.android.synthetic.main.activity_mall_goods_list.ll_selector_price
import kotlinx.android.synthetic.main.activity_mall_goods_list.ll_selector_sales
import kotlinx.android.synthetic.main.activity_mall_goods_list.mIvCapacitySort
import kotlinx.android.synthetic.main.activity_mall_goods_list.mIvScreen
import kotlinx.android.synthetic.main.activity_mall_goods_list.mIvSelectorImg
import kotlinx.android.synthetic.main.activity_mall_goods_list.mSearchEditText
import kotlinx.android.synthetic.main.activity_mall_goods_list.mCapacitySort
import kotlinx.android.synthetic.main.activity_mall_goods_list.mTvScreen
import kotlinx.android.synthetic.main.activity_mall_goods_list.mTvSelectorStar
import kotlinx.android.synthetic.main.activity_mall_goods_list.recyclerView
import kotlinx.android.synthetic.main.activity_mall_goods_list.refreshLayout
import kotlinx.android.synthetic.main.activity_mall_search_goods.*

class MallSearchGoodsActivity : BaseActivity() {
    var keyword = ""
    var priceSort: String? = null
    var volumeSort: String? = null
    var startSort: String? = null
    var popupView: CustomPartShadowPopupView? = null
    val adapter = MallGoodsListAdapter()
    var pageLoadUtil: PageLoadUtil<MallGoodsListBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setOnItemClickListener { _, _, position ->
            MallGoodsDetailsActivity.start(adapter.getItem(position)?.id,"PUTONG") }
        pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        mSearchEditText.requestFocus()
        KeyboardUtils.showSoftInput(mSearchEditText)
        mSearchEditText.setOnEditorActionListener { _, _, _ ->
            keyword = mSearchEditText.text.toString()
            if (keyword.isEmpty()) {
                ToastUtils.showShort("请输入要搜索的关键字")
                return@setOnEditorActionListener true
            }
            KeyboardUtils.hideSoftInput(mSearchEditText)
            pageLoadUtil?.load({
                ApiUtils.getApiService().mallGoodsList(
                        keyword,
                        intent.getStringExtra(KEY_DATA),
                        intent.getStringExtra(KEY_IS_RECOMMEND),
                        intent.getStringExtra(KEY_IS_SPECIAL),
                        priceSort,
                        volumeSort,
                        startSort,
                        pageLoadUtil?.page ?: 1
                )
            }, { refresh, data ->
                if (refresh && data.dataList.size == 0) {
                    sortContainer.visibility = View.GONE
                } else {
                    sortContainer.visibility = View.VISIBLE
                }
            })
            true
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

    override fun getContentView() = R.layout.activity_mall_search_goods

    companion object {
        fun start() {
            ActivityUtils.startActivity(MallSearchGoodsActivity::class.java)
        }
    }
}
