package com.zskjprojectj.andouclient.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import butterknife.OnClick
import chihane.jdaddressselector.AddressProvider
import chihane.jdaddressselector.AddressProvider.AddressReceiver
import chihane.jdaddressselector.model.City
import chihane.jdaddressselector.model.County
import chihane.jdaddressselector.model.Province
import chihane.jdaddressselector.model.Street
import com.amap.api.services.core.PoiItem
import com.blankj.utilcode.util.ActivityUtils
import com.bumptech.glide.Glide
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureMimeType
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.model.BaseResult
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zhuosongkj.android.library.util.RequestUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.http.ApiUtils
import com.zskjprojectj.andouclient.model.AddressIn
import com.zskjprojectj.andouclient.model.District
import com.zskjprojectj.andouclient.model.UserIn
import com.zskjprojectj.andouclient.utils.GlideEngine
import com.zskjprojectj.andouclient.utils.KEY_DATA
import com.zskjprojectj.andouclient.utils.LoginInfoUtil
import com.zskjprojectj.andouclient.utils.ToastUtil
import com.zskjprojectj.andouclient.utils.UrlUtil.getImageUrl
import com.zskjprojectj.andouclient.view.AddressBottomDialog
import kotlinx.android.synthetic.main.activity_mall_merchant_join.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*

@SuppressLint("SetTextI18n")
class MallMerchantJoinActivity : BaseActivity() {

    var selectedAddress: AddressIn? = null
    var type = 0
    private var bannerImgPath = ""
    private var logoImgPath = ""
    private var licenseImgPath = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        type = intent.getIntExtra(UserIn.Role.KEY_TYPE, UserIn.Role.Type.MALL.typeInt)
        selectLocationBtn.setOnClickListener {
            ActivityUtils.startActivityForResult(mActivity, SelectLocationActivity::class.java, REQUEST_CODE_SELECT_POSITION)
        }
        when (type) {
            UserIn.Role.Type.HOTEL.typeInt -> {
                ActionBarUtil.setTitle(mActivity, "酒店商家入驻")
            }
            UserIn.Role.Type.MALL.typeInt -> {
                ActionBarUtil.setTitle(mActivity, "商城商家入驻")
            }
            UserIn.Role.Type.RESTAURANT.typeInt -> {
                ActionBarUtil.setTitle(mActivity, "饭店商家入驻")
            }
        }
        selectDistrictBtn.setOnClickListener {
            val dialog = AddressBottomDialog.show(this)
            dialog.setAddressProvider(object : AddressProvider {
                override fun provideProvinces(addressReceiver: AddressReceiver<Province>) {
                    RequestUtil.request(mActivity, false, false,
                            { ApiUtils.getApiService().districts(null) }
                    ) { result: BaseResult<out List<District>?> ->
                        val provinces = ArrayList<Province>()
                        if (result.data != null) {
                            for (district in result.data!!) {
                                val province = Province()
                                province.id = district.id
                                province.name = district.name
                                provinces.add(province)
                            }
                        }
                        addressReceiver.send(provinces)
                    }
                }

                override fun provideCitiesWith(provinceId: Int, addressReceiver: AddressReceiver<City>) {
                    RequestUtil.request(mActivity, false, false,
                            { ApiUtils.getApiService().districts(provinceId) }
                    ) { result: BaseResult<out List<District>?> ->
                        val cities = ArrayList<City>()
                        if (result.data != null) {
                            for (district in result.data!!) {
                                val city = City()
                                city.id = district.id
                                city.name = district.name
                                cities.add(city)
                            }
                        }
                        addressReceiver.send(cities)
                    }
                }

                override fun provideCountiesWith(cityId: Int, addressReceiver: AddressReceiver<County>) {
                    RequestUtil.request(mActivity, false, false,
                            { ApiUtils.getApiService().districts(cityId) }
                    ) { result: BaseResult<out List<District>?> ->
                        val counties = ArrayList<County>()
                        if (result.data != null) {
                            for (district in result.data!!) {
                                val county = County()
                                county.id = district.id
                                county.name = district.name
                                counties.add(county)
                            }
                        }
                        addressReceiver.send(counties)
                    }
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
        logoImg.setOnClickListener { startSelectPic(REQUEST_CODE_SELECT_LOGO) }
        bannerImg.setOnClickListener { startSelectPic(REQUEST_CODE_SELECT_BANNER) }
        licenseImg.setOnClickListener { startSelectPic(REQUEST_CODE_SELECT_LICENSE) }
        confirmBtn.setOnClickListener {
            val name = nameEdt.text.toString()
            val contactName = contactNameEdt.text.toString()
            val contactMobile = contactMobileEdt.text.toString()
            val addressStr = addressTxt.text.toString()
            val addressDetail = addressDetailEdt.text.toString()
            val description = descriptionEdt.text.toString()
            when {
                TextUtils.isEmpty(name) -> {
                    ToastUtil.showToast("请输入商户名称!")
                }
                TextUtils.isEmpty(contactName) -> {
                    ToastUtil.showToast("请输入联系人!")
                }
                TextUtils.isEmpty(contactMobile) -> {
                    ToastUtil.showToast("请输入联系电话!")
                }
                TextUtils.isEmpty(contactMobile) -> {
                    ToastUtil.showToast("请输入联系电话!")
                }
                TextUtils.isEmpty(addressStr) -> {
                    ToastUtil.showToast("请选择店铺地址!")
                }
                TextUtils.isEmpty(addressDetail) -> {
                    ToastUtil.showToast("请选择详细地址!")
                }
                TextUtils.isEmpty(description) -> {
                    ToastUtil.showToast("请选择商家简介!")
                }
                TextUtils.isEmpty(logoImgPath) -> {
                    ToastUtil.showToast("请添加商家Logo图!")
                }
                TextUtils.isEmpty(bannerImgPath) -> {
                    ToastUtil.showToast("请添加商家海报图!")
                }
                TextUtils.isEmpty(licenseImgPath) -> {
                    ToastUtil.showToast("请添加营业执照!")
                }
                else -> {
                    RequestUtil.request(mActivity, true, false, {
                        ApiUtils.getApiService().uploadMerchantsInfo(LoginInfoUtil.getUid(),
                                LoginInfoUtil.getToken(),
                                type,
                                name,
                                contactName,
                                contactMobile,
                                1,
                                1,
                                selectedAddress?.county?.id?.toString()
                                        ?: selectedAddress?.city?.id.toString(),
                                addressStr,
                                description,
                                bannerImgPath,
                                logoImgPath,
                                licenseImgPath)
                    }, {
                        ActivityUtils.startActivity(ApplyforsuccessfulActivity::class.java)
                        finish()
                    })
                }
            }
        }
    }

    private fun startSelectPic(requestCode: Int) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .maxSelectNum(1)
                .isCamera(true)
                .loadImageEngine(GlideEngine.createGlideEngine())
                .forResult(requestCode)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == REQUEST_CODE_SELECT_POSITION && data != null) {
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
        } else {
            var path = PictureSelector.obtainMultipleResult(data)[0].androidQToPath
            if (TextUtils.isEmpty(path)) {
                path = PictureSelector.obtainMultipleResult(data)[0].path
            }
            var imageView: ImageView? = null
            when (requestCode) {
                REQUEST_CODE_SELECT_LOGO -> imageView = logoImg
                REQUEST_CODE_SELECT_BANNER -> imageView = bannerImg
                REQUEST_CODE_SELECT_LICENSE -> imageView = licenseImg
            }
            val file = File(path)
            val requestFile = RequestBody.create(MediaType.parse("image/jpg"), file)
            val body = MultipartBody.Part.createFormData("img", file.name, requestFile)
            val uid = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getUid())
            val token = RequestBody.create(MediaType.parse("multipart/form-data"), LoginInfoUtil.getToken())
            val finalImageView = imageView
            RequestUtil.request(mActivity, true, false, {
                ApiUtils.getApiService().uploadImg(uid, token, body)
            }, {
                when {
                    finalImageView === logoImg -> {
                        logoImgPath = it.data
                    }
                    finalImageView === bannerImg -> {
                        bannerImgPath = it.data
                    }
                    finalImageView === licenseImg -> {
                        licenseImgPath = it.data
                    }
                }
                Glide.with(mActivity).load(getImageUrl(it.data)).into(finalImageView!!)
            })
        }
    }

    override fun getContentView() = R.layout.activity_mall_merchant_join

    companion object {
        private const val REQUEST_CODE_SELECT_LOGO = 666
        private const val REQUEST_CODE_SELECT_BANNER = 667
        private const val REQUEST_CODE_SELECT_LICENSE = 668
        private const val REQUEST_CODE_SELECT_POSITION = 669

        fun start(activity: Activity, type: UserIn.Role.Type, requestCode: Int) {
            val bundle = Bundle()
            bundle.putInt(UserIn.Role.KEY_TYPE, type.typeInt)
            ActivityUtils.startActivityForResult(bundle, activity, MallMerchantJoinActivity::class.java, requestCode)
        }
    }
}