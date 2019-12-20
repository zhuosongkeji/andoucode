package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingbean;
import com.zskjprojectj.andouclient.view.TopView;

import java.util.ArrayList;

import butterknife.OnClick;

/**
 * 在线商城购物车
 */
public class MallShoppingFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private TopView mTopView;
    private ArrayList<MallShoppingbean> dataList;
    private Button btn_settleaccounts;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTopView = view.findViewById(R.id.alltopview);
        mTopView.setTitle("购物车");
        getBarDistance(mTopView);
        mRecycler = view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        btn_settleaccounts=view.findViewById(R.id.btn_settleaccounts);
        btn_settleaccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MallOnlineOrderActivity.class));
            }
        });
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_focuson;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

        dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MallShoppingbean databean = new MallShoppingbean();
            databean.setMallShoppingName("玛莎兰提");
            dataList.add(databean);
        }

        MallShoppingAdapter adapter = new MallShoppingAdapter(R.layout.fragment_mall_shopping_view, dataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
    }

}
