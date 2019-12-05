package com.zskjprojectj.andouclient.activity;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.LiveAdapter;
import com.zskjprojectj.andouclient.base.BaseListActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.TestBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseResult;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 外卖板块
 */
public class BookingorderActivity extends BaseListActivity {
    @Override
    protected void findView() {
        topView.setTitle("外卖");
    }

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_bookingorder);
    }

    @Override
    protected Observable<List> getHttpListObservable() {
        String key="3279cde306e48687bc89cc63e392a135";
        String menu="西红柿";

        return HttpRxObservable.getObservable(ApiUtils.getApiService().getinfo(key, menu), this).map(new Function<BaseResult<TestBean.ResultBean>, List>() {
            @Override
            public List apply(BaseResult<TestBean.ResultBean> resultBeanBaseResult) throws Exception {

                return resultBeanBaseResult.getResult().getData();
            }

        });
    }

    @Override
    protected BaseQuickAdapter createAdapter(List mDatas) {
        LiveAdapter liveAdapter=new LiveAdapter( mDatas);
        return liveAdapter;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
