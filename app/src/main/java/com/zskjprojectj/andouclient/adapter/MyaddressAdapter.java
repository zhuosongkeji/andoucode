package com.zskjprojectj.andouclient.adapter;

import android.app.Activity;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Address;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.io.IOException;

public class MyaddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder> {
    public MyaddressAdapter() {
        super(R.layout.item_myaddress);
    }

    @Override
    protected void convert(BaseViewHolder helper, Address item) {
        helper.setText(R.id.nameTxt, item.name)
                .setText(R.id.mobileTxt, item.mobile)
                .setText(R.id.addressTxt, item.province + item.city + item.area + item.address)
//                .setVisible(R.id.defaultCkb, !item.is_default.equals("1"))
                .setOnCheckedChangeListener(R.id.defaultCbx, null)
                .setChecked(R.id.defaultCbx, item.is_default.equals("1"))
                .setOnCheckedChangeListener(R.id.defaultCbx, (buttonView, isChecked) ->
                        HttpRxObservable.getObservable(ApiUtils.getApiService().defaultAddress(
                                item.id,
                                LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken()))
                                .subscribe(new BaseObserver<Object>((Activity) helper.itemView.getContext()) {
                                    @Override
                                    public void onHandleSuccess(Object o) throws IOException {
                                        for (Address address : getData()) {
                                            address.is_default = "0";
                                        }
                                        item.is_default = "1";
                                        notifyDataSetChanged();
                                    }
                                }))
                .addOnClickListener(R.id.editBtn)
                .addOnClickListener(R.id.deleteBtn);
    }
}
