package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.PlatformshoppingcartAdapter;
import com.zskjprojectj.andouclient.adapter.mall.MallShoppingAdapter;
import com.zskjprojectj.andouclient.base.BaseFragment;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingbean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.utils.TestUtil;
import com.zskjprojectj.andouclient.view.TopView;

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
                TestUtil.getUid(),
                TestUtil.getToken()
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
