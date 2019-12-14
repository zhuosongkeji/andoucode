package com.zskjprojectj.andouclient.activity.mall;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingHomeAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingHomeBean;

import java.util.ArrayList;

import butterknife.BindView;

public class MallShoppingHomeActivity extends BaseActivity implements BaseQuickAdapter.OnItemClickListener {


    @BindView(R.id.rv_recycler)
    RecyclerView mRecycler;


    private ArrayList<MallShoppingHomeBean> dataList;




    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mall_shopping_home);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        dataList=new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            MallShoppingHomeBean dataBean=new MallShoppingHomeBean();
            dataBean.setName("aaa");
            dataList.add(dataBean);
        }

        MallShoppingHomeAdapter adapter=new MallShoppingHomeAdapter(R.layout.activity_mall_shopping_item_view,dataList);
        adapter.openLoadAnimation();
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

    }

    @Override
    protected void initViews() {

        mRecycler.setLayoutManager(new GridLayoutManager(this,2));


    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        startActivity(new Intent(MallShoppingHomeActivity.this,MallGoodsDetailsActivity.class));
    }
}
