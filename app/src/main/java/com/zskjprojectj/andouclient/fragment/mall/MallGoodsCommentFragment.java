package com.zskjprojectj.andouclient.fragment.mall;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MallCommentAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallCommentBean;
import com.zskjprojectj.andouclient.http.ApiException;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private List<MallCommentBean> mallCommentBeans;
    //商品id
    private String goodId;


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
        Log.d(TAG, "getDataFromServer:bundle " + goodId);
        //商品评论
        HttpRxObservable.getObservable(ApiUtils.getApiService().mallComment(goodId))
                .subscribe(new BaseObserver<List<MallCommentBean>>(mAty) {
                    @Override
                    public void onHandleSuccess(List<MallCommentBean> mallCommentBeans) throws IOException {
                        initAdapter(mallCommentBeans);


                    }

                    @Override
                    public void onHandleError(ApiException apiExc) {
                        super.onHandleError(apiExc);
                    }

                });
    }

    private void initAdapter(List<MallCommentBean> mallCommentBeans) {
        MallCommentAdapter adapter = new MallCommentAdapter(R.layout.fragment_mall_details_comment, mallCommentBeans);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        goodId = bundle.getString("id");


    }
}
