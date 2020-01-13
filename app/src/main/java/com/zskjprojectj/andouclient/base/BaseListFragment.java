package com.zskjprojectj.andouclient.base;

import android.os.Build;
import android.os.Bundle;

import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.BaseHandleObserver;
import com.zskjprojectj.andouclient.refresh.MySwipeRefreshLayout;
import com.zskjprojectj.andouclient.utils.EmptyView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.annotations.Nullable;

public abstract class BaseListFragment<D> extends BaseFragment implements BaseQuickAdapter.OnItemClickListener, EmptyView.OnNetErrorRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    private MySwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private BaseQuickAdapter<D, BaseViewHolder> mAdapter;
    private List<D> mDatas = new ArrayList<>();
    protected EmptyView emptyView;
    private int page;
    private int pageSize = 15;
    private boolean isRequestListData = true;

    @Override
    protected int getContentViewRes() {
        return R.layout.activity_base_list;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initViews(View view, @Nullable Bundle savedInstanceState) {
        initSwiprefresh(view);
        initRecyclerView(view);
        initEmpty();
        findView(view);
    }

    public void setRequestListData(boolean requestListData) {
        isRequestListData = requestListData;
    }

    public abstract void findView(View view);

    private void initEmpty() {
        emptyView = new EmptyView(getActivity());
        emptyView.setEmptyType(EmptyView.NO_DATA);
        emptyView.setOnNetErrorRefreshListener(this);
    }

    @Override
    protected void initData() {
        initAdapter();
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    @Override
    protected void onFirsLoad() {
        refresh();
    }

    protected void initAdapter() {
        mAdapter = createAdapter(mDatas);
        mAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                loadMore();
            }
        }, mRecyclerView);
        mAdapter.setOnItemClickListener(this);
        mAdapter.setOnItemChildClickListener(this);
        mAdapter.setEmptyView(emptyView);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    protected void initRecyclerView(View view) {
        try {
            mRecyclerView = view.findViewById(getRecyclerId());
            if (mRecyclerView != null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initSwiprefresh(View view) {
        try {
            swipeRefreshLayout = view.findViewById(getSwipeLayoutId());
            swipeRefreshLayout.setColorSchemeResources(R.color.holo_blue_bright, R.color.holo_green_light, R.color.holo_orange_light, R.color.holo_red_light);
            if (swipeRefreshLayout != null) {
                swipeRefreshLayout.setOnRefreshListener(new MySwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refresh();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 刷新控件
     *
     * @return
     */
    public int getSwipeLayoutId() {
        return R.id.swiperefreshlayout;
    }

    /**
     * 列表View
     *
     * @return
     */
    protected int getRecyclerId() {
        return R.id.recyclerview;
    }

    /**
     * 刷新
     */
    protected void refresh() {
        if (!isRequestListData) return;
        page = 1;
//        mAdapter.setEnableLoadMore(false);
        swipeRefreshLayout.setRefreshing(true);
        getDataFromServer();
    }

    /**
     * 加载更多
     */
    protected void loadMore() {
        page++;
        getDataFromServer();
    }

    @Override
    public void getDataFromServer() {
        Observable<List<D>> observable = getHttpListObservable();
        observable.subscribe(new BaseHandleObserver<List<D>>() {

            @Override
            public void onNext(List<D> datas) {
                if (swipeRefreshLayout.isRefreshing()) {
                    mAdapter.getData().clear();
                }

                if (datas != null && !datas.isEmpty()) {
                    mAdapter.addData(datas);
                    mAdapter.loadMoreComplete();
                    if (mAdapter.getData().size() < getPageSize()) {
                        mAdapter.loadMoreEnd();
                    }

                } else {
                    if (swipeRefreshLayout.isRefreshing()) {
                        if (mAdapter.getItemCount() == 0) {
//                            addEmptyView();
//                            emptyView.setEmptyType(EmptyView.NO_DATA);
                        }
                        mAdapter.notifyDataSetChanged();
                    }
                    mAdapter.loadMoreEnd();
                }
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                onComplete();

                page--;
                mAdapter.loadMoreFail();
            }

            @Override
            public void onComplete() {
                super.onComplete();
                if (swipeRefreshLayout.isRefreshing()) {
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }

    protected abstract Observable<List<D>> getHttpListObservable();

    protected D getItem(int position) {
//        return mDatas.get(position);
        return mAdapter.getItem(position);
    }

    public BaseQuickAdapter getAdapter() {
        return mAdapter;
    }

    protected abstract BaseQuickAdapter createAdapter(List<D> mDatas);

    public int getPage() {
        return page;
    }

    protected void enableLoadMore(boolean enbale) {
        mAdapter.setEnableLoadMore(enbale);
    }

    protected void enableRefresh(boolean enbale) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setEnabled(enbale);
        }
    }

    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    protected int getJsonPage(BaseResult<JSONObject> objectBaseResult) {
        try {
            int i = objectBaseResult.data.getInteger("page_limit");
            return i;
        } catch (Exception e) {
            e.printStackTrace();
            return pageSize;
        }
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onNetErrorRefresh() {
        refresh();
    }
}
