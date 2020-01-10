package com.zskjprojectj.andouclient.fragment.hotel;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.HotelorderdetailsActivity;
import com.zskjprojectj.andouclient.activity.HotelordergotoevaluationActivity;
import com.zskjprojectj.andouclient.adapter.hotel.MeHotelAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.hotel.MeHotelBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;
import java.util.List;

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

    @BindView(R.id.rv_recycler)
    RecyclerView mRvRecycler;

    private String status;
    private MeHotelAdapter adapter = new MeHotelAdapter();

    public MeHotelFragment(String status) {

        this.status = status;
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_shopallorder;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mRvRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void getDataFromServer() {

        HttpRxObservable.getObservable(ApiUtils.getApiService().mehotelOrder(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                status,
                "1"

        )).subscribe(new BaseObserver<List<MeHotelBean>>(mAty) {
            @Override
            public void onHandleSuccess(List<MeHotelBean> meHotelBeans) throws IOException {
                adapter.setNewData(meHotelBeans);
                adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
                    @Override
                    public void onItemChildClick(BaseQuickAdapter adapter1, View view, int position) {
                        //查看详情
                        if (view.getId() == R.id.btn_hotelorderdetails) {
                            HotelorderdetailsActivity.start(adapter.getItem(position));
                        }else if (view.getId()==R.id.btn_evaluate){

                    HotelordergotoevaluationActivity.start(meHotelBeans.get(position).getMerchants_id(),
                            meHotelBeans.get(position).getHotel_room_id(), meHotelBeans.get(position).getBook_sn());
                        }
                    }
                });
            }
        });

        adapter.openLoadAnimation();
        mRvRecycler.setAdapter(adapter);

    }
}
