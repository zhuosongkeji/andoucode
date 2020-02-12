package com.zskjprojectj.andouclient.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.webkit.WebView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Poi;
import com.amap.api.navi.AmapNaviPage;
import com.amap.api.navi.AmapNaviParams;
import com.amap.api.navi.AmapNaviType;
import com.amap.api.navi.INaviInfoCallback;
import com.amap.api.navi.model.AMapNaviLocation;
import com.zskjprojectj.andouclient.activity.WebViewActivity;

public class MapUtil {
    public static void start(String keyword, Activity activity) {
//        Intent intent;
//        try {
//            intent = Intent.parseUri("intent://map/direction?" +
//                    "destination=" + keyword +
//                    "&mode=driving&" +
//                    "&src=appname#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end", 0);
//
//            ComponentName ComponentName = intent.resolveActivity(activity.getPackageManager());
//            if (ComponentName == null) {
//                intent = Intent.parseUri("androidamap://poi?sourceApplication=安抖本地生活" +
//                        "&keywords=" + keyword +
//                        "&dev=0", 0);
//            }
//            ComponentName = intent.resolveActivity(activity.getPackageManager());
//            if (ComponentName == null) {
//                intent = new Intent("android.intent.action.VIEW", Uri.parse("qqmap://map/search?keyword=" +
//                        keyword + "&referer=安抖本地生活"));
//            }
//            activity.startActivity(intent);
//        } catch (Exception e) {
//            ToastUtil.showToast("地图启动失败,请检查是否安装地图!");
//            e.printStackTrace();
//        }
//        WebViewActivity.start("https://m.amap.com/navigation/carmap/saddr=116.49798,39.949459&daddr=116.404269,39.99281");
        Poi start = new Poi("三元桥", new LatLng(39.96087, 116.45798), "");
/**终点传入的是北京站坐标,但是POI的ID "B000A83M61"对应的是北京西站，所以实际算路以北京西站作为终点**/
        Poi end = new Poi("北京站", new LatLng(39.904556, 116.427231), "B000A83M61");
        AmapNaviPage.getInstance().showRouteActivity(activity, new AmapNaviParams(start, null, end, AmapNaviType.DRIVER), new INaviInfoCallback() {
            @Override
            public void onInitNaviFailure() {

            }

            @Override
            public void onGetNavigationText(String s) {

            }

            @Override
            public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

            }

            @Override
            public void onArriveDestination(boolean b) {

            }

            @Override
            public void onStartNavi(int i) {

            }

            @Override
            public void onCalculateRouteSuccess(int[] ints) {

            }

            @Override
            public void onCalculateRouteFailure(int i) {

            }

            @Override
            public void onStopSpeaking() {

            }

            @Override
            public void onReCalculateRoute(int i) {

            }

            @Override
            public void onExitPage(int i) {

            }

            @Override
            public void onStrategyChanged(int i) {

            }

            @Override
            public View getCustomNaviBottomView() {
                return null;
            }

            @Override
            public View getCustomNaviView() {
                return null;
            }

            @Override
            public void onArrivedWayPoint(int i) {

            }

            @Override
            public void onMapTypeChanged(int i) {

            }

            @Override
            public View getCustomMiddleView() {
                return null;
            }

            @Override
            public void onNaviDirectionChanged(int i) {

            }

            @Override
            public void onDayAndNightModeChanged(int i) {

            }

            @Override
            public void onBroadcastModeChanged(int i) {

            }

            @Override
            public void onScaleAutoChanged(boolean b) {

            }
        });
    }
}
