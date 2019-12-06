package com.zskjprojectj.andouclient.activity;

import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.wihaohao.PageGridView;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.LiveAdapter;
import com.zskjprojectj.andouclient.base.BaseListActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BookorderBean;
import com.zskjprojectj.andouclient.entity.TestBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseResult;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * 在线预约点餐
 */
public class OnlineBookingorderActivity extends BaseListActivity {
    List<BookorderBean> mList;
    private String[] proName = {"火锅","自助餐","甜品饮品","小吃快餐","西餐","韩式烤肉",
            "香锅烤鱼","海鲜","水果"};
    private int[] proicon = {R.mipmap.hg,R.mipmap.zzc,R.mipmap.tp,R.mipmap.xckc,R.mipmap.xc,R.mipmap.kr,
            R.mipmap.xgky,R.mipmap.hx,R.mipmap.hx};
    private PageGridView<BookorderBean> mPageGridView;
    @Override
    protected void findView() {
        mPageGridView =findViewById(R.id.vp_grid_view);
        initDatas();
        mPageGridView.setData(mList);
        mPageGridView.setData(mList);
        mPageGridView.setOnItemClickListener(new PageGridView.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
               // Toast.makeText(MainActivity.this,position+"",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initDatas() {
        mList=new ArrayList<>();
        for(int i=0;i<proName.length;i++){
            mList.add(new BookorderBean(proName[i],proicon[i]));
        }
    }

    @Override
    protected void setRootView() {
       setContentView(R.layout.activity_onlinebookingorder);
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
        LiveAdapter liveAdapter=new LiveAdapter(mDatas);
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
