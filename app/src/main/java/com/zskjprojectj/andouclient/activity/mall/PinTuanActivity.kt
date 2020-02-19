package com.zskjprojectj.andouclient.activity.mall

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import butterknife.OnClick
import com.blankj.utilcode.util.BarUtils
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.FormatUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.mall.PinTuanMustAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.PinTuan
import com.zskjprojectj.andouclient.model.PinTuanGoods
import com.zskjprojectj.andouclient.model.TodayTopResponse
import com.zskjprojectj.andouclient.utils.UrlUtil
import kotlinx.android.synthetic.main.activity_pin_tuan.*
import java.util.*

class PinTuanActivity : BaseActivity() {
    var pinTuanMustAdapter = PinTuanMustAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BarUtils.transparentStatusBar(mActivity)
        BarUtils.setStatusBarLightMode(mActivity, false)
        ActionBarUtil.setTitle(mActivity, "超值团购", false)
        ActionBarUtil.getBackground(mActivity, false).alpha = 0f
        pinTuanMustAdapter.bindToRecyclerView(mustRecyclerView)
        (findViewById<View>(R.id.appBarLayout) as AppBarLayout)
                .addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout: AppBarLayout?, verticalOffset: Int ->
                    ActionBarUtil.getBackground(mActivity, false).alpha = Math.abs(verticalOffset) * 0.01f
                })
        fixContainer.setPadding(fixContainer.paddingLeft, BarUtils.getStatusBarHeight(), fixContainer.paddingEnd, 0)
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().todayTop() }
                , {
            onBind(it.data)
        })
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().pinTuanType() }
                , {
            onBind(it.data)
        })
    }

    private fun onBind(types: List<PinTuan.PinTuanType>) {
        val titles = arrayOfNulls<String>(types.size)
        val pinTuanFragments = ArrayList<Fragment>()
        for (i in types.indices) {
            val pinTuanType = types[i]
            titles[i] = pinTuanType.name
            pinTuanFragments.add(PinTuanFragment(pinTuanType))
        }
        tabLayout.setViewPager(viewPager, titles, mActivity, pinTuanFragments)
        pinTuanMustAdapter.setOnItemClickListener { _, _, position ->
            MallGoodsDetailsActivity.start(pinTuanMustAdapter.getItem(position)?.id)
        }
    }

    private fun onBind(pinTuan: TodayTopResponse) {
        pinTuanMustAdapter.setNewData(pinTuan.today_goods)
        for (i in pinTuan.hot_goods.indices) {
            val goods = pinTuan.hot_goods[i]
            when (i) {
                0 -> {
                    bindRecommendPinTuanGoods(goods, findViewById(R.id.recommendGoods1))
                }
                1 -> {
                    bindRecommendPinTuanGoods(goods, findViewById(R.id.recommendGoods2))
                }
                else -> {
                    bindRecommendPinTuanGoods(goods, findViewById(R.id.recommendGoods3))
                }
            }
        }
    }

    private fun bindRecommendPinTuanGoods(goods: PinTuanGoods, view: View) {
        Glide.with(mActivity)
                .load(UrlUtil.getImageUrl(goods.img))
                .into((view.findViewById<View>(R.id.recommendImg) as ImageView))
        (view.findViewById<View>(R.id.recommendTitleTxt) as TextView).text = goods.name
        (view.findViewById<View>(R.id.recommendPriceTxt) as TextView).text = FormatUtil.getMoneyString(goods.price)
        (view.findViewById<View>(R.id.recommendPeopleTxt) as TextView).text = goods.people.toString() + "人团"
    }

    override fun getContentView() = R.layout.activity_pin_tuan

    @OnClick(R.id.recommendGoods1, R.id.recommendGoods2, R.id.recommendGoods3)
    fun pinTuanClick(view: View) {
        when (view.id) {
            R.id.recommendGoods1, R.id.recommendGoods2, R.id.recommendGoods3 -> MallGoodsDetailsActivity.start("23")
        }
    }
}