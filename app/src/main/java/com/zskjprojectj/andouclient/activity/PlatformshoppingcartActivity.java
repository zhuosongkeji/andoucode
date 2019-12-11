package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BalancesubsidiaryAdapter;
import com.zskjprojectj.andouclient.adapter.PlatformshoppingcartAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BalancesubsidiaryBean;
import com.zskjprojectj.andouclient.entity.PlatformshoppingcartBean;

import java.util.ArrayList;

/**
 * 个人中心购物车
 */
public class PlatformshoppingcartActivity extends BaseActivity {
    private RecyclerView mRecycler;
    private ArrayList<PlatformshoppingcartBean> mDataList;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_platformshoppingcart);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("购物车");
        mDataList = new ArrayList<>();
        for (int i=0;i<20;i++){
            PlatformshoppingcartBean databean=new PlatformshoppingcartBean();
            databean.setShopname("玛莎兰提旗舰店");
            databean.setGoodsintroduction("玛莎拉蒂男士全牛皮鳄鱼纹皮带");
            databean.setGoodsprice("￥119");
            mDataList.add(databean);
        }
       PlatformshoppingcartAdapter  adapter=new PlatformshoppingcartAdapter(R.layout.fragment_mall_shopping_view,mDataList);
        adapter.openLoadAnimation();
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
////                ToastUtil.showToast("点击了第"+position);
//                showDialog();
//
//            }
//        });
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                //showDialog();
            }
        });
        mRecycler.addItemDecoration(new DividerItemDecoration(mAt,DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
