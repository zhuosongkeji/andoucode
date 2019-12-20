package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingbean;
import com.zskjprojectj.andouclient.view.TopView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 在线商城购物车
 */
public class MallShoppingFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MallShoppingbean> dataList;

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @BindView(R.id.iv_header_back)
    ImageView mHeaderBack;

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mHeaderTitle.setText("购物车");
        mHeaderBack.setVisibility(View.GONE);
        getBarDistance(mHeaderTitleView);
        mRecycler = view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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
