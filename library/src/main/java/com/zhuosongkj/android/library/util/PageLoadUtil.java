package com.zhuosongkj.android.library.util;

import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.R;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.model.IListData;
import com.zhuosongkj.android.library.ui.RefreshHeaderView;

import java.util.List;

public class PageLoadUtil<T> {
    public int page;
    private BaseQuickAdapter<T, BaseViewHolder> adapter;
    private SmartRefreshLayout refreshLayout;
    private BaseActivity activity;
    private RequestUtil.ObservableProvider<? extends IListData<T>> observableProvider;
    private View loadingView;
    private OnLoadListener<T> onLoadListener;

    private PageLoadUtil(BaseActivity activity, RecyclerView recyclerView, BaseQuickAdapter<T, BaseViewHolder> adapter
            , SmartRefreshLayout refreshLayout) {
        this.activity = activity;
        this.adapter = adapter;
        this.refreshLayout = refreshLayout;
        this.adapter.bindToRecyclerView(recyclerView);
        adapter.setLoadMoreView(new LoadMoreView() {

            @Override
            public int getLayoutId() {
                return R.layout.layout_load_more_view;
            }

            @Override
            protected int getLoadingViewId() {
                return R.id.load_more_loading_view;
            }

            @Override
            protected int getLoadFailViewId() {
                return R.id.load_more_load_fail_view;
            }

            @Override
            protected int getLoadEndViewId() {
                return R.id.load_more_load_end_view;
            }
        });
        loadingView = LayoutInflater.from(activity).inflate(R.layout.layout_loading_view, null);
        refreshLayout.setRefreshHeader(new RefreshHeaderView(this.activity));
        refreshLayout.setOnRefreshListener(temp -> loadData(true));
        refreshLayout.setEnableLoadMore(false);
        adapter.setOnLoadMoreListener(() -> loadData(false), recyclerView);
    }

    private void startLoadingImage() {
        ((AnimationDrawable) ((ImageView) loadingView.findViewById(R.id.loadingImg)).getDrawable()).start();
        adapter.setEmptyView(loadingView);
    }

    private void loadData(boolean needRefresh) {
        startLoadingImage();
        if (observableProvider == null)
            return;
        if (needRefresh) {
            page = 1;
        }
        RequestUtil.request(activity, false, false, observableProvider
                , result -> {
                    if (onLoadListener != null) {
                        onLoadListener.onLoad(needRefresh, result.data.getDataList());
                    }
                    adapter.setEmptyView(R.layout.layout_empty_view);
                    page += 1;
                    if (needRefresh) {
                        adapter.setNewData(result.data.getDataList());
                        if (result.data.getDataList().size() == 0) {
                            adapter.loadMoreEnd(true);
                        }
                        refreshLayout.finishRefresh();
                    } else {
                        if (result.data.getDataList().size() == 0) {
                            adapter.loadMoreEnd();
                        } else {
                            adapter.addData(result.data.getDataList());
                            adapter.loadMoreComplete();
                        }
                    }
                }, msg -> refreshLayout.postDelayed(() -> {
                    if (needRefresh) {
                        refreshLayout.finishRefresh();
                        adapter.setEmptyView(R.layout.layout_retry_view);
                        adapter.getEmptyView().setOnClickListener(view -> loadData(true));
                    } else {
                        adapter.loadMoreFail();
                    }
                }, 500));
    }

    public void load(RequestUtil.ObservableProvider<? extends IListData<T>> observableProvider) {
        load(observableProvider, null);
    }

    public void load(RequestUtil.ObservableProvider<? extends IListData<T>> observableProvider, OnLoadListener<T> onLoadListener) {
        this.observableProvider = observableProvider;
        this.onLoadListener = onLoadListener;
        loadData(true);
    }

    public void refresh() {
        loadData(true);
    }

    public static <T> PageLoadUtil<T> get(BaseActivity activity, RecyclerView recyclerView
            , BaseQuickAdapter<T, BaseViewHolder> adapter, SmartRefreshLayout refreshLayout) {
        return new PageLoadUtil<>(activity, recyclerView, adapter, refreshLayout);
    }

    public interface OnLoadListener<T> {
        void onLoad(boolean refresh, List<T> data);
    }
}
