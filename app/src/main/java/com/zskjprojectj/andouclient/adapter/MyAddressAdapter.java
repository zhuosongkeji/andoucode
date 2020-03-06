package com.zskjprojectj.andouclient.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.RequestUtil;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Address;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;

public class MyAddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder> {
    public MyAddressAdapter() {
        super(R.layout.item_myaddress);
    }

    @Override
    protected void convert(BaseViewHolder helper, Address item) {
        helper.setText(R.id.nameTxt, item.name)
                .setText(R.id.mobileTxt, item.mobile)
                .setText(R.id.addressTxt, item.province + item.city + item.area + item.address)
                .setOnCheckedChangeListener(R.id.defaultCbx, null)
                .setChecked(R.id.defaultCbx, item.is_defualt.equals("1"))
                .setOnCheckedChangeListener(R.id.defaultCbx, (buttonView, isChecked) ->
                        RequestUtil.request((BaseActivity) mContext, true, false,
                                () -> ApiUtils.getApiService().defaultAddress(
                                        item.id,
                                        LoginInfoUtil.getUid(),
                                        LoginInfoUtil.getToken()),
                                result -> {
                                    for (Address address : getData()) {
                                        address.is_defualt = "0";
                                    }
                                    item.is_defualt = "1";
                                    notifyDataSetChanged();
                                }))
                .addOnClickListener(R.id.editBtn)
                .addOnClickListener(R.id.deleteBtn);
    }
}
