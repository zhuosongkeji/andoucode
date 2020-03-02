package com.zskjprojectj.andouclient.activity.hotel

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import com.bigkoo.pickerview.builder.TimePickerBuilder
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.TimeUtils
import com.google.android.material.appbar.AppBarLayout
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.hotel.HotelCateGoryAdapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelHomeAdapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelPriceAdapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelStarAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.GridSectionAverageGapItemDecoration
import kotlinx.android.synthetic.main.activity_hotel_home.*
import kotlinx.android.synthetic.main.dialog_hotel_star_price.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs


class HotelHomeActivity : BaseActivity() {


    private val hotelCateGoryAdapter = HotelCateGoryAdapter()
    private val hoteladapter = HotelHomeAdapter()
    private var hotelStar: String? = null
    private val keywords: String? = null
    private var startPrice: String? = null
    private var endPrice: String? = null
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(mActivity)
        BarUtils.setStatusBarLightMode(mActivity, false)
        initActionBar()
        search_hotel.setOnClickListener {
            ActivityUtils.startActivity(mActivity, HotelFilterActivity::class.java)
        }
        mTvCheckInTime.text = TimeUtils.getNowString(
                SimpleDateFormat("yyyy 年 MM 月 dd 日", Locale.getDefault()))
        mTVCheckOutTime.text = mTvCheckInTime.text
        hotelCateGoryAdapter.bindToRecyclerView(mHotelCategoryRecyclerView)
        hoteladapter.setOnItemClickListener { _, _, position ->
            HotelDetailActivity.start(hoteladapter.getItem(position)?.id)
        }
        hotelCateGoryAdapter.setOnItemClickListener { _, _, position ->
            HotelFilterActivity.start(hotelCateGoryAdapter.getItem(position)?.id)
        }
        rv_check_in_time.setOnClickListener {
            TimePickerBuilder(mActivity) { date, _ ->
                mTvCheckInTime.text = TimeUtils.date2String(date,
                        SimpleDateFormat("yyyy 年 MM 月 dd 日", Locale.getDefault()))
            }.build().show()
        }
        rv_check_out_time.setOnClickListener {
            TimePickerBuilder(mActivity) { date, _ ->
                mTVCheckOutTime!!.text = TimeUtils.date2String(date,
                        SimpleDateFormat("yyyy 年 MM 月 dd 日", Locale.getDefault()))
            }.build().show()
        }
        mTvSetLike.setOnClickListener {
            val bottomDialog = Dialog(this, R.style.BottomDialog)

            val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_star_price, null)
            val priceAdapter = HotelPriceAdapter(R.layout.item_section_content)
            priceAdapter.bindToRecyclerView(contentView.rv_price_recycler)
            priceAdapter.setOnItemClickListener { _, _, position ->
                priceAdapter.onChange(position)
                priceAdapter.notifyDataSetChanged()
            }
            val starAdapter = HotelStarAdapter(R.layout.item_section_content)
            starAdapter.bindToRecyclerView(contentView.rv_star_recycler)
            starAdapter.setOnItemClickListener { _, _, position ->
                starAdapter.onChange(position)
                starAdapter.notifyDataSetChanged()
            }
            contentView.rv_price_recycler.addItemDecoration(GridSectionAverageGapItemDecoration(10f, 10f, 0f, 10f))
            contentView.rv_star_recycler.addItemDecoration(GridSectionAverageGapItemDecoration(10f, 10f, 0f, 10f))
            bottomDialog.window?.decorView?.setPadding(0, 0, 0, 0)
            bottomDialog.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
            bottomDialog.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
            contentView.bt_cancle.setOnClickListener {
                priceAdapter.cancel(-1)
                priceAdapter.notifyDataSetChanged()
                starAdapter.cancel(-1)
                starAdapter.notifyDataSetChanged()
            }
            contentView.confirm.setOnClickListener {
                bottomDialog.dismiss()
                val priceItem = priceAdapter.getItem(HotelPriceAdapter.SELECTOR_POSITION)
                startPrice = priceItem?.start
                endPrice = priceItem?.end
                val priceStr = "${priceItem?.start}~${priceItem?.end}"
                hotelStar = starAdapter.getItem(HotelStarAdapter.SELECTOR_POSITION)?.name
                if (priceItem == null && hotelStar == null) {
                    mTvSetLike.text = "设置我喜欢的星级/价格"
                } else if (priceItem != null && hotelStar != null) {
                    mTvSetLike.text = "$priceStr/$hotelStar"
                } else {
                    mTvSetLike.text = "${if (priceItem == null) "" else priceStr}${hotelStar ?: ""}"
                }
            }
            RequestUtil.request(mActivity, true, true,
                    { ApiUtils.getApiService().hotelSearchCondition() },
                    {
                        priceAdapter.setNewData(it.data.price_range)
                        starAdapter.setNewData(it.data.star)
                        bottomDialog.show()
                    })
            bottomDialog.setContentView(contentView)
            bottomDialog.window?.setGravity(Gravity.BOTTOM)
            bottomDialog.setCanceledOnTouchOutside(true)
            bottomDialog.window?.setWindowAnimations(R.style.BottomDialog_Animation)
        }
        refreshLayout.setEnableRefresh(false)
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().hotelCategory() },
                {
                    hotelCateGoryAdapter.setNewData(it.data)
                })
        val pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, hoteladapter, refreshLayout)
        pageLoadUtil.load {
            ApiUtils.getApiService().hotelHomeList(
                    keywords,
                    startPrice,
                    endPrice,
                    hotelStar,
                    "",
                    pageLoadUtil.page)
        }
    }

    private fun initActionBar() {
        ActionBarUtil.setTitle(mActivity, "酒店住宿", false)
        ActionBarUtil.getBackground(mActivity, false).alpha = 0f
        appBarLayout.addOnOffsetChangedListener(
                AppBarLayout.OnOffsetChangedListener { _, verticalOffset ->
                    ActionBarUtil.getBackground(mActivity, false).alpha = abs(verticalOffset) * 0.01f
                })
    }

    override fun getContentView() = R.layout.activity_hotel_home
}
