package com.zskjprojectj.andouclient.fragment.mall;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.mall.MallCommentAdapter;
import com.zskjprojectj.andouclient.entity.mall.MallCommentBean;
import com.zskjprojectj.andouclient.http.ApiUtils;

import java.util.List;

public class MallGoodsCommentFragment extends BaseFragment {

    private RecyclerView mRecycler;
    private String goodId;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler = view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        Bundle bundle = getArguments();
        goodId = bundle.getString("id");
        RequestUtil.request(mActivity, true, false,
                () -> ApiUtils.getApiService().mallComment(goodId),
                result -> {
                    initAdapter(result.data);
                });
    }

    private void initAdapter(List<MallCommentBean> mallCommentBeans) {
        MallCommentAdapter adapter = new MallCommentAdapter(R.layout.fragment_mall_details_comment, mallCommentBeans);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(mActivity, DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
    }

    @Override
    protected int getContentView() {
        return R.layout.fragment_mall_goods_comment;
    }
}
