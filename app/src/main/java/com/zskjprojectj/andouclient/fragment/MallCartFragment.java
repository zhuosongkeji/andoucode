package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MyAddressActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.PlatformshoppingcartAdapter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.utils.ArrayParamUtil;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PayCancle;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MallCartFragment extends BaseFragment {
    private RecyclerView mRecycler;

    @BindView(R.id.tv_shipping_all_price)
    TextView mShippingAllPrice;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;
    @BindView(R.id.selectAllBtn)
    View selectAllBtn;

    PlatformshoppingcartAdapter adapter = new PlatformshoppingcartAdapter();
    private PageLoadUtil<CartItem> pageLoadUtil;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRecycler = view.findViewById(R.id.recyclerView);
        mRecycler.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemChildClickListener((adapter1, view1, position) -> {
            CartItem item = adapter.getItem(position);
            if (view1.getId() == R.id.deleteBtn) {
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().delCart(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                ArrayParamUtil.getParam(new String[]{item.id})
                        ), result -> {
                            ToastUtil.showToast("删除成功");
                            adapter.remove(position);
                            resetAmount();
                        });
            } else if (view1.getId() == R.id.btn_add) {
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().editCart(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                item.id,
                                "1"
                        ), result -> {
                            item.num++;
                            adapter.notifyItemChanged(position);
                            resetAmount();
                        });
            } else if (view1.getId() == R.id.btn_sub && item.num > 1) {
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().editCart(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                item.id,
                                "0"
                        ), result -> {
                            item.num--;
                            adapter.notifyItemChanged(position);
                            resetAmount();
                        });
            } else if (view1.getId() == R.id.cb_selectorcb1) {
                adapter.setSelected(item, !adapter.isSelect(item));
                resetAmount();
                selectAllBtn.setSelected(adapter.isSelectedAll);
            }
        });
        getDataFromServer();
        //1、注册广播
        EventBus.getDefault().register(this);
    }


    private void getDataFromServer() {
        pageLoadUtil = PageLoadUtil.get((BaseActivity) getActivity(), mRecycler, adapter, mRefreshLayout);
        if (TextUtils.isEmpty(LoginInfoUtil.getToken())) {
            return;
        }
        pageLoadUtil.load(() -> ApiUtils.getApiService().cart(
                LoginInfoUtil.getUid(),
                LoginInfoUtil.getToken(),
                pageLoadUtil.page), (refresh, result) -> {
            if (adapter.getData().size() > 0) {
                view.findViewById(R.id.pay_all).setVisibility(View.VISIBLE);
            } else {
                view.findViewById(R.id.pay_all).setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //2、解除注册
        EventBus.getDefault().unregister(this);
    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void intentEventBus(PaySuccessEvent paySuccessEvent) {
        pageLoadUtil.refresh();
    }

    //5.接收消息
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void backEventBus(PayCancle payCancle) {

        pageLoadUtil.refresh();
        if (selectAllBtn.isSelected()) {
            selectAllBtn.performClick();
        }
    }


    @OnClick({R.id.selectAllBtn, R.id.btn_settleaccounts})
    public void clickSelectorAll(View view) {

        switch (view.getId()) {
            case R.id.selectAllBtn:
                if (!adapter.isSelectedAll) {
                    adapter.setSelectedAll(true);
                    resetAmount();
                } else {
                    adapter.setSelectedAll(false);
                    mShippingAllPrice.setText("¥" + 0);
                }
                view.setSelected(adapter.isSelectedAll);
                break;
            case R.id.btn_settleaccounts:
                List<String> strings = getSelectedIds();
                StringBuilder result = new StringBuilder();
                boolean first = true;
                //第一个前面不拼接","
                for (String string : strings) {
                    if (first) {
                        first = false;
                    } else {
                        result.append(",");
                    }
                    result.append(string);
                }

                String jsonCarId = result.toString();
                RequestUtil.request(mActivity, true, false,
                        () -> ApiUtils.getApiService().OrderBuyCar(
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                jsonCarId
                        ), result1 -> MallOnlineOrderActivity.start(result1.data.getOrder_sn(), "", "", ""),
                        msg -> {
                            if ("请填写收货地址".equals(msg)) {
                                startActivity(new Intent(mActivity, MyAddressActivity.class));
                            }
                        });
                break;
        }

    }

    private List<String> getSelectedIds() {
        ArrayList<String> ids = new ArrayList<>();
        for (CartItem selectedItem : adapter.getSelectedItems()) {
            ids.add(selectedItem.id);
        }
        return ids;
    }

    private void resetAmount() {
        BigDecimal allPrice = new BigDecimal(0);
        for (CartItem datum : adapter.getSelectedItems()) {
            BigDecimal price = new BigDecimal(datum.num + "");
            allPrice = allPrice.add(price.multiply(new BigDecimal(datum.price)));
        }
        mShippingAllPrice.setText("¥" + allPrice.toString());
    }


    @Override
    protected int getContentView() {
        return R.layout.fragment_mall_cart;
    }
}
