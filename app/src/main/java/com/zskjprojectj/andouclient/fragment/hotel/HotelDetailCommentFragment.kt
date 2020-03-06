package com.zskjprojectj.andouclient.fragment.hotel

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.zhuosongkj.android.library.app.BaseFragment
import com.zhuosongkj.android.library.util.PageLoadUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.hotel.CommentAdapter
import com.zskjprojectj.andouclient.http.ApiUtils
import kotlinx.android.synthetic.main.fragment_hotel_detail_comment.*

class HotelDetailCommentFragment : BaseFragment() {
    private val adapter = CommentAdapter()
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val pageLoadUtil =
                PageLoadUtil.get(
                        mActivity,
                        recyclerView,
                        adapter,
                        refreshLayout)
        pageLoadUtil.load {
            ApiUtils.getApiService().hotelDetailsCommentList(
                    arguments?.getString("hotelMerchantId"),
                    pageLoadUtil.page)
        }
    }

    override fun getContentView() = R.layout.fragment_hotel_detail_comment
}