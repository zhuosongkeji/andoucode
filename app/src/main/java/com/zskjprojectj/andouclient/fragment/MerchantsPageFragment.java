package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.hotel.HotelDetailActivity;
import com.zskjprojectj.andouclient.adapter.MerchantListAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.MerchantListBean;
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
 *商家信息
 * @author yizhubao
 */
public class MerchantsPageFragment extends BaseFragment {
    @BindView(R.id.ll_classify)
    LinearLayout mClassify;
    private PopupWindow mPopWindow;
    @BindView(R.id.tv_capacity_sort)
    TextView mCapacitySort;
    private TopView mTitle;
    private RecyclerView mRecycler;
    private ArrayList<MerchantListBean> mDataList;
    @Override
    protected void initViews(View view, Bundle savedInstanceState) {
        mTitle=view.findViewById(R.id.alltopview);
        mTitle.setTitle("商家");
        getBarDistance(mTitle);
        mRecycler=view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_merchantspage;
    }
    //初始化popuwindow
    private void initPopuWindow(View contentView, TextView textView) {
        mPopWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setContentView(contentView);

        //设置背景色
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.8f;
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);

        //popupWindow获取焦点
        mPopWindow.setFocusable(true);
        mPopWindow.setAnimationStyle(R.style.popmenu_animation); //动画
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        //设置popupWindow消失时的监听
        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            //在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp =getActivity(). getWindow().getAttributes();
                lp.alpha = 1f;
               getActivity(). getWindow().setAttributes(lp);
                textView.setTextColor(getResources().getColor(R.color.color_common_font));
            }
        });
    }
    @Override
    protected void getDataFromServer() {

    }
    @OnClick({R.id.ll_merchants_classification, R.id.ll_in_screening, R.id.ll_sorting_way})
    public void clickSelector(View v)
    {
        switch (v.getId())
        {
            case R.id.ll_merchants_classification:
                initclassification();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            case R.id.ll_in_screening:
                initscreening();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
            case R.id.ll_sorting_way:
                initsortingway();
                if (mPopWindow != null && !mPopWindow.isShowing()) {
                    mPopWindow.showAsDropDown(mClassify, 0, 0);
                }
                break;
        }
    }

    /**
     * 商家分类
     */
    private void initclassification()
    {

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shop_comprehensive, null);

        initPopuWindow(contentView,mCapacitySort);
    }

    /**
     * 地区筛选
     */
    private void initscreening()
    {

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shop_comprehensive, null);

        initPopuWindow(contentView,mCapacitySort);
    }

    /**
     * 排序方式
     */
    private void initsortingway()
    {

        View contentView = LayoutInflater.from(getContext()).inflate(R.layout.dialog_shop_comprehensive, null);

        initPopuWindow(contentView,mCapacitySort);
    }
    @Override
    protected void initData() {

        mDataList=new ArrayList<>();
        for (int i=0;i<20;i++){
            MerchantListBean databean=new MerchantListBean();
            mDataList.add(databean);
        }

        MerchantListAdapter adapter=new MerchantListAdapter(R.layout.merchant_item_view,mDataList);
        adapter.openLoadAnimation();
        mRecycler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycler.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                startActivity(new Intent(getContext(), HotelDetailActivity.class));
            }
        });
    }
}
