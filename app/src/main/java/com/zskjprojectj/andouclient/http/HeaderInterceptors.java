package com.zskjprojectj.andouclient.http;


import com.orhanobut.logger.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;

public class HeaderInterceptors implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        RequestBody requestBody = request.body();
        String oldUrl = request.url().toString();
        Logger.e("HeaderInterceptors", oldUrl);
        Map<String, String> map = getPublicParams(request);
        if ("POST".equals(request.method())) {
            MediaType mediaType = null;
            if (request.body().contentType() == null) {
                mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8");
            }
            //直接调用post请求
            String postBodyString = getBuildPostParams(request, map);
            //String postBodyString = createMd5(request, map);
            request = request.newBuilder().url(oldUrl).method(request.method(),
                    RequestBody.create(requestBody.contentType() == null ? mediaType : requestBody.contentType(), postBodyString)).build();
        } else if ("GET".equals(request.method())) {
            String newUrl = getBuildGetParams(map, oldUrl);
            request = request.newBuilder().url(newUrl).build();
        }
        return chain.proceed(request);
    }

    private String bodyToString(RequestBody request) {
        try {
            Buffer buffer = new Buffer();
            request.writeTo(buffer);
            return buffer.readUtf8();
        } catch (IOException e) {
            return "";
        }
    }

    private Map<String, String> getPublicParams(Request request) {
        Map<String, String> map = new HashMap<>();
    //        String deviceId = new DeviceUuidFactory(BaseApplication.getInstance()).getDeviceUuid().toString();
    //        map.put("versionCode", BuildConfig.VERSION_CODE + "");
    //        map.put("os", "android");
    //        map.put("useport", "2");
    //        map.put("deviceId", deviceId);
    //        map.put("uid", SharedPreferencesManager.getUid());
    //        map.put("phoneName", DeviceUuidFactory.getPhoneBrand());
    //        map.put("deviceName", DeviceUuidFactory.getPhoneModel());
    //        map.put("ram", DeviceUuidFactory.getAvailMemory(BaseApplication.getInstance()));
    //        map.put("metrics", DeviceUuidFactory.getMetrics(BaseApplication.getInstance()));
    //        map.put("system", DeviceUuidFactory.getSystemVersion());
    //        map.put("operator", DeviceUuidFactory.getOperators(BaseApplication.getInstance()));
    //        map.put("netType", DeviceUuidFactory.getNetworkType(BaseApplication.getInstance()));
        return map;
    }


//    private FormBody.Builder getBuildPostParams(Map<String, String> map) {
//        FormBody.Builder builder = new FormBody.Builder();
//        try {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                builder.add(entry.getKey(), entry.getValue());
//            }
//        } catch (Exception e) {
//
//        }
//        return builder;
//    }

    /**
     * 公共参数不加密调用
     * @param request
     * @param map
     * @return
     */
    private String getBuildPostParams(Request request,Map<String, String> map) {
//        FormBody.Builder builder = new FormBody.Builder();
//        try {
//            for (Map.Entry<String, String> entry : map.entrySet()) {
//                builder.add(entry.getKey(), entry.getValue());
//            }
//        } catch (Exception e) {
//
//        }
//        return builder;
        try {
            FormBody.Builder builder = new FormBody.Builder();
            //JSONObject formData = new JSONObject();
            int count = 0;
            if (request.body().contentLength() > 0 && request.body() instanceof FormBody) {
                count = ((FormBody) request.body()).size();
            }
            for (int i = 0; i < count; i++) {
                String n = ((FormBody) request.body()).encodedName(i);
                String v = ((FormBody) request.body()).value(i);
                builder.add(n, v);
            }
            if (!map.isEmpty()) {
                try {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        builder.add(entry.getKey(), entry.getValue()+"");
                    }
                } catch (Exception e) {

                }
            }
            return bodyToString(builder.build());
        } catch (Exception e) {
            return "";
        }
    }
    /**
     * 加密
     * @param map
     * @return
     */
    private String createMd5(Request request, Map<String, String> map) {
        try {
            FormBody.Builder builder = new FormBody.Builder();
            //JSONObject formData = new JSONObject();
            int count = 0;
            if (request.body().contentLength() > 0 && request.body() instanceof FormBody) {
                count = ((FormBody) request.body()).size();
            }
            for (int i = 0; i < count; i++) {
                String n = ((FormBody) request.body()).encodedName(i);
                String v = ((FormBody) request.body()).value(i);
                builder.add(n, v);
            }
            if (!map.isEmpty()) {
                try {
                    for (Map.Entry<String, String> entry : map.entrySet()) {
                        builder.add(entry.getKey(), entry.getValue()+"");
                    }
                } catch (Exception e) {

                }
            }
            return bodyToString(builder.build());
        } catch (Exception e) {
            return "";
        }
    }

    private String getBuildGetParams(Map<String, String> map, String oldUrl) {
        StringBuilder stringBuilder = new StringBuilder();//创建一个stringBuilder...字符串缓冲区
        stringBuilder.append(oldUrl);
        if (oldUrl.contains("?")) {
            if (oldUrl.indexOf("?") != oldUrl.length() - 1) {
                stringBuilder.append("&");
            }
        } else {
            stringBuilder.append("?");
        }
        try {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                stringBuilder.append(entry.getKey()).append("=").append(entry.getValue()).append("&");
            }
        } catch (Exception e) {

        }
        if (stringBuilder.indexOf("&") != -1) {
            stringBuilder.deleteCharAt(stringBuilder.lastIndexOf("&"));
        }
        return stringBuilder.toString();
    }
}
