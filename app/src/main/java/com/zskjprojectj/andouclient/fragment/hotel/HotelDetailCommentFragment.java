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

import java.util.ArrayList;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.hotel
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/7 9:32
 * des: 酒店详情评论
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class HotelDetailCommentFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<HotelDetailCommentBean> mDataList;

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

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();

        for (int i=0;i<20;i++){
            HotelDetailCommentBean databean=new HotelDetailCommentBean();
            databean.setHeadPic(R.mipmap.ic_touxiang);
            databean.setComment("酒店环境干净卫生，入住体验非常好");
            databean.setCommentImage(R.mipmap.hotel_details);
            databean.setName("暮看日西沉");
            mDataList.add(databean);
        }

        CommentAdapter adapter=new CommentAdapter(R.layout.comment_item_view,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);

    }
}
