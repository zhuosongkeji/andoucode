package com.zskjprojectj.andouclient.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.core.view.marginTop
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.stx.xhb.xbanner.XBanner
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.HotCity
import com.zaaach.citypicker.model.LocateState
import com.zaaach.citypicker.model.LocatedCity
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.model.ListData
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.AppHomeActivity
import com.zskjprojectj.andouclient.activity.BookingorderActivity
import com.zskjprojectj.andouclient.activity.LoginActivity
import com.zskjprojectj.andouclient.activity.MallMainActivity
import com.zskjprojectj.andouclient.activity.QrCodeActivity
import com.zskjprojectj.andouclient.activity.hotel.HotelActivity
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity
import com.zskjprojectj.andouclient.activity.mall.MallShoppingHomeActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantDetailActivity
import com.zskjprojectj.andouclient.activity.restaurant.RestaurantHomeActivity
import com.zskjprojectj.andouclient.adapter.CoverFlowAdapter
import com.zskjprojectj.andouclient.adapter.hotel.HotelHomeAdapter
import com.zskjprojectj.andouclient.adapter.mall.RecommendProductsAdapter
import com.zskjprojectj.andouclient.adapter.merchantsCategoryAdapter
import com.zskjprojectj.andouclient.adapter.restaurant.RestaurantAdapter
import com.zskjprojectj.andouclient.entity.EnvelopesBean
import com.zskjprojectj.andouclient.entity.IndexHomeBean
import com.zskjprojectj.andouclient.entity.NewuserBean
import com.zskjprojectj.andouclient.entity.hotel.HotelHomeBean
import com.zskjprojectj.andouclient.entity.mall.MallHomeDataBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.http.BaseObserver
import com.zskjprojectj.andouclient.http.HttpRxObservable
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ScreenUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.utils.UrlUtil
import com.zskjprojectj.andouclient.view.CustomDialog
import com.zskjprojectj.andouclient.view.OnRedPacketDialogClickListener
import com.zskjprojectj.andouclient.view.RedPacketViewHolder

import java.io.IOException
import java.util.ArrayList

import butterknife.BindView
import butterknife.OnClick
import com.blankj.utilcode.util.BarUtils
import com.zskjprojectj.andouclient.model.Restaurant
import kotlinx.android.synthetic.main.fragment_app_home.*
import kotlinx.android.synthetic.main.main_online_broadcast.view.*

class AppHomeFragment : BaseFragment(), CoverFlowAdapter.onItemClick {

    private val enable: Boolean = false
    private val hotCities: List<HotCity>? = null
    private val anim: Int = 0
    private val theme: Int = 0
    private var tv_cityinfo: TextView? = null
    private var onlinebroadcast_see_more_layout: LinearLayout? = null
    private var appointment_see_more_layout: LinearLayout? = null
    private var onlinebooking_see_more_layout: LinearLayout? = null
    private var ly_citychoose: LinearLayout? = null
    private var adapter: CoverFlowAdapter? = null
    private val merchantsAdapter = merchantsCategoryAdapter()
    internal var recommendProductsAdapter = RecommendProductsAdapter(R.layout.fragment_mall_goods_details_view, ArrayList<MallHomeDataBean.RecommendGoodsBean>())

    //红包
    private var mRedPacketDialog: CustomDialog? = null
    private var mRedPacketDialogView: View? = null
    private var mRedPacketViewHolder: RedPacketViewHolder? = null
    private val hoteladapter = HotelHomeAdapter()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initActionBar()
        initCoverFlow()
        initXBanner()
        onlinebroadcast_see_more_layout = view.findViewById(R.id.onlinebroadcast_see_more_layout)
        appointment_see_more_layout = view.findViewById(R.id.appointment_see_more_layout)
        onlinebooking_see_more_layout = view.findViewById(R.id.onlinebooking_see_more_layout)
        ly_citychoose = view.findViewById(R.id.ly_citychoose)
        tv_cityinfo = view.findViewById(R.id.tv_cityinfo)

        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        rv_merchants!!.layoutManager = layoutManager
        rv_merchants!!.adapter = merchantsAdapter
        merchantsAdapter.setOnItemClickListener { adapter, view, position ->
            when (merchantsAdapter.getItem(position)!!.merchant_type_id) {
                //商家商城
                "2" -> MallShoppingHomeActivity.start(merchantsAdapter.getItem(position)!!.id)
                //酒店商家
                "3" -> HotelDetailActivity.start(merchantsAdapter.getItem(position)!!.id)
                //饭店商家
                "4" -> RestaurantDetailActivity.start(merchantsAdapter.getItem(position)!!.id)
                //农家乐
                "5" -> {
                }
                //旅游
                "6" -> {
                }
                //美食预订
                "7" -> {
                }
                //农家乐民宿
                "8" -> {
                }
            }
        }

        initData()
        getDataFromServer()
        RequestUtil.request<ListData<Restaurant>>(mActivity, true, false,
                { ApiUtils.getApiService().getRestaurants(null, null, null, 1) },
                { result ->
                    val adapter = RestaurantAdapter()
                    adapter.bindToRecyclerView(view.findViewById(R.id.restaurantRecyclerView))
                    adapter.setNewData(result.data)
                    adapter.setOnItemClickListener { adapter1, view, position -> RestaurantDetailActivity.start(adapter.getItem(position)!!.id) }
                })
        val hotelRecyclerView = view.findViewById<RecyclerView>(R.id.hotelRecyclerView)
        hotelRecyclerView.addItemDecoration(DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL))
        hoteladapter.bindToRecyclerView(hotelRecyclerView)
        hoteladapter.setOnItemClickListener { adapter, view, position -> HotelDetailActivity.start(hoteladapter.getItem(position)!!.id) }
        recommendProductsAdapter.bindToRecyclerView(view.findViewById(R.id.goodsRecyclerView))
        recommendProductsAdapter.setOnItemClickListener { adapter, view, position -> MallGoodsDetailsActivity.start(recommendProductsAdapter.getItem(position)!!.id) }
    }

    private fun initActionBar() {
        actionBarContainer.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        sha.setOnClickListener { ActivityUtils.startActivity(QrCodeActivity::class.java) }
    }

    private fun showRedPacketDialog() {
        if (mRedPacketDialogView == null) {
            mRedPacketDialogView = View.inflate(mActivity, R.layout.dialog_red_packet, null)
            val tv_money = mRedPacketDialogView!!.findViewById<TextView>(R.id.tv_hbmoney)
            HttpRxObservable.getObservable(ApiUtils.getApiService().envelopes(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(object : BaseObserver<EnvelopesBean>(mActivity) {
                @Throws(IOException::class)
                override fun onHandleSuccess(envelopesBean: EnvelopesBean) {
                    tv_money.text = "最高可获得" + envelopesBean.value + "元"
                }
            })
            mRedPacketViewHolder = RedPacketViewHolder(mActivity, mRedPacketDialogView)
            mRedPacketDialog = CustomDialog(mActivity, mRedPacketDialogView, R.style.custom_dialog)
            mRedPacketDialog!!.setCancelable(false)
        }
        mRedPacketViewHolder!!.setOnRedPacketDialogClickListener(object : OnRedPacketDialogClickListener {
            override fun onCloseClick() {
                mRedPacketDialog!!.dismiss()
            }

            override fun onOpenClick() {
                if (TextUtils.isEmpty(LoginInfoUtil.getToken())) {
                    LoginActivity.start(mActivity)
                } else {
                    //领取红包,调用接口
                    HttpRxObservable.getObservable(ApiUtils.getApiService().envelopes_add(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(object : BaseObserver<Any>(mActivity) {
                        @Throws(IOException::class)
                        override fun onHandleSuccess(o: Any) {
                            ToastUtil.showToast("领取成功")
                            mRedPacketDialog!!.dismiss()
                        }
                    })
                }

            }
        })
        mRedPacketDialog!!.show()
    }

    private fun initCoverFlow() {
        adapter = CoverFlowAdapter(activity, this)
        coverflow!!.setGreyItem(true) //设置灰度渐变
        coverflow!!.setAlphaItem(true) //设置半透渐变
    }


    private fun getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().index()).subscribe(object : BaseObserver<IndexHomeBean>(mActivity) {
            @Throws(IOException::class)
            override fun onHandleSuccess(indexHomeBean: IndexHomeBean) {
                bannertop.setBannerData(indexHomeBean.banner)
                val merchant_type = indexHomeBean.merchant_type
                adapter!!.setNewData(merchant_type)
                coverflow!!.adapter = adapter
                coverflow!!.scrollToPosition(merchant_type.size * 1000)
                val merchants = indexHomeBean.merchants
                merchantsAdapter.setNewData(merchants)


                val notice = indexHomeBean.notice
                for (i in notice.indices) {
                    val view = layoutInflater.inflate(R.layout.text_view, null)
                    val content = view.findViewById<TextView>(R.id.tv_index_notice)
                    content.text = notice[i].content
                    view_flipper!!.addView(view)
                }
                view_flipper!!.setFlipInterval(2000)
                view_flipper!!.startFlipping()

            }
        })
        if (!TextUtils.isEmpty(LoginInfoUtil.getToken())) {
            HttpRxObservable.getObservable(ApiUtils.getApiService().new_user(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(object : BaseObserver<NewuserBean>(mActivity) {
                @Throws(IOException::class)
                override fun onHandleSuccess(newuserBean: NewuserBean) {
                    if (newuserBean.getVal() == 1) {
                        showRedPacketDialog()
                    }

                }
            })
        }

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelHomeList("", "", "", "", "", 1))
                .subscribe(object : BaseObserver<ListData<HotelHomeBean>>(mActivity) {
                    @Throws(IOException::class)
                    override fun onHandleSuccess(hotelHomeBeans: ListData<HotelHomeBean>) {
                        for (i in hotelHomeBeans.indices) {
                            if (i > 2)
                                return
                            hoteladapter.addData(hotelHomeBeans[i])
                        }
                    }
                })
        HttpRxObservable.getObservable(ApiUtils.getApiService().mallInfo)
                .subscribe(object : BaseObserver<MallHomeDataBean>(mActivity) {

                    override fun onHandleSuccess(bean: MallHomeDataBean) {
                        val recommend_goods = bean.recommend_goods
                        recommendProductsAdapter.setNewData(recommend_goods)
                    }
                })

    }

    private fun initData() {


        /**
         * 在线直播
         */
        onlinebroadcast_see_more_layout!!.setOnClickListener { ToastUtil.showToast("功能持续完成中......") }
        /**
         * 外卖
         */
        appointment_see_more_layout!!.setOnClickListener { startActivity(Intent(context, BookingorderActivity::class.java)) }
        /**
         * 在线预约订单(饭店预订)
         */
        onlinebooking_see_more_layout!!.setOnClickListener { ActivityUtils.startActivity(RestaurantHomeActivity::class.java) }
        /**
         * 城市选择
         */
        ly_citychoose!!.setOnClickListener {
            CityPicker.from(context)
                    .enableAnimation(enable)
                    .setAnimationStyle(anim)
                    .setLocatedCity(null)
                    .setHotCities(hotCities)
                    .setOnPickListener(object : OnPickListener {
                        override fun onPick(position: Int, data: City) {
                            tv_cityinfo!!.text = String.format("%s", data.name)
                            //  tv_cityinfo.setText(String.format("当前城市：%s，%s", data.getName(), data.code()));
                            //                                Toast.makeText(
                            //                                        getApplicationContext(),
                            //                                        String.format("点击的数据：%s，%s", data.getName(), data.code()),
                            //                                        Toast.LENGTH_SHORT)
                            //                                        .show();
                        }

                        override fun onCancel() {
                            // Toast.makeText(getApplicationContext(), "取消选择", Toast.LENGTH_SHORT).show();
                        }

                        override fun onLocate() {
                            //开始定位，这里模拟一下定位
                            Handler().postDelayed({ CityPicker.from(context).locateComplete(LocatedCity("重庆", "广东", "101280601"), LocateState.SUCCESS) }, 3000)
                        }
                    })
                    .show()
        }
    }

    private fun initXBanner() {
        bannertop.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                ScreenUtil.getScreenWidth(mActivity) / 2)
        bannertop.loadImage { _, model, view, _ ->
            val model1 = model as IndexHomeBean.BannerBean
            Glide.with(mActivity).load(UrlUtil.getImageUrl(model1.img)).apply(RequestOptions()
                    .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into(view as ImageView)
        }
        bannertop.setAutoPlayAble(true)
        bannertop.setIsClipChildrenMode(true)
    }


    override fun clickItem(pos: Int) {
        coverflow!!.smoothScrollToPosition(pos)
        val index = pos % adapter!!.merchant_type.size
        when (index) {
            /**
             * 商城
             */
            0 -> startActivity(Intent(context, MallMainActivity::class.java))
            /**
             * 酒店
             */
            1 -> startActivity(Intent(context, HotelActivity::class.java))
            2 -> {
            }
            3 -> {
            }
            4 -> {
            }
            5 -> {
            }
            6 -> {
            }
        }
    }

    @OnClick(R.id.ll_see_more)
    fun clickSeeMore() {
        if (activity is AppHomeActivity) {
            (activity as AppHomeActivity).mNavigationBar.selectTab(1)
        }
    }

    @OnClick(R.id.more_hotel_btn)
    internal fun onMoreHotelBtnClick() {
        ActivityUtils.startActivity(HotelActivity::class.java)
    }

    @OnClick(R.id.moreGoodsBtn)
    internal fun onMoreGoodsBtnClick() {
        ActivityUtils.startActivity(MallMainActivity::class.java)
    }

    override fun getContentView() = R.layout.fragment_app_home
}
