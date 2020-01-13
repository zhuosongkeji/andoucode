package com.zskjprojectj.andouclient.activity;


import android.view.View;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.zhuosongkj.android.library.model.BaseResult;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.LiveAdapter;
import com.zskjprojectj.andouclient.base.BaseListActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.TestBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 直播界面
 */
public class LiveActivity extends BaseListActivity {
    @Override
    protected void findView() {
        topView.setTitle("直播中心");
    }

    protected void setRootView() {
        setContentView(R.layout.activity_live);
    }

    @Override
    protected Observable<List> getHttpListObservable() {
        String key="3279cde306e48687bc89cc63e392a135";
        String menu="西红柿";
        return HttpRxObservable.getObservable(ApiUtils.getApiService().getinfo(key, menu), this).map(new Function<BaseResult<TestBean.ResultBean>, List>() {
            @Override
            public List apply(BaseResult<TestBean.ResultBean> resultBeanBaseResult) throws Exception {

                return resultBeanBaseResult.data.getData();
            }

        });
    }
    @Override
    protected BaseQuickAdapter createAdapter(List mDatas) {
        LiveAdapter liveAdapter=new LiveAdapter(mDatas);
        return liveAdapter;
    }

    /**
     * 设置列表样式
     * spancount表示几行
     */
    @Override
    protected void initRecyclerView() {
        super.initRecyclerView();
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        if (getRecyclerView() != null)
            getRecyclerView().setLayoutManager(manager);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtil.showToast("======>"+position);
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
