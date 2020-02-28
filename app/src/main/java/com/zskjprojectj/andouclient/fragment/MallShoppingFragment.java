package com.zskjprojectj.andouclient.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.app.BaseFragment;
import com.zhuosongkj.android.library.util.PageLoadUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.activity.MyAddressActivity;
import com.zskjprojectj.andouclient.activity.mall.MallOnlineOrderActivity;
import com.zskjprojectj.andouclient.adapter.PlatformshoppingcartAdapter;
import com.zskjprojectj.andouclient.entity.mall.MallCarBean;
import com.zskjprojectj.andouclient.entity.mall.MallShoppingbean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.CartItem;
import com.zskjprojectj.andouclient.utils.ArrayParamUtil;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.PayCancle;
import com.zskjprojectj.andouclient.utils.PaySuccessEvent;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.math.BigDecimal;
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

    @BindView(R.id.tv_shipping_all_price)
    TextView mShippingAllPrice;



    @BindView(R.id.cb_selectorcb)
    AppCompatCheckBox mCheckBox;

    @BindView(R.id.refreshLayout)
    SmartRefreshLayout mRefreshLayout;

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
                HttpRxObservable.getObservable(ApiUtils.getApiService().delCart(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        ArrayParamUtil.getParam(new String[]{item.id})
                )).subscribe(new BaseObserver<Object>(mActivity) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("删除成功");
                        adapter.remove(position);
                        resetAmount();
                    }
                });
            } else if (view1.getId() == R.id.btn_add) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().editCart(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        item.id,
                        "1"
                )).subscribe(new BaseObserver<Object>(mActivity) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        item.num++;
                        adapter.notifyItemChanged(position);
                        resetAmount();
                    }
                });
            } else if (view1.getId() == R.id.btn_sub && item.num > 1) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().editCart(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        item.id,
                        "0"
                )).subscribe(new BaseObserver<Object>(mActivity) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        item.num--;
                        adapter.notifyItemChanged(position);
                        resetAmount();
                    }
                });
            } else if (view1.getId() == R.id.cb_selectorcb1) {
                adapter.setSelected(item, !adapter.isSelect(item));
                resetAmount();
                mCheckBox.setSelected(adapter.isSelectedAll);
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
                pageLoadUtil.page));
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
        if (mCheckBox.isSelected()){
            mCheckBox.performClick();
        }
    }


    @OnClick({R.id.cb_selectorcb, R.id.btn_settleaccounts})
    public void clickSelectorAll(View view) {

        switch (view.getId()) {
            case R.id.cb_selectorcb:
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
//                JSONArray jsonArray=new JSONArray(strings);
//                String jsonCarId = jsonArray.toString();

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

                HttpRxObservable.getObservable(ApiUtils.getApiService().OrderBuyCar(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        jsonCarId
                )).subscribe(new BaseObserver<MallCarBean>(mActivity) {

                    @Override
                    public void onHandleSuccess(MallCarBean mallCarBean) throws IOException {
                        String order_sn = mallCarBean.getOrder_sn();

                        MallOnlineOrderActivity.start(order_sn,"","","");

                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if ("请填写收货地址".equals(e.getMessage())){
                            startActivity(new Intent(mActivity, MyAddressActivity.class));
                        }

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
        return R.layout.fragment_focuson;
    }
}
