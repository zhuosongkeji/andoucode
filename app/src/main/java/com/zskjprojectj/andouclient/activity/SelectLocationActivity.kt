package com.zskjprojectj.andouclient.activity

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.*
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.blankj.utilcode.util.KeyboardUtils
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.loadmore.LoadMoreView
import com.zaaach.citypicker.CityPicker
import com.zaaach.citypicker.adapter.OnPickListener
import com.zaaach.citypicker.model.City
import com.zaaach.citypicker.model.LocateState
import com.zaaach.citypicker.model.LocatedCity
import com.zhuosongkj.android.library.app.BaseActivity
import com.zhuosongkj.android.library.util.ActionBarUtil
import com.zskjprojectj.andouclient.R
import com.zskjprojectj.andouclient.adapter.PoiItemListAdapter
import com.zskjprojectj.andouclient.utils.KEY_DATA
import kotlinx.android.synthetic.main.activity_select_location.*

class SelectLocationActivity : BaseActivity() {

    val adapter = PoiItemListAdapter()
    var poiSearch: PoiSearch? = null
    val poiSearchQuery = PoiSearch.Query("", "", "")
    var regeocodeAddress: RegeocodeAddress? = null
    var refresh: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActionBarUtil.setTitle(mActivity, "定位地址")
        initSearchBar()
        selectCityBtn.setOnClickListener {
            CityPicker.from(mActivity)
                    .enableAnimation(true)
                    .setOnPickListener(object : OnPickListener {
                        override fun onPick(position: Int, data: City) {
                            cityTxt.text = data.name
                        }

                        override fun onCancel() {
                        }

                        override fun onLocate() {
                            baseContentView.postDelayed({
                                CityPicker.from(mActivity).locateComplete(
                                        LocatedCity(regeocodeAddress?.city,
                                                regeocodeAddress?.province,
                                                regeocodeAddress?.adCode),
                                        LocateState.SUCCESS)
                            }, 1000)
                        }
                    })
                    .show()
        }
        adapter.bindToRecyclerView(recyclerView)
        adapter.setOnItemClickListener { _, _, position ->
            val intent = Intent()
            intent.putExtra(KEY_DATA, adapter.getItem(position))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        adapter.setLoadMoreView(object : LoadMoreView() {
            override fun getLayoutId(): Int {
                return com.zhuosongkj.android.library.R.layout.layout_load_more_view
            }

            override fun getLoadingViewId(): Int {
                return com.zhuosongkj.android.library.R.id.load_more_loading_view
            }

            override fun getLoadFailViewId(): Int {
                return com.zhuosongkj.android.library.R.id.load_more_load_fail_view
            }

            override fun getLoadEndViewId(): Int {
                return com.zhuosongkj.android.library.R.id.load_more_load_end_view
            }
        })
        adapter.setEnableLoadMore(true)
        adapter.disableLoadMoreIfNotFullPage(recyclerView)
        adapter.setOnLoadMoreListener({
            poiSearch?.searchPOIAsyn()
        }, recyclerView)
        mapView.onCreate(savedInstanceState)
        mapView.map.uiSettings.isMyLocationButtonEnabled = true
        mapView.map.isMyLocationEnabled = true
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.location_blue))
        myLocationStyle.interval(2000)
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE)
        myLocationStyle.radiusFillColor(Color.TRANSPARENT)
        myLocationStyle.strokeColor(Color.TRANSPARENT)
        mapView.map.myLocationStyle = myLocationStyle
        mapView.map.setOnMyLocationChangeListener {
            val geoCoderSearch = GeocodeSearch(mActivity)
            geoCoderSearch.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
                override fun onRegeocodeSearched(result: RegeocodeResult?, p1: Int) {
                    cityTxt.text = result?.regeocodeAddress?.city
                    regeocodeAddress = result?.regeocodeAddress
                    mapView.map.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
                        override fun onCameraChangeFinish(result: CameraPosition?) {
                            searchLocationPoi(result?.target?.latitude, result?.target?.longitude)
                        }

                        override fun onCameraChange(p0: CameraPosition?) {
                        }
                    })
                }

                override fun onGeocodeSearched(result: GeocodeResult?, p1: Int) {
                }
            })
            geoCoderSearch.getFromLocationAsyn(RegeocodeQuery(LatLonPoint(it.latitude, it.longitude), 1000F, GeocodeSearch.AMAP))
            mapView.map.moveCamera(CameraUpdateFactory.zoomTo(17F))
            poiSearch = PoiSearch(mActivity, poiSearchQuery)
            poiSearch?.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
                override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
                }

                override fun onPoiSearched(result: PoiResult, code: Int) {
                    if (code == 1000) {
                        if (result.pois.size > 0) {
                            adapter.loadMoreComplete()
                            if (refresh) {
                                refresh = false
                                adapter.setNewData(result.pois)
                            } else {
                                adapter.addData(result.pois)
                            }
                            poiSearchQuery.pageNum += 1
                        } else {
                            adapter.loadMoreEnd()
                        }
                    } else {
                        adapter.loadMoreFail()
                        ToastUtils.showShort("获取数据失败")
                    }
                }
            })
        }
    }

    private fun searchLocationPoi(lat: Double?, long: Double?) {
        refresh = true
        poiSearch?.bound = PoiSearch.SearchBound(
                LatLonPoint(lat ?: 0.0, long ?: 0.0),
                20 * 1000)
        poiSearch?.searchPOIAsyn()
    }

    private fun initSearchBar() {
        val searchResultAdapter = PoiItemListAdapter()
        var poiSearchQuery = PoiSearch.Query("", "", cityTxt.text.toString())
        val poiSearch = PoiSearch(mActivity, poiSearchQuery)
        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
            }

            override fun onPoiSearched(result: PoiResult, code: Int) {
                if (code == 1000) {
                    if (result.pois.size > 0) {
                        searchResultAdapter.loadMoreComplete()
                        searchResultAdapter.addData(result.pois)
                        poiSearchQuery.pageNum += 1
                    } else {
                        searchResultAdapter.loadMoreEnd()
                    }
                } else {
                    searchResultAdapter.loadMoreFail()
                    ToastUtils.showShort("获取数据失败")
                }
            }
        })
        searchResultAdapter.bindToRecyclerView(searchResultRecyclerView)
        searchResultAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent()
            intent.putExtra(KEY_DATA, searchResultAdapter.getItem(position))
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
        searchResultAdapter.setEnableLoadMore(true)
        searchResultAdapter.setLoadMoreView(object : LoadMoreView() {
            override fun getLayoutId(): Int {
                return com.zhuosongkj.android.library.R.layout.layout_load_more_view
            }

            override fun getLoadingViewId(): Int {
                return com.zhuosongkj.android.library.R.id.load_more_loading_view
            }

            override fun getLoadFailViewId(): Int {
                return com.zhuosongkj.android.library.R.id.load_more_load_fail_view
            }

            override fun getLoadEndViewId(): Int {
                return com.zhuosongkj.android.library.R.id.load_more_load_end_view
            }
        })
        searchResultAdapter.setOnLoadMoreListener({
            poiSearch.searchPOIAsyn()
        }, recyclerView)
        keywordEdt.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                searchResultRecyclerView.visibility = View.VISIBLE
                cancelBtn.visibility = View.VISIBLE
            }
        }
        keywordEdt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                poiSearchQuery = PoiSearch.Query(s.toString(), "", cityTxt.text.toString())
                poiSearch.query = poiSearchQuery
                poiSearch.searchPOIAsyn()
            }
        })
        cancelBtn.setOnClickListener {
            keywordEdt.setText("")
            keywordEdt.clearFocus()
            KeyboardUtils.hideSoftInput(keywordEdt)
            it.visibility = View.GONE
            searchResultAdapter.setNewData(null)
            searchResultRecyclerView.visibility = View.GONE
        }
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
