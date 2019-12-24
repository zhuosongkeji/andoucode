package com.zskjprojectj.andouclient.fragment.mall;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MallCommentAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallDetailCommentBean;

import java.util.ArrayList;

/**
 * 项目名称： andoucode
 * 包名：com.zskjprojectj.andouclient.fragment.mall
 * author: Bin email:wangdabin2333@163.com
 * time: 2019/12/11 14:42
 * des:
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MallGoodsCommentFragment extends BaseFragment implements View.OnClickListener {

    private RecyclerView mRecycler;
    private ArrayList<MallDetailCommentBean> dataList;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler = view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mall_goods_comment;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        dataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            MallDetailCommentBean databean=new MallDetailCommentBean();
            databean.setComment("产品比较好，比较实用");
            databean.setHeadPic(R.mipmap.ic_touxiang);
            databean.setName("暮看日西沉");
            dataList.add(databean);
        }

        MallCommentAdapter adapter=new MallCommentAdapter(R.layout.fragment_mall_details_comment,dataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);

    }
}