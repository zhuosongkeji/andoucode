package com.zskjprojectj.andouclient.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.BalanceofprepaidPaywayAdapter;
import com.zskjprojectj.andouclient.adapter.BalancesubsidiaryAdapter;
import com.zskjprojectj.andouclient.adapter.mall.PayWaysAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.BalanceofprepaidpaywayBean;
import com.zskjprojectj.andouclient.entity.BalancesubsidiaryBean;
import com.zskjprojectj.andouclient.entity.mall.MallPayWaysBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 *余额充值
 */
public class BalanceofprepaidActivity extends BaseActivity {
    private Button btn_confirm;
    private RecyclerView mRecycler;
    private ArrayList<BalanceofprepaidpaywayBean> mDataList;
    @BindView(R.id.rv_recycler)
    RecyclerView mRvPayWAys;
    private String payId;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_balanceofprepaid);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        topView.setTitle("余额充值");
//        mDataList = new ArrayList<>();
//        for (int i=0;i<3;i++){
//            BalanceofprepaidpaywayBean databean=new BalanceofprepaidpaywayBean();
//            databean.setPaywayname("微信支付");
//            mDataList.add(databean);
//        }
//        BalanceofprepaidPaywayAdapter adapter=new BalanceofprepaidPaywayAdapter(R.layout.item_balanceofprepaidpayway,mDataList);
//        adapter.openLoadAnimation();
//        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
//            @Override
//            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
//                //showDialog();
//            }
//        });
//        mRecycler.addItemDecoration(new DividerItemDecoration(mAt,DividerItemDecoration.VERTICAL));
//        mRecycler.setAdapter(adapter);
    }

    @Override
    protected void initViews() {
        mRecycler=findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mAt));
        btn_confirm=findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                jumpActivity(MywalletActivity.class);
                finish();
            }
        });
    }

    @Override
    public void getDataFromServer() {
        //请求支付方式
        HttpRxObservable.getObservable(ApiUtils.getApiService().getMallPayWays()).subscribe(new BaseObserver<List<MallPayWaysBean>>(mAt) {
            @Override
            public void onHandleSuccess(List<MallPayWaysBean> mallPayWaysBeans) throws IOException {
                mRvPayWAys.setLayoutManager(new LinearLayoutManager(mAt));
                PayWaysAdapter adapter = new PayWaysAdapter(R.layout.pay_ways_item, mallPayWaysBeans);
                mRvPayWAys.addItemDecoration(new DividerItemDecoration(mAt, DividerItemDecoration.VERTICAL));
                mRvPayWAys.setAdapter(adapter);
                adapter.setItemPayWays(new PayWaysAdapter.ItemPayWays() {
                    @Override
                    public void getPayWays(String payWays, int position) {
                        payId = mallPayWaysBeans.get(position).getId();
                    }
                });
            }
        });
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
