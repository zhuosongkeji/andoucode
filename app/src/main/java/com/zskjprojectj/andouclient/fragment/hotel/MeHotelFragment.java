package com.zskjprojectj.andouclient.fragment.hotel;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.HotelorderdetailsActivity;
import com.zskjprojectj.andouclient.activity.HotelordergotoevaluationActivity;
import com.zskjprojectj.andouclient.adapter.hotel.MeHotelAdapter;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import butterknife.BindView;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2020/1/9 14:28
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MeHotelFragment extends BaseFragment {

    @BindView(R.id.recyclerView)
    RecyclerView mRvRecycler;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

    private String status;
    private MeHotelAdapter adapter = new MeHotelAdapter();

    public MeHotelFragment(String status) {

        this.status = status;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initViews();
        getDataFromServer();
    }

    private void initViews() {

        mRvRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    }


    private void getDataFromServer() {
        PageLoadUtil<MeHotelBean> pageLoadUtil = PageLoadUtil.get((BaseActivity) getActivity(), mRvRecycler, adapter, mRefreshLayout);
        pageLoadUtil.load(() -> ApiUtils.getApiService().mehotelOrder(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                status,
                pageLoadUtil.page

        ));
        adapter.setOnItemChildClickListener((adapter1, view, position) -> {
            //查看详情
            if (view.getId() == R.id.btn_hotelorderdetails) {
                HotelorderdetailsActivity.start(adapter.getItem(position));
            } else if (view.getId() == R.id.btn_evaluate) {
                //发表评论
                HotelordergotoevaluationActivity.start(adapter.getItem(position).getMerchants_id(),
                        adapter.getItem(position).getHotel_room_id(), adapter.getItem(position).getBook_sn());
            }
        });

        adapter.openLoadAnimation();
        mRvRecycler.setAdapter(adapter);

    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mall_order_list;
    }
}
