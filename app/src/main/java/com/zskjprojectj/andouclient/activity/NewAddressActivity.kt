package com.zskjprojectj.andouclient.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.os.bundleOf
import chihane.jdaddressselector.AddressProvider
import chihane.jdaddressselector.AddressProvider.AddressReceiver
import chihane.jdaddressselector.model.City
import chihane.jdaddressselector.model.County
import chihane.jdaddressselector.model.Province
import chihane.jdaddressselector.model.Street
import com.amap.api.services.core.PoiItem
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.RegexUtils
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.AddressIn
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.view.AddressBottomDialog
import kotlinx.android.synthetic.main.activity_new_address.*
import java.util.*

class NewAddressActivity : BaseActivity() {
    var selectedAddress: AddressIn? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val id = intent.getStringExtra(KEY_DATA)
        if (id == null) {
            ActionBarUtil.setTitle(mActivity, "新增地址")
        } else {
            ActionBarUtil.setTitle(mActivity, "修改地址")
            RequestUtil.request(mActivity, true, true, {
                ApiUtils.getApiService().addressDetail(
                        LoginInfoUtil.getUid(),
                        LoginInfoUtil.getToken(),
                        id
                )
            }, {
                nameEdt.setText(it.data?.name)
                mobileEdt.setText(it.data?.mobile)
                addressDetailEdt.setText(it.data?.address)
                defaultCbx.isChecked = it.data?.is_defualt == "1"
                val province = Province()
                province.id = it.data?.province_id?.toInt() ?: 0
                province.name = it.data?.province
                val city = City()
                city.id = it.data?.city_id?.toInt() ?: 0
                city.name = it.data?.city
                val county = County()
                county.id = it.data?.area_id?.toInt() ?: 0
                county.name = it.data?.area
                selectedAddress = AddressIn(province, city, county)
                addressTxt.text = selectedAddress.toString()
            })
        }
        selectDistrictBtn.setOnClickListener {
            val dialog = AddressBottomDialog.show(mActivity)
            dialog.setTitles("收货地址")
            dialog.setAddressProvider(object : AddressProvider {
                override fun provideProvinces(addressReceiver: AddressReceiver<Province>) {
                    RequestUtil.request(mActivity, false, false,
                            { ApiUtils.getApiService().districts() },
                            {
                                val provinces = ArrayList<Province>()
                                it.data?.forEach { district ->
                                    val province = Province()
                                    province.id = district.id
                                    province.name = district.name
                                    provinces.add(province)
                                }
                                addressReceiver.send(provinces)
                            })
                }

                override fun provideCitiesWith(provinceId: Int, addressReceiver: AddressReceiver<City>) {
                    RequestUtil.request(mActivity, false, false,
                            { ApiUtils.getApiService().districts(provinceId) },
                            {
                                val cities = ArrayList<City>()
                                it.data?.forEach { district ->
                                    val city = City()
                                    city.id = district.id
                                    city.name = district.name
                                    cities.add(city)
                                }
                                addressReceiver.send(cities)
                            })
                }

                override fun provideCountiesWith(cityId: Int, addressReceiver: AddressReceiver<County>) {
                    RequestUtil.request(mActivity, false, false,
                            { ApiUtils.getApiService().districts(cityId) },
                            {
                                val counties = ArrayList<County>()
                                it.data?.forEach { district ->
                                    val county = County()
                                    county.id = district.id
                                    county.name = district.name
                                    counties.add(county)
                                }
                                addressReceiver.send(counties)
                            })
                }

                override fun provideStreetsWith(countyId: Int, addressReceiver: AddressReceiver<Street>) {
                    addressReceiver.send(null)
                }
            })
            dialog.setOnAddressSelectedListener { province: Province?, city: City?, county: County?, street: Street? ->
                selectedAddress = AddressIn(province, city, county)
                addressTxt.text = selectedAddress.toString()
                dialog.dismiss()
            }
        }
        selectLocationBtn.setOnClickListener {
            ActivityUtils.startActivityForResult(mActivity, SelectLocationActivity::class.java, 666)
        }
        saveBtn.setOnClickListener {
            val nameStr = nameEdt.text.toString()
            val mobileStr = mobileEdt.text.toString()
            val detailStr = addressDetailEdt.text.toString()
            when {
                nameStr.isEmpty() -> ToastUtils.showShort("收件人不能为空!")
                !RegexUtils.isMobileSimple(mobileStr) -> ToastUtils.showShort("手机号不正确!")
                detailStr.isEmpty() -> ToastUtils.showShort("详细地址不能为空!")
                selectedAddress == null -> ToastUtils.showShort("请选择省市区!")
                else -> {
                    val id = intent.getStringExtra(KEY_DATA)
                    if (id == null) {
                        RequestUtil.request(mActivity, true, false,
                                {
                                    ApiUtils.getApiService().addAddress(
                                            LoginInfoUtil.getUid(),
                                            LoginInfoUtil.getToken()
                                            , nameStr
                                            , mobileStr
                                            , selectedAddress?.county?.id?.toString()
                                            ?: selectedAddress?.city?.id.toString()
                                            , detailStr
                                            , if (defaultCbx.isChecked) "1" else "0"
                                    )
                                },
                                {
                                    ToastUtil.showToast("保存成功!")
                                    setResult(Activity.RESULT_OK)
                                    finish()
                                })
                    } else {
                        RequestUtil.request(mActivity, true, false,
                                {
                                    ApiUtils.getApiService().editAddress(
                                            id,
                                            LoginInfoUtil.getUid(),
                                            LoginInfoUtil.getToken()
                                            , nameStr
                                            , mobileStr
                                            , selectedAddress?.county?.id?.toString()
                                            ?: selectedAddress?.city?.id.toString()
                                            , detailStr
                                            , if (defaultCbx.isChecked) "1" else "0"
                                    )
                                },
                                {
                                    ToastUtil.showToast("保存成功!")
                                    setResult(Activity.RESULT_OK)
                                    finish()
                                })
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 666 && resultCode == Activity.RESULT_OK && data != null) {
            val poiItem = data.getParcelableExtra<PoiItem>(KEY_DATA) ?: return
            addressDetailEdt.setText(poiItem.snippet + poiItem.title)
            val province = Province()
            province.id = poiItem.provinceCode.toInt()
            province.name = poiItem.provinceName
            val city = City()
            city.id = poiItem.cityCode.toInt()
            city.name = poiItem.cityName
            val county = County()
            county.id = poiItem.adCode.toInt()
            county.name = poiItem.adName
            selectedAddress = AddressIn(province, city, county)
            addressTxt.text = selectedAddress.toString()
        }
    }

    override fun getContentView() = R.layout.activity_new_address

    companion object {
        fun start(activity: Activity, id: String?, requestCode: Int) {
            ActivityUtils.startActivityForResult(
                    bundleOf(Pair(KEY_DATA, id)),
                    activity,
                    NewAddressActivity::class.java,
                    requestCode)
        }
    }
}