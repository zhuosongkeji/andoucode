package com.zskjprojectj.andouclient.http;


import com.google.gson.JsonParseException;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public abstract class BaseHandleObserver<T> implements Observer<T> {

    private static final String TAG = BaseHandleObserver.class.getSimpleName();

    @Override
    public void onError(Throwable e) {
        e.printStackTrace();
         //Logger.e(TAG, e.getMessage() + "");
        if (e instanceof ApiException) {
            onHandleError((ApiException) e);
        } else if (e instanceof SocketTimeoutException) { // 连接超时
            onHandleError(new ApiException(ApiException.TYPE_NETWORK_ERROR, "超时连接"));
        } else if (e instanceof JsonParseException) {// 数据解析异常
            onHandleError(new ApiException(ApiException.TYPE_JSON_ERROR, "数据解析错误"));
        } else if (e instanceof UnknownHostException || e instanceof ConnectException) {// 无网络
            onHandleError(new ApiException(ApiException.TYPE_NETWORK_ERROR, "网络连接失败"));
        } else {//其他错误
            onHandleError(new ApiException(ApiException.TYPE_NETWORK_ERROR, ""));
        }
    }

    /**
     * 失败处理
     *
     * @param apiExc
     */
    public void onHandleError(ApiException apiExc) {
            ToastUtil.showToast(apiExc.getMessage());

        switch (apiExc.getErrorCode()) {
            case ApiException.TYPE_NETWORK_ERROR: // 没有网路哟

                break;
            case ApiException.TYPE_OUT_TIME_ERROR:// 超时

                break;
            case ApiException.TYPE_REQUEST_ERROR:// 数据请求错误

                break;
            case ApiException.TYPE_NOT_LOGIN:// 用户未登录

                break;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onComplete() {

    }
}
