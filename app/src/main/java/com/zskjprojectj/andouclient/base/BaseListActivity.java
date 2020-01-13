package com.zskjprojectj.andouclient.base;

import android.os.Bundle;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import com.google.gson.JsonParseException;
import com.zhuosongkj.android.library.model.BaseResult;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.BaseHandleObserver;
import com.zskjprojectj.andouclient.refresh.MySwipeRefreshLayout;
import com.zskjprojectj.andouclient.utils.EmptyView;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class BaseListActivity<D> extends BaseActivity implements BaseQuickAdapter.OnItemClickListener, EmptyView.OnNetErrorRefreshListener, BaseQuickAdapter.OnItemChildClickListener {

    protected MySwipeRefreshLayout swipeRefreshLayout;
    protected RecyclerView mRecyclerView;
    private BaseQuickAdapter<D, BaseViewHolder> mAdapter;
    private List<D> mDatas = new ArrayList<>();
    private EmptyView emptyView;
    private int page = 1;
    private int pageSize = 5;
    private boolean isRequestListData = true;
    protected abstract void findView();
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_base_list);
    }

    @Override
    protected void initViews() {
        initSwiprefresh();
        initRecyclerView();
        initEmpty();
        findView();
    }

    private void initEmpty() {
        emptyView = new EmptyView(this);
        emptyView.setEmptyType(EmptyView.NO_DATA);
        emptyView.setOnNetErrorRefreshListener(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initAdapter();
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(mAdapter);
        }
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
        if (emptyView != null) {
            mAdapter.setEmptyView(emptyView);
        }
    }

    protected void initRecyclerView() {
        try {
            mRecyclerView = findViewById(getRecyclerId());
            if (mRecyclerView != null) {
                mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void initSwiprefresh() {
        try {
            swipeRefreshLayout = findViewById(getSwipeLayoutId());
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
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
        getData();
    }

    public void setRequestListData(boolean requestListData) {
        isRequestListData = requestListData;
    }
    @Override
    public void getDataFromServer() {

    }
    /**
     * 加载更多
     */
    protected void loadMore() {
        page++;
        getData();
    }

    private void getData() {
        Observable<List<D>> observable = getHttpListObservable();
        if (observable == null) return;
        observable.subscribe(new BaseHandleObserver<List<D>>() {

            @Override
            public void onNext(List<D> datas) {
                if (page == 1 || (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing())) {
                    mAdapter.getData().clear();
                }

                if (datas != null && !datas.isEmpty()) {
                    mAdapter.addData(datas);
                    mAdapter.loadMoreComplete();
                    if (mAdapter.getData().size() < getPageSize()) {
                        mAdapter.loadMoreEnd();
                    }

                } else {
                    if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
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
                if (e instanceof SocketTimeoutException) { // 连接超时
                    emptyView.setEmptyType(EmptyView.NETWORK_ERROR);
                } else if (e instanceof JsonParseException) {// 数据解析异常
                    emptyView.setEmptyType(EmptyView.NO_DATA);
                } else if (e instanceof UnknownHostException || e instanceof ConnectException) {// 无网络
                    emptyView.setEmptyType(EmptyView.NETWORK_ERROR);
                } else {//其他错误
                    emptyView.setEmptyType(EmptyView.NO_DATA);
                }
            }

            @Override
            public void onComplete() {
                super.onComplete();
                if (swipeRefreshLayout != null && swipeRefreshLayout.isRefreshing()) {
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

    protected void enableRefresh(boolean enable) {
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setEnabled(enable);
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

    public void setPageSize(BaseResult<JSONObject> objectBaseResult) {
        this.pageSize = getJsonPage(objectBaseResult);
    }

    protected int getJsonPage(BaseResult<JSONObject> objectBaseResult) {
        try {
           // return objectBaseResult.data.getInteger("pageSize");
            return objectBaseResult.data.getInteger("pageSize");
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
