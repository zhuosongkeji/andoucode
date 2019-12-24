package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.Address;
import com.zskjprojectj.andouclient.utils.TestUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.io.IOException;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

/**
 * 新增地址
 */
public class NewaddressActivity extends BaseActivity {
    private RelativeLayout ry_selectaddress;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_newaddress);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    @Override
    protected void initViews() {
        Address address = (Address) getIntent().getSerializableExtra(KEY_DATA);
        ry_selectaddress = findViewById(R.id.ry_selectaddress);
        EditText nameEdt = findViewById(R.id.nameEdt);
        EditText mobileEdt = findViewById(R.id.mobileEdt);
        EditText detailEdt = findViewById(R.id.detailEdt);
        CheckBox defaultCbx = findViewById(R.id.defaultCkb);
        if (address == null) {
            topView.setTitle("新增地址");
        } else {
            topView.setTitle("修改地址");
            nameEdt.setText(address.name);
            mobileEdt.setText(address.mobile);
            detailEdt.setText(address.address);
            defaultCbx.setChecked(address.is_defualt.equals("1"));
        }
        ry_selectaddress.setOnClickListener(view -> jumpActivity(ShareLocationActivity.class));
        findViewById(R.id.saveBtn).setOnClickListener(v -> {
            String nameStr = nameEdt.getText().toString().trim();
            String mobileStr = mobileEdt.getText().toString().trim();
            String detailStr = detailEdt.getText().toString().trim();
            if (nameStr.isEmpty()) {
                ToastUtil.showToast("收件人不能为空!");
                return;
            }
            if (mobileStr.isEmpty()) {
                ToastUtil.showToast("手机号不能为空!");
                return;
            }
            if (detailStr.isEmpty()) {
                ToastUtil.showToast("详细地址不能为空!");
                return;
            }
            if (address == null) {
                HttpRxObservable.getObservable(ApiUtils.getApiService().addAddress(
                        TestUtil.getUid(),
                        TestUtil.getToken()
                        , nameStr
                        , mobileStr
                        , "11"
                        , "1101"
                        , "110101"
                        , detailStr
                        , defaultCbx.isChecked() ? "1" : "0"
                )).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("保存成功!");
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });
            } else {
                HttpRxObservable.getObservable(ApiUtils.getApiService().editAddress(
                        address.id,
                        TestUtil.getUid(),
                        TestUtil.getToken()
                        , nameStr
                        , mobileStr
                        , "11"
                        , "1101"
                        , "110101"
                        , detailStr
                        , defaultCbx.isChecked() ? "1" : "0"
                )).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        ToastUtil.showToast("保存成功!");
                        setResult(Activity.RESULT_OK);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
}
