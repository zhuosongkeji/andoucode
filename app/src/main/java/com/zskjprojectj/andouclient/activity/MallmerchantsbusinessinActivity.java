package com.zskjprojectj.andouclient.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.blankj.utilcode.util.ActivityUtils;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureMimeType;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseObserver;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.model.ADArea;
import com.zskjprojectj.andouclient.model.ADCity;
import com.zskjprojectj.andouclient.model.ADProvince;
import com.zskjprojectj.andouclient.model.AddressIn;
import com.zskjprojectj.andouclient.model.User;
import com.zskjprojectj.andouclient.model.UserIn;
import com.zskjprojectj.andouclient.utils.ActionBarUtil;
import com.zskjprojectj.andouclient.utils.BitmapUtil;
import com.zskjprojectj.andouclient.utils.GlideEngine;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;
import com.zskjprojectj.andouclient.utils.ToastUtil;
import com.zskjprojectj.andouclient.view.AddressBottomDialog;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import chihane.jdaddressselector.AddressProvider;
import chihane.jdaddressselector.model.City;
import chihane.jdaddressselector.model.County;
import chihane.jdaddressselector.model.Province;
import chihane.jdaddressselector.model.Street;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.zskjprojectj.andouclient.model.UserIn.Role.KEY_TYPE;

/**
 * 商城商家入驻
 */

public class MallmerchantsbusinessinActivity extends BaseActivity {
    private static final int REQUEST_CODE_SELECT_LOGO = 666;
    private static final int REQUEST_CODE_SELECT_BANNER = 667;
    private static final int REQUEST_CODE_SELECT_LICENSE = 668;
//    @BindView(R.id.header_title_view)
//    RelativeLayout mTitleView;
    @BindView(R.id.tv_header_title)
    TextView mHeaderTitle;

    @BindView(R.id.nameEdt)
    EditText nameEdt;

    @BindView(R.id.contactNameEdt)
    EditText contactNameEdt;

    @BindView(R.id.contactMobileEdt)
    EditText contactMobileEdt;

    @BindView(R.id.addressTxt)
    TextView addressTxt;

    @BindView(R.id.addressDetailEdt)
    EditText addressDetailEdt;

    @BindView(R.id.descriptionEdt)
    EditText descriptionEdt;

    @BindView(R.id.logoImg)
    ImageView logoImg;
    @BindView(R.id.bannerImg)
    ImageView bannerImg;
    @BindView(R.id.licenseImg)
    ImageView licenseImg;

    AddressIn address;
    int type;

    final List<ADProvince> adProvincess = new ArrayList<>();
    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_mallmerchantsbusinessin);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
     // getBarDistance(mTitleView);
   //  mHeaderTitle.setText("商城商家入驻");
    }

    @Override
    protected void initViews() {
          type = getIntent().getIntExtra(KEY_TYPE, UserIn.Role.Type.MALL.typeInt);
        if (type == UserIn.Role.Type.HOTEL.typeInt) {
            mHeaderTitle.setText("酒店商家入驻");
        } else if (type == UserIn.Role.Type.MALL.typeInt) {
           // ActionBarUtil.setTitle(MallmerchantsbusinessinActivity.this, "商城商家入驻");
            mHeaderTitle.setText("商城商家入驻");
        } else if (type == UserIn.Role.Type.RESTAURANT.typeInt) {

            mHeaderTitle.setText("饭店商家入驻");
        }
       HttpRxObservable.getObservable(ApiUtils.getApiService().districts()).subscribe(new BaseObserver<List<ADProvince>>(mAt) {
           @Override
           public void onHandleSuccess(List<ADProvince> adProvinces) throws IOException {
               adProvincess.addAll(adProvinces);
           }
       });
        addressTxt.setOnClickListener(v -> {
            AddressBottomDialog dialog = AddressBottomDialog.show(this);
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
                address = new AddressIn(province, city, county);
                addressTxt.setText(address.toString());
                dialog.dismiss();
            });
        });
        logoImg.setOnClickListener(v -> startSelectPic(REQUEST_CODE_SELECT_LOGO));
        bannerImg.setOnClickListener(v -> startSelectPic(REQUEST_CODE_SELECT_BANNER));
        licenseImg.setOnClickListener(v -> startSelectPic(REQUEST_CODE_SELECT_LICENSE));
    }
    private void startSelectPic(int requestCode) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .isCamera(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(requestCode);
    }
    @Override
    public void getDataFromServer() {

    }
    @OnClick(R.id.confirmBtn)
    void onConfirmBtn()
    {
        String name = nameEdt.getText().toString();
        String contactName = contactNameEdt.getText().toString();
        String contactMobile = contactMobileEdt.getText().toString();
        String addressStr = addressTxt.getText().toString();
        String addressDetail = addressDetailEdt.getText().toString();
        String description = descriptionEdt.getText().toString();
        if (TextUtils.isEmpty(name)) {
            ToastUtil.showToast("请输入商户名称!");
        } else if (TextUtils.isEmpty(contactName)) {
            ToastUtil.showToast("请输入联系人!");
        } else if (TextUtils.isEmpty(contactMobile)) {
            ToastUtil.showToast("请输入联系电话!");
        } else if (TextUtils.isEmpty(contactMobile)) {
            ToastUtil.showToast("请输入联系电话!");
        } else if (TextUtils.isEmpty(addressStr)) {
            ToastUtil.showToast("请选择店铺地址!");
        } else if (TextUtils.isEmpty(addressDetail)) {
            ToastUtil.showToast("请选择详细地址!");
        } else if (TextUtils.isEmpty(description)) {
            ToastUtil.showToast("请选择商家简介!");
        } else if (TextUtils.isEmpty((String) logoImg.getTag())) {
            ToastUtil.showToast("请添加商家Logo图!");
        } else if (TextUtils.isEmpty((String) bannerImg.getTag())) {
            ToastUtil.showToast("请添加商家海报图!");
        } else if (TextUtils.isEmpty((String) licenseImg.getTag())) {
            ToastUtil.showToast("请添加营业执照!");
        } else {
                HttpRxObservable.getObservable(ApiUtils.getApiService().uploadMerchantsInfo(  LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        type,
                        name,
                        contactName,
                        contactMobile,
                        address.province.id,
                        address.city.id,
                        address.county.id,
                        addressStr,
                        description,
                        (String) bannerImg.getTag(),
                        (String) logoImg.getTag(),
                        (String) licenseImg.getTag())).subscribe(new BaseObserver<Object>(mAt) {
                    @Override
                    public void onHandleSuccess(Object o) throws IOException {
                        jumpActivity(ApplyforsuccessfulActivity.class);
                        finish();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                    }
                });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) return;
        String path = PictureSelector.obtainMultipleResult(data).get(0).getAndroidQToPath();
        if (TextUtils.isEmpty(path)) {
            path = PictureSelector.obtainMultipleResult(data).get(0).getPath();
        }
        ImageView imageView = null;
        switch (requestCode) {
            case REQUEST_CODE_SELECT_LOGO:
                imageView = logoImg;
                break;
            case REQUEST_CODE_SELECT_BANNER:
                imageView = bannerImg;
                break;
            case REQUEST_CODE_SELECT_LICENSE:
                imageView = licenseImg;
                break;
        }
//        imageView.setTag(path);
//        BitmapUtil.recycle(imageView);
//        imageView.setImageBitmap(BitmapFactory.decodeFile(path));
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpg"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("img", file.getName(), requestFile);
        RequestBody uid = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getUid());
        RequestBody token = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getToken());
        ImageView finalImageView = imageView;
        String finalPath = path;
        HttpRxObservable.getObservable(ApiUtils.getApiService().uploadImg(uid,token,body)).subscribe(new BaseObserver<String>(mAt) {

            @Override
            public void onHandleSuccess(String s) throws IOException {
                finalImageView.setTag(s);
                BitmapUtil.recycle(finalImageView);
                finalImageView.setImageBitmap(BitmapFactory.decodeFile(finalPath));
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
            }
       });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        BitmapUtil.recycle(logoImg);
        BitmapUtil.recycle(bannerImg);
        BitmapUtil.recycle(licenseImg);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    public static void start(Activity activity, UserIn.Role.Type type, int requestCode) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE, type.typeInt);
        ActivityUtils.startActivityForResult(bundle, activity, MallmerchantsbusinessinActivity.class, requestCode);
    }
    @OnClick(R.id.iv_header_back)
    public void clickView() {
        finish();
    }
}
