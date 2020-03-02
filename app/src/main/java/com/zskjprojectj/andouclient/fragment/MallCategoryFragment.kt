package com.zskjprojectj.andouclient.fragment

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import com.azoft.carousellayoutmanager.CarouselLayoutManager
import com.azoft.carousellayoutmanager.CarouselZoomPostLayoutListener
import com.azoft.carousellayoutmanager.CenterScrollListener
import com.azoft.carousellayoutmanager.ItemTransformation
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.CategoryLevelAdapter
import com.zskjprojectj.andouclient.adapter.HomeAdapter
import com.zskjprojectj.andouclient.entity.mall.MallGoodsCateBean
import com.zskjprojectj.andouclient.http.ApiUtils
import kotlinx.android.synthetic.main.fragment_mall_category.*
import java.util.*
import kotlin.math.abs


class MallCategoryFragment : BaseFragment() {

    private val homeList = ArrayList<MallGoodsCateBean>()
    val showTitle = arrayListOf<Int>()

    private val level1Adapter = CategoryLevelAdapter(R.layout.layout_category_level_1_item)
    private var homeAdapter: HomeAdapter? = null
    private var currentItem: Int = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager1 = CarouselLayoutManager(CarouselLayoutManager.VERTICAL, true)
        layoutManager1.setPostLayoutListener(object : CarouselZoomPostLayoutListener() {
            override fun transformChild(child: View, itemPositionToCenterDiff: Float, orientation: Int): ItemTransformation {
                val scale = (2 * (2 * -StrictMath.atan(abs(itemPositionToCenterDiff / 4) + 1.0) / Math.PI + 1)).toFloat()
                return ItemTransformation(scale, scale, 0f, 0f)
            }
        })
        layoutManager1.maxVisibleItems = 6
        layoutManager1.addOnItemSelectionListener { adapterPosition ->
            if (CarouselLayoutManager.INVALID_POSITION != adapterPosition) {
                titleTxt.text = level1Adapter.getItem(adapterPosition)?.name
                lv_home.setSelection(adapterPosition)
            }
        }
        lv_menu.addOnScrollListener(CenterScrollListener())
        lv_menu.layoutManager = layoutManager1
        lv_menu.setHasFixedSize(true)
        level1Adapter.bindToRecyclerView(lv_menu)
        homeAdapter = HomeAdapter(mActivity, homeList)
        lv_home.adapter = homeAdapter
        lv_home.setOnScrollListener(object : AbsListView.OnScrollListener {
            private var scrollState: Int = 0

            override fun onScrollStateChanged(view: AbsListView, scrollState: Int) {
                this.scrollState = scrollState
            }

            override fun onScroll(view: AbsListView, firstVisibleItem: Int,
                                  visibleItemCount: Int, totalItemCount: Int) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return
                }
                val current = showTitle.indexOf(firstVisibleItem)
                if (currentItem != current && current >= 0) {
                    currentItem = current
                    titleTxt.text = level1Adapter.getItem(currentItem)?.name
                }
            }
        })
        RequestUtil.request(mActivity,true,true,
                {ApiUtils.getApiService().mallGoodsCate},
                {
                    for (i in it.data.indices) {
                        showTitle.add(i)
                        homeList.add(it.data[i])
                    }
                    titleTxt.text =  it.data[0].name
                    homeAdapter?.notifyDataSetChanged()
                    level1Adapter.addData(it.data)
                    level1Adapter.addData(it.data)
                })
    }
   
    override fun getContentView()=R.layout.fragment_mall_category
}
