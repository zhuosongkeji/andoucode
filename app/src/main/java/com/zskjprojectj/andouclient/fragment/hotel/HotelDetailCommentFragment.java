package com.zskjprojectj.andouclient.fragment.hotel;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.hotel.CommentAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailCommentBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.hotel
 * author: Bin email:wangdabin2333@163.com
 * startTime: 2019/12/7 9:32
 * des: 酒店详情评论
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailCommentFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private  CommentAdapter adapter=new CommentAdapter();

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_hotel_detail_comment;
    }

    @Override
    protected void getDataFromServer() {

        Bundle bundle = getArguments();
        String hotelMerchantId = bundle.getString("hotelMerchantId");

        HttpRxObservable.getObservable(ApiUtils.getApiService().hotelDetailsCommentList(hotelMerchantId,"1"))
                .subscribe(new BaseObserver<List<HotelDetailCommentBean>>(mAty) {
                    @Override
                    public void onHandleSuccess(List<HotelDetailCommentBean> hotelDetailCommentBeans) throws IOException {
                        adapter.setNewData(hotelDetailCommentBeans);
                    }
                });

    }

    @Override
    protected void initData() {



        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);

    }
}
