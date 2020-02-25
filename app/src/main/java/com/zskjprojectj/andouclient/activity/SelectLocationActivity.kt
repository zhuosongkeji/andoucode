package com.zskjprojectj.andouclient.activity

import android.os.Bundle
import com.amap.api.maps.model.MyLocationStyle
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import kotlinx.android.synthetic.main.activity_select_location.*

class SelectLocationActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "定位地址")
        mapView.onCreate(savedInstanceState)
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.interval(2000)
        mapView.map.myLocationStyle = myLocationStyle
        mapView.map.uiSettings.isMyLocationButtonEnabled = true
        mapView.map.isMyLocationEnabled = true
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun getContentView() = R.layout.activity_select_location
}
