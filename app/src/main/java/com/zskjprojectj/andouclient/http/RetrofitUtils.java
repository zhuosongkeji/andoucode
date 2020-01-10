package com.zskjprojectj.andouclient.http;

import com.google.gson.GsonBuilder;
import com.zskjprojectj.andouclient.utils.LoginInfoUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitUtils {
    private static final String TAG = RetrofitUtils.class.getSimpleName() + "：";
    public static final int OUT_TIME = 10;
    public static final int READ_WRITE_TIME = 10;

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final RetrofitUtils INSTANCE = new RetrofitUtils();
    }

    //获取单例
    public static RetrofitUtils getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private RetrofitUtils() {
    }

    /**
     * 设置okHttp
     *
     * @author ZhongDaFeng
     */
    private static OkHttpClient okHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .connectTimeout(OUT_TIME, TimeUnit.SECONDS)
                .writeTimeout(READ_WRITE_TIME, TimeUnit.SECONDS)
                .readTimeout(READ_WRITE_TIME, TimeUnit.SECONDS)
                .addInterceptor(chain -> {
                    Request request = chain.request()
                            .newBuilder()
                            .addHeader("token", LoginInfoUtil.getToken())
                            .build();
                    return chain.proceed(request);
                })
                .addInterceptor(new HttpLoggingInterceptor(
                        new HttpLogger()).setLevel(HttpLoggingInterceptor.Level.BODY));
               // .addInterceptor(new HeaderInterceptors());


        return builder.build();
    }

    /**
     * 获取Retrofit
     *
     * @author ZhongDaFeng
     */
    public Retrofit retrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient())
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("MMM d, yyyy").create()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }
}
