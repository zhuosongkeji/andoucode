package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.ArrayList;

import butterknife.BindView;

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
public class InfoPageFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<InfoFragmentBean> mDataList;

    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.iv_header_back)
    ImageView mHeaderBack;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;


    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        int barHeight = com.blankj.utilcode.util.BarUtils.getStatusBarHeight();
        if (barHeight > 0) {
            //设置状态栏的高度
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTitleView.getLayoutParams();
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mAty) + layoutParams.topMargin;
            mTitleView.setLayoutParams(layoutParams);
        }
        mHeaderTitle.setText("信息");
        mHeaderBack.setVisibility(View.GONE);
        mRecycler=view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_infopage;
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
        InfoFragmentAdapter adapter=new InfoFragmentAdapter(R.layout.item_infopage,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("信息功能暂未完善给您带来的不便敬请谅解");
            }
        });
    }

}
