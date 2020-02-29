package com.zskjprojectj.andouclient.activity.hotel

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.hotel.Catagory1Adapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelPriceAdapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelResultAdapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelStarAdapter
import com.zskjprojectj.andouclient.entity.hotel.CategoryBean
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean
import com.zskjprojectj.andouclient.entity.hotel.HotelSearchConditionBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.http.BaseObserver
import com.zskjprojectj.andouclient.http.HttpRxObservable
import com.zskjprojectj.andouclient.utils.BarUtils
import com.zskjprojectj.andouclient.utils.GridSectionAverageGapItemDecoration
import kotlinx.android.synthetic.main.activity_hotel_filter.*
import kotlinx.android.synthetic.main.dialog_hotel_location.view.*
import kotlinx.android.synthetic.main.dialog_hotel_star_price.view.*
import java.io.IOException
import java.util.*


class HotelFilterActivity : BaseActivity() {
    private var rv_price_recycler: RecyclerView? = null
    private var mStarRecycler: RecyclerView? = null
    private var mCancle: Button? = null
    private var mConfirm: Button? = null
    private var mPopWindow: PopupWindow? = null


    private var catagory2Adapter: Catagory1Adapter? = null
    private var catagory3Adapter: Catagory1Adapter? = null
    private var catagory1Adapter: Catagory1Adapter? = null
    private var datalist1: ArrayList<CategoryBean> = arrayListOf()

    private var priceAdapter: HotelPriceAdapter? = null
    private var starAdapter: HotelStarAdapter? = null
    //价格区间最小值
    private val startPrice: String? = null

    //价格区间最大值
    private val endPrice: String? = null
    //酒店星级
    private val hotelStar: String? = null
    //排序方式(不是必传 1按距离,2按点价格)
    private val type: String? = null
    //关键字搜索
    private val keywords: String? = null
    //酒店ID
    private var hotelId: String? = null
    internal var adapter = HotelResultAdapter()
    private var pageLoadUtil: PageLoadUtil<HotelHomeBean>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        com.blankj.utilcode.util.BarUtils.transparentStatusBar(mActivity)
        com.blankj.utilcode.util.BarUtils.setNavBarLightMode(mActivity, true)
        val barHeight = com.blankj.utilcode.util.BarUtils.getStatusBarHeight()
        if (barHeight > 0) {
            //设置状态栏的高度
            val layoutParams = header_title.layoutParams as LinearLayout.LayoutParams
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mActivity) + layoutParams.topMargin
            header_title.layoutParams = layoutParams
        }
        initViews()
        initData()
    }

    private fun initData() {
        pageLoadUtil = PageLoadUtil.get(mActivity, recyclerView, adapter, refreshLayout)
        pageLoadUtil?.load {
            ApiUtils.getApiService().hotelHomeList(
                    keywords,
                    startPrice,
                    endPrice,
                    hotelStar,
                    type,
                    pageLoadUtil?.page)
        }
        adapter.openLoadAnimation()
        recyclerView.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        recyclerView.adapter = adapter
        adapter.setOnItemClickListener { adapter1, view, position -> HotelDetailActivity.start(adapter.getItem(position)?.id) }

    }

    private fun initViews() {
        hotelId = intent.getStringExtra("hotelId")
        recyclerView.layoutManager = LinearLayoutManager(this)
        btn_clear.setOnClickListener {
            val content = keywordEdt.text.toString().trim { it <= ' ' }
            pageLoadUtil?.load {
                ApiUtils.getApiService().hotelHomeList(
                        content,
                        startPrice,
                        endPrice,
                        hotelStar,
                        type,
                        pageLoadUtil?.page)
            }
        }
        ll_selector_location.setOnClickListener {
            initLocation()
            if (mPopWindow != null && mPopWindow?.isShowing == false) {
                mPopWindow?.showAsDropDown(mClassify, 0, 0)
            }
        }
        ll_price_star.setOnClickListener {
            mTvSelectorStar.setTextColor(resources.getColor(R.color.colorNavy))
            initPriceStar()
            if (mPopWindow != null && mPopWindow?.isShowing == false) {
                mPopWindow?.showAsDropDown(mClassify, 0, 0)
            }
        }
        ll_selector_sort.setOnClickListener {
            initSort()
            if (mPopWindow != null && mPopWindow?.isShowing == false) {
                mPopWindow?.showAsDropDown(mClassify, 0, 0)
            }
        }
        ll_selector_screen.setOnClickListener {
            initScreen()
        }
        mHeaderBack.setOnClickListener { onBackPressed() }
    }


    private fun initScreen() {

    }

    private fun initSort() {

        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_sort, null)

        initPopuWindow(contentView, mCapacitySort)
        //不限
        contentView.findViewById<View>(R.id.tv_unlimited).setOnClickListener {
            pageLoadUtil?.load {
                ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        startPrice,
                        endPrice,
                        hotelStar,
                        type,
                        pageLoadUtil?.page)
            }
            mPopWindow?.dismiss()
        }
        //距离
        contentView.findViewById<View>(R.id.tv_distance).setOnClickListener {
            pageLoadUtil?.load {
                ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        startPrice,
                        endPrice,
                        hotelStar,
                        "1",
                        pageLoadUtil?.page)
            }
            mPopWindow?.dismiss()
        }
        //价格
        contentView.findViewById<View>(R.id.mTvPrice).setOnClickListener {
            pageLoadUtil?.load {
                ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        startPrice,
                        endPrice,
                        hotelStar,
                        "2",
                        pageLoadUtil?.page)
            }
            mPopWindow?.dismiss()
        }


    }

    private fun initPriceStar() {

        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_star_price, null)

        initPopuWindow(contentView, mTvSelectorStar)
        //价格
        rv_price_recycler = contentView.rv_price_recycler
        //星级
        mStarRecycler = contentView.rv_star_recycler
        mCancle = contentView.bt_cancle
        mConfirm = contentView.confirm
        initDialogRecycler()
    }

    //初始化popuwindow
    private fun initPopuWindow(contentView: View, textView: TextView) {
        mPopWindow = PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
        mPopWindow?.contentView = contentView

        //设置背景色
        val lp = window.attributes
        lp.alpha = 0.8f
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        window.attributes = lp

        //popupWindow获取焦点
        mPopWindow?.isFocusable = true
        mPopWindow?.animationStyle = R.style.popmenu_animation //动画
        mPopWindow?.isOutsideTouchable = true
        mPopWindow?.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
        //设置popupWindow消失时的监听
        mPopWindow?.setOnDismissListener {
            val lp = window.attributes
            lp.alpha = 1f
            window.attributes = lp
            textView.setTextColor(resources.getColor(R.color.color_common_font))
        }
    }

    private fun initDialogRecycler() {
        //价格
        rv_price_recycler?.layoutManager = GridLayoutManager(this, 4)
        rv_price_recycler?.addItemDecoration(GridSectionAverageGapItemDecoration(10f, 10f, 0f, 10f))
        //星级
        mStarRecycler?.layoutManager = GridLayoutManager(this, 4)
        mStarRecycler?.addItemDecoration(GridSectionAverageGapItemDecoration(10f, 10f, 0f, 10f))

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelSearchCondition()).subscribe(object : BaseObserver<HotelSearchConditionBean>(mActivity) {
            @Throws(IOException::class)
            override fun onHandleSuccess(hotelSearchConditionBean: HotelSearchConditionBean) {
                //价格
                priceAdapter = HotelPriceAdapter(R.layout.item_section_content)
                priceAdapter?.setNewData(hotelSearchConditionBean.price_range)
                rv_price_recycler?.adapter = priceAdapter
                priceAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                    priceAdapter?.onChange(position)
                    priceAdapter?.notifyDataSetChanged()
                }


                //星级
                starAdapter = HotelStarAdapter(R.layout.item_section_content)
                starAdapter?.setNewData(hotelSearchConditionBean.star)
                mStarRecycler?.adapter = starAdapter
                starAdapter?.onItemClickListener = BaseQuickAdapter.OnItemClickListener { adapter, view, position ->
                    starAdapter?.onChange(position)
                    starAdapter?.notifyDataSetChanged()
                }
            }
        })
        mCancle?.setOnClickListener { v ->
            priceAdapter?.cancel(-1)
            priceAdapter?.notifyDataSetChanged()
            starAdapter?.cancel(-1)
            starAdapter?.notifyDataSetChanged()

        }
        mConfirm?.setOnClickListener { v ->
            val item = priceAdapter?.getItem(HotelPriceAdapter.SELECTOR_POSITION)

            pageLoadUtil?.load {
                ApiUtils.getApiService().hotelHomeList(
                        keywords,
                        item?.start,
                        item?.end,
                        starAdapter?.getItem(HotelPriceAdapter.SELECTOR_POSITION)?.name,
                        type,
                        pageLoadUtil?.page)
            }

            mPopWindow?.dismiss()
        }


    }

    private fun initLocation() {
        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_hotel_location, null)
        initPopuWindow(contentView, tv_location)

        datalist1 = ArrayList()
        for (i in 0..4) {
            val databean = CategoryBean()
            databean.content = "一级$i"
            for (j in 0..4) {
                val dataBean2 = CategoryBean()
                dataBean2.content = "二级$i$j"
                for (k in 0..4) {
                    val dataBean3 = CategoryBean()
                    dataBean3.content = "三级$i$j$k"
                    dataBean2.categories.add(dataBean3)
                }
                databean.categories.add(dataBean2)
            }
            datalist1.add(databean)
        }


        initCatagory1(contentView)
        initCatagory2(contentView)
        initCatagory3(contentView)
    }

    private fun initCatagory1(view: View) {
        view.catagory1.layoutManager = LinearLayoutManager(this)
        catagory1Adapter = Catagory1Adapter(R.layout.catagory1_item_view, datalist1)
        catagory1Adapter?.openLoadAnimation()
        view.catagory1.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        view.catagory1.adapter = catagory1Adapter

        catagory1Adapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            catagory1Adapter?.select(position)
            catagory2Adapter?.setNewData(catagory1Adapter?.getItem(position)?.categories)
            catagory2Adapter?.select(0)
            catagory3Adapter?.setNewData(catagory1Adapter?.getItem(position)?.categories?.get(0)?.categories)
            catagory3Adapter?.select(0)
        }

    }

    private fun initCatagory2(view: View) {
        view.catagory2.layoutManager = LinearLayoutManager(this)

        catagory2Adapter = Catagory1Adapter(R.layout.catagory1_item_view, datalist1[0].categories)
        catagory2Adapter?.openLoadAnimation()
        view.catagory2.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        view.catagory2.adapter = catagory2Adapter

        catagory2Adapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position ->
            catagory2Adapter?.select(position)
            catagory3Adapter?.setNewData(catagory2Adapter?.getItem(position)?.categories)
            catagory3Adapter?.select(0)
        }
    }

    private fun initCatagory3(view: View) {
        view.catagory3.layoutManager = LinearLayoutManager(this)


        catagory3Adapter = Catagory1Adapter(R.layout.catagory1_item_view, datalist1[0].categories[0].categories)
        catagory3Adapter?.openLoadAnimation()
        view.catagory3.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        view.catagory3.adapter = catagory3Adapter

        catagory3Adapter?.onItemChildClickListener = BaseQuickAdapter.OnItemChildClickListener { adapter, view, position -> catagory3Adapter?.select(position) }
    }

    override fun getContentView(): Int {
        return R.layout.activity_hotel_filter
    }

    companion object {


        fun start(hotelId: String?) {

            val bundle = Bundle()
            bundle.putString("hotelId", hotelId)
            ActivityUtils.startActivity(bundle, HotelFilterActivity::class.java)
        }
    }
}
