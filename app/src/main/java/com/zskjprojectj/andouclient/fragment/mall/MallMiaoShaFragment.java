package com.zskjprojectj.andouclient.fragment.mall;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallGoodsDetailsActivity;
import com.zskjprojectj.andouclient.adapter.mall.MallMiaoShaAdapter;
import com.zskjprojectj.andouclient.adapter.mall.MallMiaoShaGoodsAdapter;
import com.zskjprojectj.andouclient.entity.mall.MallMiaoShaBean;

import java.util.ArrayList;

import butterknife.BindView;

public class MallMiaoShaFragment extends BaseFragment {

    public MallMiaoShaFragment(MiaoSha miaoSha) {
        this.miaoSha = miaoSha;
    }

    MiaoSha miaoSha ;

    MallMiaoShaGoodsAdapter goodsAdapter = new MallMiaoShaGoodsAdapter();
    MallMiaoShaAdapter adapter = new MallMiaoShaAdapter();


    @BindView(R.id.mRvGoodGoods)
    RecyclerView mRvGoodGoods;

    @BindView(R.id.mRvMiaoShaGoods)
    RecyclerView mRvMiaoShaGoods;

    @Override
    protected int getContentView() {
        return R.layout.fragment_mall_miao_sha;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        goodsAdapter.bindToRecyclerView(mRvGoodGoods);
        adapter.bindToRecyclerView(mRvMiaoShaGoods);
        onBind(miaoSha);

        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                MallGoodsDetailsActivity.start("23");
            }
        });
    }

    private void onBind(MiaoSha miaoSha) {
        goodsAdapter.setNewData(miaoSha.recommends);
        adapter.miaoSha = miaoSha;
        adapter.setNewData(miaoSha.goods);
    }



}
