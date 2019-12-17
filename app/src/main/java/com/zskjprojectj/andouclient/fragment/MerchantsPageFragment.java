package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.MerchantListAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MerchantListBean;
import com.zskjprojectj.andouclient.view.TopView;

import java.util.ArrayList;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 *商家信息
 * @author yizhubao
 */
public class MerchantsPageFragment extends BaseFragment {
    private TopView mTitle;
    private RecyclerView mRecycler;
    private ArrayList<MerchantListBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTitle=view.findViewById(R.id.alltopview);
        mTitle.setTitle("商家");
        getBarDistance(mTitle);
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_merchantspage;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

        mDataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            MerchantListBean databean=new MerchantListBean();
            databean.setImageResource(R.mipmap.ic_busiess_canting);
            databean.setName("北平楼涮羊肉");
            mDataList.add(databean);
        }

        MerchantListAdapter adapter=new MerchantListAdapter(R.layout.merchant_item_view,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);

    }
}
