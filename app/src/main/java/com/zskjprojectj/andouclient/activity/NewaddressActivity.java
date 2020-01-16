package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.ADArea;
import com.zskjprojectj.andouclient.model.ADCity;
import com.zskjprojectj.andouclient.model.ADProvince;
import com.zskjprojectj.andouclient.model.Address;
import com.zskjprojectj.andouclient.model.AddressIn;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.view.AddressBottomDialog;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import chihane.jdaddressselector.AddressProvider;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;

import static com.zskjprojectj.andouclient.activity.MyaddressActivity.KEY_DATA;

/**
 * 新增地址
 */
public class NewaddressActivity extends BaseActivity {
    private RelativeLayout ry_selectaddress;
    final List<ADProvince> adProvincess = new ArrayList<>();
    @BindView(R.id.tv_showadress)
    TextView addressTxt;
    AddressIn addresss;
    @BindView(R.id.header_title_view)
    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_newaddress);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        getBarDistance(mTitleView);

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
           // topView.setTitle("新增地址");
            mHeaderTitle.setText("新增地址");
        } else {
            //topView.setTitle("修改地址");
            mHeaderTitle.setText("修改地址");
            nameEdt.setText(address.name);
            mobileEdt.setText(address.mobile);
            detailEdt.setText(address.address);
            defaultCbx.setChecked(address.is_defualt.equals("1"));
        }
        HttpRxObservable.getObservable(ApiUtils.getApiService().districts()).subscribe(new BaseObserver<List<ADProvince>>(mAt) {
            @Override
            public void onHandleSuccess(List<ADProvince> adProvinces) throws IOException {
                adProvincess.addAll(adProvinces);
            }
        });
       // ry_selectaddress.setOnClickListener(view -> jumpActivity(ShareLocationActivity.class));
        ry_selectaddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddressBottomDialog dialog = AddressBottomDialog.show(mAt);
                dialog.setAddressProvider(new AddressProvider() {
                    @Override
                    public void provideProvinces(AddressReceiver<Province> addressReceiver) {
                        ArrayList<Province> provinces = new ArrayList<>();
                        for (ADProvince adProvince : adProvincess) {
                            Province province = new Province();
                            province.id = adProvince.id;
                            province.name = adProvince.name;
                            provinces.add(province);
                        }
                        addressReceiver.send(provinces);
                    }

                    @Override
                    public void provideCitiesWith(int provinceId, AddressReceiver<City> addressReceiver) {
                        ArrayList<City> cities = new ArrayList<>();
                        for (ADProvince adProvince : adProvincess) {
                            if (adProvince.id == provinceId) {
                                for (ADCity adCity : adProvince.cities) {
                                    City city = new City();
                                    city.id = adCity.id;
                                    city.name = adCity.name;
                                    city.province_id = adCity.pid;
                                    cities.add(city);
                                }
                                break;
                            }
                        }
                        addressReceiver.send(cities);
                    }

                    @Override
                    public void provideCountiesWith(int cityId, AddressReceiver<County> addressReceiver) {
                        ArrayList<County> counties = new ArrayList<>();
                        for (ADProvince adProvince : adProvincess) {
                            for (ADCity adCity : adProvince.cities) {
                                if (adCity.id == cityId) {
                                    for (ADArea adArea : adCity.getAreas()) {
                                        County county = new County();
                                        county.id = adArea.id;
                                        county.name = adArea.name;
                                        county.city_id = adArea.pid;
                                        counties.add(county);
                                    }
                                    break;
                                }
                            }
                        }
                        addressReceiver.send(counties);
                    }

                    @Override
                    public void provideStreetsWith(int countyId, AddressReceiver<Street> addressReceiver) {
                        addressReceiver.send(null);
                    }
                });
                dialog.setOnAddressSelectedListener((province, city, county, street) -> {
                    addresss = new AddressIn(province, city, county);
                    addressTxt.setText(addresss.toString());
                    dialog.dismiss();
                });
            }
        });
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
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken()
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
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken()
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
