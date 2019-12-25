package com.zskjprojectj.andouclient.fragment.mall;

import android.os.Bundle;
import android.view.View;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseFragment;

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
public class MallGoodsDetailFragment extends BaseFragment implements View.OnClickListener {
    private int id;
    public MallGoodsDetailFragment(int goodsId) {
        this.id=goodsId;
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_mall_goods_details;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {

    }
}
