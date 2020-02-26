package com.zskjprojectj.andouclient.fragment

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.*
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.LoginInfoUtil.isLogin
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.utils.UrlUtil
import kotlinx.android.synthetic.main.fragment_me.*
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView

class MeFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        loginBtn.setOnClickListener {
            LoginActivity.start(mFragment, 666)
        }
        mTitleView.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
        img_touxiang.setOnClickListener { startActivity(Intent(context, OpeningmemberActivity::class.java)) }
        mycenter_vegetablemarket_layout.setOnClickListener { startActivity(Intent(context, VegetableMarketActivity::class.java)) }
        mycenter_foodorder_layout.setOnClickListener { startActivity(Intent(context, FoodorderActivity::class.java)) }
        mycenter_qrcode_layout.setOnClickListener { ToastUtil.showToast("功能暂未开通敬请期待....") }
        mycenter_myrelease_layout.setOnClickListener { ToastUtil.showToast("功能暂未开通敬请期待....") }
        mycenter_hotelorder_layout.setOnClickListener { startActivity(Intent(context, HotelorderActivity::class.java)) }
        mycenter_shoporder_layout.setOnClickListener {
            startActivity(Intent(context, ShoporderActivity::class.java))
        }
        mycenter_restaurant_layout.setOnClickListener { ActivityUtils.startActivity(RestaurantOrderListActivity::class.java) }
        mycenter_invitation_with_courtesy_layout.setOnClickListener { startActivity(Intent(context, InvitationActivity::class.java)) }
        mycenter_mywallet_layout.setOnClickListener { startActivity(Intent(context, MywalletActivity::class.java)) }
        mycenter_releas_layout.setOnClickListener { startActivity(Intent(context, MyreleaseActivity::class.java)) }
        mycenter_shoppingcart_layout.setOnClickListener {
            val intent = Intent(mActivity, MallHomeActivity::class.java)
            intent.putExtra("id", "MallShopping")
            startActivity(intent)
        }
        mycenter_mycollection_layout.setOnClickListener { startActivity(Intent(context, MycollectionActivity::class.java)) }
        mycenter_browsing_layout.setOnClickListener { startActivity(Intent(context, HistoryActivity::class.java)) }
        mycenter_myaddress_layout.setOnClickListener { startActivity(Intent(context, MyaddressActivity::class.java)) }
        mycenter_myscore_layout.setOnClickListener { startActivity(Intent(context, MyscoreActivity::class.java)) }
        mycenter_mymessage_layout.setOnClickListener { startActivity(Intent(context, MymessageActivity::class.java)) }
        mycenter_business_residence_layout.setOnClickListener { startActivity(Intent(context, BusinessresidenceActivity::class.java)) }
        mycenter_downloadapp_layout.setOnClickListener { startActivity(Intent(context, DownloadappActivity::class.java)) }
        mycenter_operationvideo_layout.setOnClickListener { startActivity(Intent(context, OperationvideoActivity::class.java)) }
        mycenter_myfocuson_layout.setOnClickListener { startActivity(Intent(context, MyFocusonActivity::class.java)) }
        img_meset.setOnClickListener { startActivity(Intent(context, MesettingActivity::class.java)) }
    }

    private fun loadData() {
        if (!isLogin()) {
            return
        } else {
            loginBtn.visibility = View.GONE
        }
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().getpersonal(LoginInfoUtil.getUid(), LoginInfoUtil.getToken()) },
                { result ->
                    tv_nickname.text = result.data.name
                    tv_collectionnum.text = result.data.collect
                    tv_focusonnum.text = result.data.focus
                    tv_browsenum.text = result.data.record
                    tv_moneynum.text = result.data.money
                    tv_integralnumm.text = result.data.integral
                    if ("0" == result.data.status) {
                        img_showvip.setImageResource(R.mipmap.putongvip)
                    } else {
                        img_showvip.setImageResource(R.mipmap.vipplusicon)
                    }
                    val numhotel = Integer.parseInt(result.data.booksordernum)
                    val numfood = Integer.parseInt(result.data.foodsordernum)
                    val nummall = Integer.parseInt(result.data.goodordernum)
                    QBadgeView(context).bindTarget(img_hotelnum).setBadgeNumber(numhotel).setBadgeTextSize(8f, true).setBadgeGravity(Gravity.END or Gravity.TOP).setOnDragStateChangedListener { dragState, badge, targetView ->
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                            badge.hide(true)
                        }
                    }
                    QBadgeView(context).bindTarget(img_mallnum).setBadgeNumber(nummall).setBadgeTextSize(8f, true).setBadgeGravity(Gravity.END or Gravity.TOP).setOnDragStateChangedListener { dragState, badge, targetView ->
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                            badge.hide(true)
                        }
                    }
                    QBadgeView(context).bindTarget(img_restaurantnum).setBadgeNumber(numfood).setBadgeTextSize(8f, true).setBadgeGravity(Gravity.END or Gravity.TOP).setOnDragStateChangedListener { dragState, badge, targetView ->
                        if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                            badge.hide(true)
                        }
                    }
                    Glide.with(mActivity).load(UrlUtil.getImageUrl(result.data.avator)).apply(RequestOptions.bitmapTransform(CircleCrop())).into(img_touxiang)
                })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 666 && resultCode == Activity.RESULT_OK) {
            loginBtn.visibility = View.GONE
            loadData()
        }
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    fun refresh() {
        loadData()
    }

    override fun getContentView() = R.layout.fragment_me
}
