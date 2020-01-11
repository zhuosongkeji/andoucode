package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BrowsingAdapter;
import com.zskjprojectj.andouclient.adapter.InfoFragmentAdapter;
import com.zskjprojectj.andouclient.adapter.SquareAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.InfoFragmentBean;
import com.zskjprojectj.andouclient.entity.SquareBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

/**
 * 广场碎片
 */
public class SquareFragment extends BaseFragment {
    private RecyclerView mRecycler;
    BrowsingAdapter adapter=new BrowsingAdapter();
    private ArrayList<SquareBean> mDataList;
    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_square;
    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        for (int i=0;i<4;i++) {
            SquareBean databean = new SquareBean();
            mDataList.add(databean);
        }
        SquareAdapter adapter=new SquareAdapter(R.layout.item_squarefragment,mDataList);
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

    @Override
    protected void getDataFromServer() {

    }
}
