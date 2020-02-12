package com.zskjprojectj.andouclient.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.flyco.tablayout.SlidingTabLayout;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.InfoFragmentAdapter;
import com.zskjprojectj.andouclient.adapter.SquareAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.InfoFragmentBean;
import com.zskjprojectj.andouclient.entity.SquareBean;
import com.zskjprojectj.andouclient.utils.BarUtils;
import com.zskjprojectj.andouclient.utils.StatusBarUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.view.TopView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *     e-mail : 3307501630@qq.com
 *     startTime   : 2019/10/25
 *     desc   :
 *     version: 1.0
 * </pre>
 *
 * @author yizhubao
 */
public class TieBaFragment extends BaseFragment {

    @BindView(R.id.title_view)
    FrameLayout mTitleView;

    private ArrayList<Fragment> fragments = new ArrayList<>();

//    private RecyclerView mRecycler;
//    private ArrayList<SquareBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        int barHeight = StatusBarUtil.getStatusBarHeight(mAty);
        if (barHeight > 0) {
            //设置状态栏的高度
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTitleView.getLayoutParams();
            layoutParams.topMargin = BarUtils.getStatusBarHeight(mAty) + layoutParams.topMargin;
            mTitleView.setLayoutParams(layoutParams);
        }
        fragments.add(new SquareFragment());
        fragments.add(new CircleoffriendsFragment());
        ((SlidingTabLayout)view.findViewById(R.id.tabLayout)).setViewPager(
                view.findViewById(R.id.viewPager),
                new String[]{ "广场", "我的"},
                getActivity(),
                fragments);
//        mRecycler=view.findViewById(R.id.rv_recycler);
//        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
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

//        mDataList=new ArrayList<>();
//        for (int i=0;i<4;i++) {
//            SquareBean databean = new SquareBean();
//            mDataList.add(databean);
//        }
//        SquareAdapter adapter=new SquareAdapter(R.layout.item_tiebafragment,mDataList);
//        adapter.openLoadAnimation();
//        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
//        mRecycler.setAdapter(adapter);
//        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
//            @Override
//            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//               // ToastUtil.showToast("功能暂未完善给您带来的不便敬请谅解");
//            }
//        });
//
   }
}
