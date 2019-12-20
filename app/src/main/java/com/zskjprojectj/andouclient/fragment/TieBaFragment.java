package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.InfoFragmentAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.InfoFragmentBean;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.view.TopView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     time   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class TieBaFragment extends BaseFragment {
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;


    private RecyclerView mRecycler;
    private ArrayList<InfoFragmentBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mHeaderTitle.setText("贴吧");
        getBarDistance(mHeaderTitleView);
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_tiebapage;
    }

    @Override
    protected void getDataFromServer() {

    }

    @Override
    protected void initData() {
        mDataList=new ArrayList<>();
        for (int i=0;i<4;i++) {
            InfoFragmentBean databean = new InfoFragmentBean();
            mDataList.add(databean);
        }
        InfoFragmentAdapter adapter=new InfoFragmentAdapter(R.layout.item_tiebafragment,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("功能暂未完善给您带来的不便敬请谅解");
            }
        });

    }

    @OnClick(R.id.iv_header_back)
    public void clickBack(){
        mAty.finish();
    }
}
