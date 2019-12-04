package com.zskjprojectj.andouclient.http;


import com.zskjprojectj.andouclient.BuildConfig;

public class ApiUtils {
    private static ApiService apiService;

    public static ApiService getApiService() {
        return getApiService(BuildConfig.API_HOST);
    }

    public static ApiService getApiService(String baseUrl) {
        if (apiService == null) {
            apiService = RetrofitUtils.getInstance().retrofit(baseUrl).create(ApiService.class);
        }
        return apiService;
    }
}
