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
import com.blankj.utilcode.util.ToastUtils
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.ADProvince
import com.zskjprojectj.andouclient.model.Address
import com.zskjprojectj.andouclient.model.AddressIn
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.view.AddressBottomDialog
import kotlinx.android.synthetic.main.activity_new_address.*
import java.util.*

class NewAddressActivity : BaseActivity() {
    val adProvincess: MutableList<ADProvince> = ArrayList()
    var selectedAddress: AddressIn? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val editAddress = intent.getSerializableExtra(KEY_DATA) as? Address
        if (editAddress == null) {
            ActionBarUtil.setTitle(mActivity, "新增地址")
        } else {
            ActionBarUtil.setTitle(mActivity, "修改地址")
            nameEdt.setText(editAddress.name)
            mobileEdt.setText(editAddress.mobile)
            detailEdt.setText(editAddress.address)
            defaultCbx.isChecked = editAddress.is_defualt == "1"
//            val province = Province()
//            province.id = editAddress.province.
//            province.name = poiItem.provinceName
//            val city = City()
//            city.id = poiItem.cityCode.toInt()
//            city.name = poiItem.cityName
//            val county = County()
//            county.id = poiItem.adCode.toInt()
//            county.name = poiItem.adName
//            selectedAddress = AddressIn(province, city, county)
        }
        ry_selectaddress.setOnClickListener {
            val dialog = AddressBottomDialog.show(mActivity)
            dialog.setTitles("收货地址")
            dialog.setAddressProvider(object : AddressProvider {
                override fun provideProvinces(addressReceiver: AddressReceiver<Province>) {
                    val provinces = ArrayList<Province>()
                    for (adProvince in adProvincess) {
                        val province = Province()
                        province.id = adProvince.id
                        province.name = adProvince.name
                        provinces.add(province)
                    }
                    addressReceiver.send(provinces)
                }

                override fun provideCitiesWith(provinceId: Int, addressReceiver: AddressReceiver<City>) {
                    val cities = ArrayList<City>()
                    for (adProvince in adProvincess) {
                        if (adProvince.id == provinceId) {
                            for (adCity in adProvince.cities) {
                                val city = City()
                                city.id = adCity.id
                                city.name = adCity.name
                                city.province_id = adCity.pid
                                cities.add(city)
                            }
                            break
                        }
                    }
                    addressReceiver.send(cities)
                }

                override fun provideCountiesWith(cityId: Int, addressReceiver: AddressReceiver<County>) {
                    val counties = ArrayList<County>()
                    for (adProvince in adProvincess) {
                        for (adCity in adProvince.cities) {
                            if (adCity.id == cityId) {
                                for (adArea in adCity.getAreas()) {
                                    val county = County()
                                    county.id = adArea.id
                                    county.name = adArea.name
                                    county.city_id = adArea.pid
                                    counties.add(county)
                                }
                                break
                            }
                        }
                    }
                    addressReceiver.send(counties)
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
            val detailStr = detailEdt.text.toString()
            when {
                nameStr.isEmpty() -> ToastUtils.showShort("收件人不能为空!")
                mobileStr.isEmpty() -> ToastUtils.showShort("手机号不能为空!")
                detailStr.isEmpty() -> ToastUtils.showShort("详细地址不能为空!")
                selectedAddress == null -> ToastUtils.showShort("请选择省市区!")
                else -> {
                    if (editAddress == null) {
                        RequestUtil.request(mActivity, true, false,
                                {
                                    ApiUtils.getApiService().addAddress(
                                            LoginInfoUtil.getUid(),
                                            LoginInfoUtil.getToken()
                                            , nameStr
                                            , mobileStr
                                            , selectedAddress?.province?.id.toString()
                                            , selectedAddress?.city?.id.toString()
                                            , selectedAddress?.county?.id.toString()
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
                                            editAddress.id,
                                            LoginInfoUtil.getUid(),
                                            LoginInfoUtil.getToken()
                                            , nameStr
                                            , mobileStr
                                            , selectedAddress?.province?.id.toString()
                                            , selectedAddress?.city?.id.toString()
                                            , selectedAddress?.county?.id.toString()
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
        RequestUtil.request(mActivity, true, true,
                { ApiUtils.getApiService().districts() },
                { adProvincess.addAll(it.data) })
    }

    @SuppressLint("SetTextI18n")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 666 && resultCode == Activity.RESULT_OK && data != null) {
            val poiItem = data.getParcelableExtra<PoiItem>(KEY_DATA)
            detailEdt.setText(poiItem.snippet + poiItem.title)
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
        fun start(activity: Activity, address: Address?, requestCode: Int) {
            ActivityUtils.startActivityForResult(
                    bundleOf(Pair(KEY_DATA, address)),
                    activity,
                    NewAddressActivity::class.java,
                    requestCode)
        }
    }
}