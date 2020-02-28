package com.zskjprojectj.andouclient.activity.mall

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.TimeUtils
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.MallHomeActivity
import com.zskjprojectj.andouclient.activity.MyAddressActivity
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.adapter.mall.MallBuyAdapter
import com.zskjprojectj.andouclient.adapter.mall.MallPinTuanAdapter
import com.zskjprojectj.andouclient.entity.XBannerBean
import com.zskjprojectj.andouclient.entity.mall.MallBuyBean
import com.zskjprojectj.andouclient.entity.mall.MallGoodsDetailsDataBean
import com.zskjprojectj.andouclient.fragment.hotel.CustomViewDialog
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsCommentFragment
import com.zskjprojectj.andouclient.fragment.mall.MallGoodsDetailFragment
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.GetGroupOrderResponse
import com.zskjprojectj.andouclient.model.UserIn.Role.KEY_TYPE
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.utils.UrlUtil
import kotlinx.android.synthetic.main.activity_mall_goods_details.*
import java.text.SimpleDateFormat
import java.util.*

class MallGoodsDetailsActivity : BaseActivity() {
    var goodsDetail: MallGoodsDetailsDataBean? = null

    private var bottomDialog: Dialog? = null
    var goodsId: String? = null
    private var isCollection = false
    val res: ArrayList<MallBuyBean.SpecInfo> = arrayListOf()
    private val pinTuanAdapter = MallPinTuanAdapter()
    //订单标题
    private var orderName: String? = null
    private val timer = Timer()
    private var offset: Long = 0
    var type: String? = ""
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "商品详情")
        goodsId = intent.getStringExtra(GOODS_ID)
        type = intent.getStringExtra(KEY_TYPE)
        val controlId = intent.getStringExtra(KEY_DATA)
        when (type) {
            TYPE_MIAO_SHA -> {
                miaoShaContainer.visibility = View.VISIBLE
                mMallGoodsName.visibility = View.GONE
                mGoodsPrice.visibility = View.GONE
                tv_mall_goods_name2.visibility = View.VISIBLE
                add_shopping.visibility = View.GONE
                mBuyNow.setOnClickListener {
                    RequestUtil.request(mActivity, true, false,
                            {
                                ApiUtils.getApiService().miaosha(
                                        LoginInfoUtil.getUid(),
                                        LoginInfoUtil.getToken(),
                                        controlId
                                )
                            },
                            {
                                MallOnlineOrderActivity.start(it.data.order_sn)
                            },
                            {
                                if (it.contains("请填写收货地址")) {
                                    startActivity(Intent(mActivity, MyAddressActivity::class.java))
                                }
                            })
                }
                RequestUtil.request(mActivity, true, true,
                        { ApiUtils.getApiService().miaoShaDetails(controlId) },
                        {
                            miaoshaprice.text = it.data.kill_price
                            price.text = it.data.old_price
                            price.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
                            timer.schedule(object : TimerTask() {
                                override fun run() {
                                    timerContainer.postDelayed({ countTime(it.data.end_time) }, 1)
                                }
                            }, 0, 1000)
                        })
            }
            TYPE_PIN_TUAN -> {
                ll_pintuan_person.visibility = View.VISIBLE
                ll_pintuan_list.visibility = View.VISIBLE
                miaoShaContainer.visibility = View.GONE
                ll_normal.visibility = View.GONE
                ll_pintuan.visibility = View.VISIBLE
                pinTuanAdapter.bindToRecyclerView(tv_pintuan)
                pinTuanAdapter.setOnItemChildClickListener { _, view, position ->
                    val team = pinTuanAdapter.getItem(position)
                    RequestUtil.request<GetGroupOrderResponse>(mActivity, true, false,
                            {
                                ApiUtils.getApiService().getGroupOrder(
                                        LoginInfoUtil.getUid(),
                                        LoginInfoUtil.getToken(),
                                        1,
                                        controlId,
                                        2,
                                        team?.group_id
                                )
                            },
                            { result ->
                                MallOnlineOrderActivity.start(result.data.order_sn)
                            })
                }
                RequestUtil.request(mActivity, true, true,
                        { ApiUtils.getApiService().tuangouDetails(controlId) },
                        {
                            if ("0" == it.data.group_goods.code) {
                                mTvPrice.text = it.data.group_goods.price
                                tv_pintuan_number.text = it.data.group_goods.top_member + "人拼"
                                mTvGoodsVolume.text = it.data.group_goods.sale_total
                                mTvGoodsStoreNum.text = it.data.group_goods.storage
                                tv_total_member.text = "已有" + it.data.total_member + "人参团"
                                pinTuanAdapter.setNewData(it.data.team_list)
                                pinTuanAdapter.setEndTime(it.data.group_goods.finish_time)
                                pintuan_tv_buy_now.text="¥${it.data.group_goods.price}\n我要开团"
                            }
                        })
                pintuan_add_shopping.setOnClickListener {
                    goToBuy()
                }
                pintuan_tv_buy_now.setOnClickListener {
                    RequestUtil.request<GetGroupOrderResponse>(mActivity, true, false,
                            {
                                ApiUtils.getApiService().getGroupOrder(
                                        LoginInfoUtil.getUid(),
                                        LoginInfoUtil.getToken(),
                                        1,
                                        controlId,
                                        1,
                                        null
                                )
                            },
                            { result ->
                                MallOnlineOrderActivity.start(result.data.order_sn)
                            })
                }
            }
            else -> mBuyNow.setOnClickListener {
                goToBuy()
            }
        }
        banner.loadImage { _, model, view, _ ->
            //                加载本地图片展示
            (view as ImageView).scaleType = ImageView.ScaleType.CENTER_CROP
            val urlList = model as XBannerBean
            val url = UrlUtil.getImageUrl(urlList.imageUrl)
            Glide.with(this@MallGoodsDetailsActivity).load(url).apply(RequestOptions()
                    .placeholder(R.drawable.default_image).error(R.drawable.default_image)).into(view)
        }
        val mallGoodsDetailFragment = MallGoodsDetailFragment()
        mallGoodsDetailFragment.arguments = bundleOf(Pair("id", goodsId))
        val mallGoodsCommentFragment = MallGoodsCommentFragment()
        mallGoodsCommentFragment.arguments = bundleOf(Pair("id", goodsId))
        tabLayout.setViewPager(viewPager,
                arrayOf("产品详情", "产品评价"),
                mActivity,
                arrayListOf(mallGoodsDetailFragment, mallGoodsCommentFragment))
        mall_goods_collection.setOnClickListener {
            val collected = if (!isCollection) "1" else "0"
            RequestUtil.request(mActivity, true, false,
                    {
                        ApiUtils.getApiService().mallGoodsCollection(
                                goodsId,
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                collected
                        )
                    },
                    {
                        if (collected == "1") {
                            mtvMallGoodsCollection.text = "已收藏"
                            iviscollection.setImageResource(R.mipmap.ic_heart_mall)
                            isCollection = true
                        } else {
                            mtvMallGoodsCollection.text = "收藏"
                            iviscollection.setImageResource(R.mipmap.uncollectionicon)
                            isCollection = false
                        }
                    })
        }
        tv_mall_shopping.setOnClickListener {
            MallHomeActivity.start(2)
        }
        tv_Mall_service.setOnClickListener {
            ToastUtils.showShort("暂无客服")
        }
        add_shopping.setOnClickListener {
            goToBuy()
        }
        tv_mall_home.setOnClickListener {
            MallShoppingHomeActivity.start(goodsDetail?.merchant_id)
        }
        mShopHome.setOnClickListener {
            MallShoppingHomeActivity.start(goodsDetail?.merchant_id)
        }
        mDiscount.setOnClickListener {
            initDiscount()
        }
        shared.setOnClickListener {
            val dialog = CustomViewDialog(this, R.layout.activity_shared_dialog_view,
                    intArrayOf(R.id.cancle, R.id.weixin, R.id.friendcircle, R.id.qq, R.id.qqkongjian, R.id.weibo))
            dialog.setOnCenterItemClickListener { dialog, view ->
                when (view.id) {
                    R.id.cancle -> dialog.dismiss()
                    R.id.weixin -> ToastUtil.showToast("微信")
                    R.id.friendcircle -> ToastUtil.showToast("朋友圈")
                    R.id.qq -> ToastUtil.showToast("QQ")
                    R.id.qqkongjian -> ToastUtil.showToast("QQ空间")
                    R.id.weibo -> ToastUtil.showToast("微博")
                }
            }
            dialog.show()
        }
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().mallDetailsShow(goodsId, LoginInfoUtil.getUid()) },
                {
                    goodsDetail = it.data
                    val bannerData = arrayListOf(XBannerBean(goodsDetail?.img))
                    goodsDetail?.album?.forEach { imgUrl ->
                        bannerData.add(XBannerBean(imgUrl))
                    }
                    banner.setBannerData(bannerData)
                    mMallGoodsName.text = goodsDetail?.name
                    if(type!= TYPE_PIN_TUAN){
                        mTvPrice.text = goodsDetail?.price
                    }
                    pintuan_add_shopping.text="¥${goodsDetail?.price}\n单独购买"
                    tv_goods_dilivery.text = goodsDetail?.dilivery
                    mTvGoodsVolume.text = goodsDetail?.volume
                    mTvGoodsStoreNum.text = goodsDetail?.store_num
                    Glide.with(mActivity)
                            .load(UrlUtil.getImageUrl(goodsDetail?.merchant?.logo_img))
                            .apply(RequestOptions()
                                    .placeholder(R.mipmap.ic_default_head_photo)
                                    .error(R.mipmap.ic_default_head_photo))
                            .into(IvHeadPic)
                    mTvName.text = goodsDetail?.merchant?.name
                    if ("0" == goodsDetail?.is_collection) {
                        mtvMallGoodsCollection.text = "收藏"
                        isCollection = false
                        iviscollection.setImageResource(R.mipmap.uncollectionicon)
                    } else {
                        mtvMallGoodsCollection.text = "已收藏"
                        isCollection = true
                        iviscollection.setImageResource(R.mipmap.ic_heart_mall)
                    }
                })
    }

    @SuppressLint("SetTextI18n")
    private fun countTime(time: String) {
        val date = TimeUtils.string2Date(time, SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()))
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, date.hours)
        calendar.set(Calendar.MINUTE, date.minutes)
        calendar.set(Calendar.SECOND, 0)
        val endTime = calendar.time.time
        val currentTime = System.currentTimeMillis()
        offset = (endTime - currentTime) / 1000
        if (offset > 0) {
            secondTxt.text = (offset % 60).toString()
            minuteTxt.text = (offset / 60 % 60).toString()
            hourTxt.text = (offset / 60 / 60 % 24).toString()
            dayTxt.text = "${offset / 60 / 60 / 24}天"
            offset -= 1
        }
    }

    private fun goToBuy() {
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().buySpecification(goodsId) },
                {
                    res.addAll(it.data.res.map { spec ->
                        val info = MallBuyBean.SpecInfo()
                        info.name = spec.name
                        return@map info
                    })
                    initBuyNow(it.data.res, it.data.price)
                })
    }

    private fun initDiscount() {
//
//        val bottomDialog = Dialog(this, R.style.BottomDialog)
//
//        val window = bottomDialog.window
//        // 把 DecorView 的默认 padding 取消，同时 DecorView 的默认大小也会取消
//        window.decorView.setPadding(0, 0, 0, 0)
//        val layoutParams = window.attributes
//        // 设置宽度
//        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
//        window.attributes = layoutParams
//        // 给 DecorView 设置背景颜色，很重要，不然导致 Dialog 内容显示不全，有一部分内容会充当 padding，上面例子有举出
//        window.decorView.setBackgroundColor(Color.TRANSPARENT)
//
//
//        val contentView = LayoutInflater.from(this).inflate(R.layout.dialog_mall_goods_discount, null)
//        bottomDialog.setContentView(contentView)
//        //        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) baseContentView.getLayoutParams();
//        //        params.width = getResources().getDisplayMetrics().widthPixels - DensityUtil.dp2px(this, 16f);
//        //        params.bottomMargin = DensityUtil.dp2px(this, 8f);
//        //        baseContentView.setLayoutParams(params);
//        val mCancle = contentView.findViewById<ImageView>(R.id.iv_cancle)
//        mCancle.setOnClickListener { bottomDialog.dismiss() }
//        bottomDialog.setCanceledOnTouchOutside(true)
//        bottomDialog.window.setGravity(Gravity.BOTTOM)
//        bottomDialog.window.setWindowAnimations(R.style.BottomDialog_Animation)
//        bottomDialog.show()

    }

    @SuppressLint("SetTextI18n")
    private fun initBuyNow(specInfos: ArrayList<MallBuyBean.SpecInfo>, price: HashMap<String, MallBuyBean.PriceInfo>) {
        bottomDialog = Dialog(this, R.style.BottomDialog)
        bottomDialog?.window?.decorView?.setPadding(0, 0, 0, 0)
        bottomDialog?.window?.attributes?.width = WindowManager.LayoutParams.MATCH_PARENT
        bottomDialog?.window?.decorView?.setBackgroundColor(Color.TRANSPARENT)
        val dialogContentView = LayoutInflater.from(this).inflate(R.layout.dialog_buy_now, null)
        Glide.with(mActivity).load(UrlUtil.getImageUrl(goodsDetail?.img))
                .apply(RequestOptions().placeholder(R.drawable.default_image).error(R.drawable.default_image))
                .into(dialogContentView.findViewById(R.id.orderImg))
        val mOrderName = dialogContentView.findViewById<TextView>(R.id.tv_order_name)
        mOrderName.text = orderName
        val mGoodsNum = dialogContentView.findViewById<TextView>(R.id.tv_goods_num_a)
        val mGoodsPrice = dialogContentView.findViewById<TextView>(R.id.tv_goods_price_a)
        val mBuyRecycler = dialogContentView.findViewById<RecyclerView>(R.id.rv_buy_recyclerview)
        mBuyRecycler.layoutManager = LinearLayoutManager(mBuyRecycler.context)
        val buyAdapter = MallBuyAdapter(R.layout.mall_buy_item)
        buyAdapter.setNewData(specInfos)
        mBuyRecycler.adapter = buyAdapter
        buyAdapter.setItemClickKind(MallBuyAdapter.ItemClickKind { spec, kind ->
            for (re in res) {
                if (re.name == spec) {
                    re.value.clear()
                    if (kind != null) {
                        re.value.add(kind)
                    }
                }
            }
            val buffer = StringBuffer()
            for (i in res.indices) {
                val info = res[i]
                if (info.value.size <= 0) {
                    return@ItemClickKind
                } else {
                    buffer.append(info.value[0]).append("-")
                }
            }
            val priceInfo = price[buffer.substring(0, buffer.length - 1)]
            mGoodsNum.text = priceInfo?.num.toString()
            mGoodsPrice.text = "¥${priceInfo?.price}"
        })
        val mSub = dialogContentView.findViewById<ImageView>(R.id.btn_sub)
        val mAdd = dialogContentView.findViewById<ImageView>(R.id.btn_add)
        val mNum = dialogContentView.findViewById<TextView>(R.id.tv_num)
        val mTvAddShopping = dialogContentView.findViewById<TextView>(R.id.tv_add_shopping)
        if (type == TYPE_MIAO_SHA || type == TYPE_PIN_TUAN) {
            mTvAddShopping.visibility = View.GONE
        }
        mTvAddShopping.setOnClickListener {
            val buffer = StringBuffer()
            for (i in res.indices) {
                val info = res[i]
                if (info.value.size <= 0) {
                    ToastUtil.showToast("请选择" + info.name)
                    return@setOnClickListener
                } else {
                    buffer.append(info.value[0]).append("-")
                }
            }
            RequestUtil.request(mActivity, true, false,
                    {
                        ApiUtils.getApiService().addCar(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                goodsId,
                                goodsDetail?.merchant_id,
                                price[buffer.substring(0, buffer.length - 1)]?.id
                        )
                    },
                    {
                        ToastUtil.showToast("加入购物车成功")
                        bottomDialog?.dismiss()
                    })
        }
        val mBuyNow = dialogContentView.findViewById<TextView>(R.id.tv_buynow)
        mBuyNow.setOnClickListener(View.OnClickListener {
            val num = mNum.text.toString()
            val buffer = StringBuffer()
            for (i in res.indices) {
                val info = res[i]
                if (info.value.size <= 0) {
                    ToastUtil.showToast("请选择" + info.name)
                    return@OnClickListener
                } else {
                    buffer.append(info.value[0]).append("-")
                }
            }
            RequestUtil.request(mActivity, true, false,
                    {
                        ApiUtils.getApiService().MallBuyNow(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                goodsId,
                                goodsDetail?.merchant_id,
                                price[buffer.substring(0, buffer.length - 1)]?.id,
                                num
                        )
                    },
                    {
                        MallOnlineOrderActivity.start(it.data.order_sn)
                        bottomDialog?.dismiss()
                    },
                    {
                        if (it.contains("请填写收货地址")) {
                            startActivity(Intent(mActivity, MyAddressActivity::class.java))
                        }
                    })
        })
        val mCancle = dialogContentView.findViewById<ImageView>(R.id.iv_cancle)
        mCancle.setOnClickListener { bottomDialog?.dismiss() }
        mAdd.setOnClickListener {
            val number = mNum.text.toString()
            var i = Integer.parseInt(number)
            i += 1
            val addNumber = Integer.toString(i)
            mNum.text = addNumber
        }

        mSub.setOnClickListener {
            val number = mNum.text.toString()
            var i = Integer.parseInt(number)
            if (i > 1) {
                i -= 1
                val addNumber = Integer.toString(i)
                mNum.text = addNumber
            }
        }
        bottomDialog?.setContentView(dialogContentView)
        bottomDialog?.window?.setGravity(Gravity.BOTTOM)
        bottomDialog?.setCanceledOnTouchOutside(true)
        bottomDialog?.window?.setWindowAnimations(R.style.BottomDialog_Animation)
        bottomDialog?.show()
    }


    companion object {
        const val GOODS_ID = "goodsId"
        const val TYPE_MIAO_SHA = "TYPE_MIAO_SHA"
        const val TYPE_PIN_TUAN = "TYPE_PIN_TUAN"
        const val TYPE_NORMAL = "TYPE_NORMAL"

        fun start(goodId: String?, type: String? = TYPE_NORMAL, controlId: String? = null) {
            val intent = Bundle()
            intent.putString(GOODS_ID, goodId)
            intent.putString(KEY_TYPE, type)
            intent.putString(KEY_DATA, controlId)
            ActivityUtils.startActivity(intent, MallGoodsDetailsActivity::class.java)
        }
    }

    override fun getContentView() = R.layout.activity_mall_goods_details
}
