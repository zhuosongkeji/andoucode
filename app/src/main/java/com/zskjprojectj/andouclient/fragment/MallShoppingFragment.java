package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.PlatformshoppingcartAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingbean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.utils.ArrayParamUtil;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 在线商城购物车
 */
public class MallShoppingFragment extends BaseFragment {
    private RecyclerView mRecycler;
    private ArrayList<MallShoppingbean> dataList;
    private Button btn_settleaccounts;

    @BindView(R.id.header_title_view)
    RelativeLayout mHeaderTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @BindView(R.id.iv_header_back)
    ImageView mHeaderBack;

    PlatformshoppingcartAdapter adapter = new PlatformshoppingcartAdapter();

    @Override
    protected void initViews(View view, Bundle savedInstanceState) {

        mHeaderTitle.setText("购物车");
        mHeaderBack.setVisibility(View.GONE);
        getBarDistance(mHeaderTitleView);
        mRecycler = view.findViewById(R.id.rv_recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.bindToRecyclerView(mRecycler);
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            CartItem item = adapter.getItem(position);
            if (view1.getId() == R.id.deleteBtn) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().delCart(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        ArrayParamUtil.getParam(new String[]{item.id})
                )).subscribe(new BaseObserver<Object>(mAty) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("删除成功");
                        adapter.remove(position);
                    }
                });
            } else if (view1.getId() == R.id.btn_add) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().editCart(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        item.id,
                        "1"
                )).subscribe(new BaseObserver<Object>(mAty) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        item.num++;
                        adapter.notifyItemChanged(position);
                    }
                });
            } else if (view1.getId() == R.id.btn_sub && item.num > 1) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().editCart(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        item.id,
                        "0"
                )).subscribe(new BaseObserver<Object>(mAty) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        item.num--;
                        adapter.notifyItemChanged(position);
                    }
                });
            }

        });
        btn_settleaccounts = view.findViewById(R.id.btn_settleaccounts);
        btn_settleaccounts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(), MallOnlineOrderActivity.class));
            }
        });
    }

    @Override
    protected int getContentViewRes() {
        return R.layout.fragment_focuson;
    }

    @Override
    protected void getDataFromServer() {
        HttpRxObservable.getObservable(ApiUtils.getApiService().cart(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken()
        )).subscribe(new BaseObserver<List<CartItem>>(getActivity()) {
            @Override
            public void onHandleSuccess(List<CartItem> cartItem) throws IOException {
                adapter.setNewData(cartItem);
            }
        });
    }

    @Override
    protected void initData() {


    }

    @OnClick(R.id.cb_selectorcb)
    public void clickSelectorAll() {

    }


}
