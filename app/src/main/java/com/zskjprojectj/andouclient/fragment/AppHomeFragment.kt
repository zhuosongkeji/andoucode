package com.zskjprojectj.andouclient.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zaaach.citypicker.CityPicker
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.model.ListData
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.AppHomeActivity
import com.zskjprojectj.andouclient.activity.MallHomeActivity
import com.zskjprojectj.andouclient.activity.QrCodeActivity
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity
import com.zskjprojectj.andouclient.activity.hotel.HotelHomeActivity
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity
import com.zskjprojectj.andouclient.activity.mall.MallSearchGoodsActivity
import com.zskjprojectj.andouclient.activity.mall.MallDetailActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantHomeActivity
import com.zskjprojectj.andouclient.adapter.CoverFlowAdapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelHomeAdapter
import com.zskjprojectj.andouclient.adapter.mall.RecommendProductsAdapter
import com.zskjprojectj.andouclient.adapter.merchantsCategoryAdapter
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantAdapter
import com.zskjprojectj.andouclient.entity.IndexHomeBean
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.Restaurant
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ScreenUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.utils.UrlUtil
import com.zskjprojectj.andouclient.view.CustomDialog
import com.zskjprojectj.andouclient.view.OnRedPacketDialogClickListener
import com.zskjprojectj.andouclient.view.RedPacketViewHolder
import kotlinx.android.synthetic.main.dialog_red_packet.view.*
import kotlinx.android.synthetic.main.fragment_app_home.*
import kotlinx.android.synthetic.main.text_view.view.*
import java.util.*

class AppHomeFragment : BaseFragment() {
    private var hotelStar: String? = null
    private val keywords: String? = null
    private var startPrice: String? = null
    private var endPrice: String? = null
    private val type: String? = null
    private var adapter: CoverFlowAdapter? = null
    private val merchantsAdapter = merchantsCategoryAdapter()
    private var recommendProductsAdapter = RecommendProductsAdapter(
            R.layout.fragment_mall_goods_details_view,
            ArrayList<MallHomeDataBean.RecommendGoodsBean>())
    private val hotelAdapter = HotelHomeAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initActionBar()
        initCoverFlow()
        initXBanner()
        tempBtn.setOnClickListener {
            ToastUtils.showShort("该功能正在开发中...")
        }
        temp2Btn.setOnClickListener {
            ToastUtils.showShort("该功能正在开发中...")
        }
        temp3Btn.setOnClickListener {
            ToastUtils.showShort("该功能正在开发中...")
        }
        merchantsAdapter.bindToRecyclerView(rv_merchants)
        merchantsAdapter.setOnItemClickListener { _, _, position ->
            when (merchantsAdapter.getItem(position)!!.merchant_type_id) {
                "2" -> MallDetailActivity.start(merchantsAdapter.getItem(position)?.id)
                "3" -> HotelDetailActivity.start(merchantsAdapter.getItem(position)?.id)
                "4" -> RestaurantDetailActivity.start(merchantsAdapter.getItem(position)?.id)
            }
        }
        onlinebooking_see_more_layout.setOnClickListener { ActivityUtils.startActivity(RestaurantHomeActivity::class.java) }
        loadData()
        RequestUtil.request<ListData<Restaurant>>(mActivity, true, false,
                { ApiUtils.getApiService().getRestaurants() },
                { result ->
                    val adapter = RestaurantAdapter()
                    adapter.bindToRecyclerView(view.findViewById(R.id.restaurantRecyclerView))
                    adapter.setNewData(result.data)
                    adapter.setOnItemClickListener { _, _, position -> RestaurantDetailActivity.start(adapter.getItem(position)!!.id) }
                })
        val hotelRecyclerView = view.findViewById<RecyclerView>(R.id.hotelRecyclerView)
        hotelRecyclerView.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        hotelAdapter.bindToRecyclerView(hotelRecyclerView)
        hotelAdapter.setOnItemClickListener { _, _, position -> HotelDetailActivity.start(hotelAdapter.getItem(position)!!.id) }
        recommendProductsAdapter.bindToRecyclerView(goodsRecyclerView)
        recommendProductsAdapter.setOnItemClickListener { _, _, position ->
            MallGoodsDetailsActivity.start(recommendProductsAdapter.getItem(position)!!.id)
        }
        ll_see_more.setOnClickListener {
            (activity as AppHomeActivity).mNavigationBar.selectTab(1)
        }
        more_hotel_btn.setOnClickListener {
            ActivityUtils.startActivity(HotelHomeActivity::class.java)
        }
        moreGoodsBtn.setOnClickListener {
            ActivityUtils.startActivity(MallHomeActivity::class.java)
        }
    }

    private fun initActionBar() {
        ly_citychoose.setOnClickListener {
            CityPicker.from(mActivity)
                    .enableAnimation(true)
                    .show()
        }
        actionBarContainer.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        sha.setOnClickListener { ActivityUtils.startActivity(QrCodeActivity::class.java) }
        mSearchEditText.setOnClickListener {
            MallSearchGoodsActivity.start()
        }
    }

    private fun initCoverFlow() {
        adapter = CoverFlowAdapter(activity) {
            coverflow!!.smoothScrollToPosition(it)
            when (it % (adapter?.merchant_type?.size ?: 1)) {
                0 -> startActivity(Intent(context, MallHomeActivity::class.java))
                1 -> startActivity(Intent(context, HotelHomeActivity::class.java))
                5 -> ActivityUtils.startActivity(RestaurantHomeActivity::class.java)
            }
        }
        coverflow.adapter = adapter
        coverflow.setGreyItem(true)
        coverflow.setAlphaItem(true)
    }

    private fun initXBanner() {
        bannertop.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ScreenUtil.getScreenWidth(mActivity) / 2)
        bannertop.loadImage { _, model, view, _ ->
            val model1 = model as IndexHomeBean.BannerBean
            Glide.with(mActivity)
                    .load(UrlUtil.getImageUrl(model1.img))
                    .apply(RequestOptions().placeholder(R.mipmap.ic_placeholder))
                    .into(view as ImageView)
        }
        bannertop.setOnItemClickListener { banner, model, view, position ->
            ToastUtils.showShort("功能正在开发中...")
        }
        bannertop.setAutoPlayAble(true)
        bannertop.setIsClipChildrenMode(true)
    }

    @SuppressLint("SetTextI18n", "InflateParams")
    private fun loadData() {
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().index() },
                { result ->
                    bannertop.setBannerData(result.data.banner)
                    val merchantTypes = result.data.merchant_type
                    adapter?.setNewData(merchantTypes)
                    coverflow.scrollToPosition(merchantTypes.size * 1000)
                    merchantsAdapter.setNewData(result.data.merchants)
                    result.data.notice.forEach {
                        val view = layoutInflater.inflate(R.layout.text_view, null)
                        view.tv_index_notice.text = it.content
                        view_flipper.addView(view)
                    }
                    view_flipper.setFlipInterval(2000)
                    view_flipper.startFlipping()
                })
        if (LoginInfoUtil.isLogin()) {
            RequestUtil.request(mActivity, true, true,
                    { ApiUtils.getApiService().new_user(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                    { result ->
                        if (result.data.getVal() == 1) {
                            RequestUtil.request(mActivity, true, true,
                                    { ApiUtils.getApiService().envelopes(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                                    { result1 ->
                                        val mRedPacketDialogView = View.inflate(mActivity, R.layout.dialog_red_packet, null)
                                        mRedPacketDialogView.tv_hbmoney.text = "最高可获得${result1.data.value}元"
                                        val mRedPacketViewHolder = RedPacketViewHolder(mActivity, mRedPacketDialogView)
                                        val mRedPacketDialog = CustomDialog(mActivity, mRedPacketDialogView, R.style.custom_dialog)
                                        mRedPacketDialog.setCancelable(false)
                                        mRedPacketViewHolder.setOnRedPacketDialogClickListener(object : OnRedPacketDialogClickListener {
                                            override fun onCloseClick() {
                                                mRedPacketDialog.dismiss()
                                            }

                                            override fun onOpenClick() {
                                                RequestUtil.request(mActivity, true, true,
                                                        { ApiUtils.getApiService().envelopes_add(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                                                        {
                                                            ToastUtil.showToast("领取成功")
                                                            mRedPacketDialog.dismiss()
                                                        })
                                            }
                                        })
                                        mRedPacketDialog.show()
                                    })
                        }
                    })
        }
        RequestUtil.request(mActivity, true, true,
                {
                    ApiUtils.getApiService().hotelHomeList(
                            null,
                            null,
                            null,
                            keywords,
                            startPrice,
                            endPrice,
                            hotelStar,
                            ""
                    )
                },
                { result ->
                    hotelAdapter.setNewData(
                            result.data.subList(0, if (result.data.size >= 3) 3 else result.data.size))
                })

        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().mallInfo },
                { result ->
                    recommendProductsAdapter.setNewData(result.data.recommend_goods)
                })
    }

    override fun getContentView() = R.layout.fragment_app_home
}
