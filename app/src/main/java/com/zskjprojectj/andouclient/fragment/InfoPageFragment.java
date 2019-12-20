package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.InfoFragmentAdapter;
import com.zskjprojectj.andouclient.adapter.hotel.CommentAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.InfoFragmentBean;
import com.zskjprojectj.andouclient.entity.hotel.HotelDetailCommentBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.view.TopView;

import java.util.ArrayList;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class InfoPageFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<InfoFragmentBean> mDataList;
    private TopView topView;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        topView=view.findViewById(R.id.alltopview);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_infopage;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        topView.setTitle("信息");
        for (int i=0;i<4;i++) {
            InfoFragmentBean databean = new InfoFragmentBean();
            mDataList.add(databean);
        }
        InfoFragmentAdapter adapter=new InfoFragmentAdapter(R.layout.item_infopage,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("信息功能暂未完善给您带来的不便敬请谅解");
            }
        });
    }
}
