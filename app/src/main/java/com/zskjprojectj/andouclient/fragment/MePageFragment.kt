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
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.activity.*
import com.zskjprojectj.andouclient.entity.PersonalBean
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.http.BaseObserver
import com.zskjprojectj.andouclient.http.HttpRxObservable
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.LoginInfoUtil.isLogin
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.utils.UrlUtil
import kotlinx.android.synthetic.main.fragment_mepage.*
import q.rorbin.badgeview.Badge
import q.rorbin.badgeview.QBadgeView
import java.io.IOException

class MePageFragment : BaseFragment() {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (isLogin()) {
            loginBtn.visibility = View.GONE
        }
        loginBtn.setOnClickListener {
            LoginActivity.start(mFragment, 666)
        }
        header_title_view.setPadding(0, BarUtils.getStatusBarHeight(), 0, 0)
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
            val intent = Intent(mActivity, MallMainActivity::class.java)
            intent.putExtra("id", "MallShopping")
            startActivity(intent)
        }
        mycenter_mycollection_layout.setOnClickListener { startActivity(Intent(context, MycollectionActivity::class.java)) }
        mycenter_browsing_layout.setOnClickListener { startActivity(Intent(context, BrowsingActivity::class.java)) }
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
        }
        HttpRxObservable.getObservable(ApiUtils.getApiService().getpersonal(LoginInfoUtil.getUid(), LoginInfoUtil.getToken())).subscribe(object : BaseObserver<PersonalBean>(mActivity) {
            @Throws(IOException::class)
            override fun onHandleSuccess(personalBean: PersonalBean) {
                // tv_viplevel.setText(personalBean.getGrade());
                tv_nickname!!.text = personalBean.name
                tv_collectionnum!!.text = personalBean.collect
                tv_focusonnum!!.text = personalBean.focus
                tv_browsenum!!.text = personalBean.record
                tv_moneynum!!.text = personalBean.money
                tv_integralnumm!!.text = personalBean.integral
                if ("0" == personalBean.status) {
                    img_showvip!!.setImageResource(R.mipmap.putongvip)
                } else {
                    img_showvip!!.setImageResource(R.mipmap.vipplusicon)
                }
                val numhotel = Integer.parseInt(personalBean.booksordernum)
                val numfood = Integer.parseInt(personalBean.foodsordernum)
                val nummall = Integer.parseInt(personalBean.goodordernum)
                QBadgeView(context).bindTarget(img_hotelnum!!).setBadgeNumber(numhotel).setBadgeTextSize(8f, true).setBadgeGravity(Gravity.END or Gravity.TOP).setOnDragStateChangedListener { dragState, badge, targetView ->
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                        badge.hide(true)
                    }
                }
                QBadgeView(context).bindTarget(img_mallnum!!).setBadgeNumber(nummall).setBadgeTextSize(8f, true).setBadgeGravity(Gravity.END or Gravity.TOP).setOnDragStateChangedListener { dragState, badge, targetView ->
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                        badge.hide(true)
                    }
                }
                QBadgeView(context).bindTarget(img_restaurantnum!!).setBadgeNumber(numfood).setBadgeTextSize(8f, true).setBadgeGravity(Gravity.END or Gravity.TOP).setOnDragStateChangedListener { dragState, badge, targetView ->
                    if (Badge.OnDragStateChangedListener.STATE_SUCCEED == dragState) {
                        badge.hide(true)
                    }
                }
                Glide.with(mActivity).load(UrlUtil.getImageUrl(personalBean.avator)).apply(RequestOptions.bitmapTransform(CircleCrop())).into(img_touxiang!!)
            }
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

    override fun getContentView() = R.layout.fragment_mepage
}
