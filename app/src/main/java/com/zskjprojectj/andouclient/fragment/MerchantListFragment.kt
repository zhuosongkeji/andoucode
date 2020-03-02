package com.zskjprojectj.andouclient.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import com.blankj.utilcode.util.BarUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity
import com.zskjprojectj.andouclient.adapter.MerchantAdapter
import com.zskjprojectj.andouclient.adapter.MerchantListAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.Merchant
import kotlinx.android.synthetic.main.dialog_shop_comprehensive.view.*
import kotlinx.android.synthetic.main.dialog_shop_comprehensive_view.view.*
import kotlinx.android.synthetic.main.fragment_merchant_list.*

class MerchantListFragment : BaseFragment() {
    private var mPopWindow: PopupWindow? = null
    val adapter = MerchantListAdapter()
    var merchantTypeId: String? = null
    var type: String? = null
    private var pageLoadUtil: PageLoadUtil<Merchant>? = null
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        actionBarContainer.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        recyclerView?.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))

        adapter.setOnItemChildClickListener { _, _, position ->
            when (adapter.getItem(position)?.merchant_type_id) {
                "2" -> MallShoppingHomeActivity.start(adapter.getItem(position)?.id)
                "3" -> HotelDetailActivity.start(adapter.getItem(position)?.id)
                "4" -> RestaurantDetailActivity.start(adapter.getItem(position)?.id)
            }
        }
        ll_merchants_classification.setOnClickListener {
            initMerchantTypeFilter()
            if (mPopWindow != null && mPopWindow?.isShowing == false) {
                mPopWindow?.showAsDropDown(mClassify, 0, 0)
            }
        }
        ll_sorting_way.setOnClickListener {
            initSort()
            if (mPopWindow != null && mPopWindow?.isShowing == false) {
                mPopWindow?.showAsDropDown(mClassify, 0, 0)
            }
        }
        pageLoadUtil = PageLoadUtil.get(activity as BaseActivity?, recyclerView, adapter, refreshLayout)
        pageLoadUtil?.load {
            ApiUtils.getApiService().merchants_two(
                    merchantTypeId,
                    type,
                    pageLoadUtil?.page ?: 1)
        }
    }

    private fun initPopupWindow(contentView: View, textView: TextView?) {
        mPopWindow = PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        mPopWindow?.contentView = contentView
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        activity?.window?.attributes?.alpha = 0.8f
        mPopWindow?.isFocusable = true
        mPopWindow?.animationStyle = R.style.popmenu_animation //动画
        mPopWindow?.isOutsideTouchable = true
        mPopWindow?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        mPopWindow?.setOnDismissListener {
            activity?.window?.attributes?.alpha = 1f
            textView?.setTextColor(Color.parseColor("#646464"))
        }
    }

    private fun initMerchantTypeFilter() {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_shop_comprehensive_view, null)
        initPopupWindow(contentView, mCapacitySort)
        val adapter = MerchantAdapter()
        adapter.onItemClickListener = BaseQuickAdapter.OnItemClickListener { _, _, position ->
            merchantTypeId = adapter.getItem(position)?.id
            pageLoadUtil?.refresh()
            mPopWindow?.dismiss()
        }
        contentView.mMerchantRecycler.adapter = adapter
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().merchanttype() },
                { adapter.setNewData(it.data.merchant_type) })
    }

    private fun initSort() {
        val contentView = LayoutInflater.from(context).inflate(R.layout.dialog_shop_comprehensive, null)
        initPopupWindow(contentView, mCapacitySort)
        contentView.mPopuunlimited.setOnClickListener {
            type = null
            pageLoadUtil?.refresh()
            mPopWindow?.dismiss()
        }
        contentView.mPopucredit.setOnClickListener {
            type = "1"
            pageLoadUtil?.refresh()
            mPopWindow?.dismiss()
        }
        contentView.mPopuprice.visibility = View.GONE
    }

    override fun getContentView() = R.layout.fragment_merchant_list
}