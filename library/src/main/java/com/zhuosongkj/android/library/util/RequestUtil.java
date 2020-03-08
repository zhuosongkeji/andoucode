package com.zhuosongkj.android.library.util;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.google.gson.stream.MalformedJsonException;
import com.zhuosongkj.android.library.R;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.exception.ApiException;
import com.zhuosongkj.android.library.model.BaseResult;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class RequestUtil {

    public static OnLoginReqeustListener onLoginRequest;

    public static <T> void request(BaseActivity activity, boolean showLoading, boolean showRetry,
                                   ObservableProvider<? extends T> provider,
                                   OnSuccessListener<T> onSuccessListener,
                                   OnFailureListener onFailureListener) {
        provider.getObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BaseResult<? extends T>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        dismissNetworkError(activity);
                        if (showLoading) {
                            showProgressDialog(activity);
                        }
                    }

                    @Override
                    public void onNext(BaseResult<? extends T> result) {
                        dismissProgressDialog(activity);
                        if (result.code.equals("200")) {
                            onSuccessListener.onSuccess(result);
                        } else if (result.code.equals("202")) {
                            if (onLoginRequest != null) {
                                onLoginRequest.onLoginRequest(activity);
                            }
                            onError(new ApiException(result.code, result.msg));
                        } else if (result.code.equals("203") && result.msg.contains("绑定")) {
                            onSuccessListener.onSuccess(result);
                        } else {
                            onError(new ApiException(result.code, result.msg));
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        handleError(throwable, activity, onFailureListener, showRetry,
                                v -> request(activity, showLoading, showRetry, provider, onSuccessListener, onFailureListener));
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static <T> void request(BaseActivity activity, boolean showLoading, boolean showRetry,
                                   ObservableProvider<T> provider,
                                   OnSuccessListener<T> onSuccessListener) {
        request(activity, showLoading, showRetry, provider, onSuccessListener, null);
    }

    private static void handleError(Throwable throwable,
                                    BaseActivity activity,
                                    OnFailureListener onFailureListener,
                                    boolean showRetry, View.OnClickListener retryAction) {
        dismissProgressDialog(activity);
        if (onFailureListener != null) {
            onFailureListener.onFailure(throwable.getMessage());
        }
        String msg = convertErrorMsg(throwable);
        if (showRetry) {
            showNetworkError(activity, msg, retryAction);
        } else {
            ToastUtils.showShort(msg);
        }
        throwable.printStackTrace();
    }

    private static String convertErrorMsg(Throwable throwable) {
        if (throwable instanceof ApiException) {
            return ((ApiException) throwable).getErrorCode() + " " + throwable.getMessage();
        } else if (throwable instanceof MalformedJsonException) {
            return "500 服务器数据格式错误";
        } else if (throwable instanceof HttpException) {
            if (((HttpException) throwable).code() == 500) {
                return "500 服务器内部错误";
            }
        }
        return throwable.getLocalizedMessage() + "访问错误,请稍后重试!";
    }

    public interface OnSuccessListener<T> {
        void onSuccess(BaseResult<? extends T> result);
    }

    public interface OnFailureListener {
        void onFailure(String msg);
    }

    public interface ObservableProvider<T> {
        Observable<? extends BaseResult<? extends T>> getObservable();
    }

    private static void dismissProgressDialog(BaseActivity activity) {
        ViewGroup contentView = activity.findViewById(R.id.baseContentView);
        View progressBarContainer = activity.findViewById(R.id.progressBarContainer);
        contentView.removeView(progressBarContainer);
    }

    private static void dismissNetworkError(BaseActivity activity) {
        if (activity == null) return;
        ((ViewGroup) activity.findViewById(R.id.baseContentView))
                .removeView(activity.findViewById(R.id.networkErrorContainer));
    }

    private static void showNetworkError(BaseActivity activity, String errorMsg, View.OnClickListener retryAction) {
        if (activity == null) return;
        if (activity.findViewById(R.id.networkErrorContainer) == null) {
            final View networkErrorContainer = LayoutInflater.from(activity).inflate(R.layout.layout_network_error, null);
            ((TextView) networkErrorContainer.findViewById(R.id.msgTxt)).setText(errorMsg);
            ViewGroup baseContentView = activity.findViewById(R.id.baseContentView);
            baseContentView.postDelayed(() -> baseContentView.addView(networkErrorContainer), 1000);
            networkErrorContainer.setOnClickListener(retryAction);
        }
    }

    private static void showProgressDialog(BaseActivity activity) {
        setupProgressBar(activity);
        ImageView progressBar = activity.findViewById(R.id.progressBar);
        if (progressBar.getDrawable() instanceof AnimationDrawable) {
            ((AnimationDrawable) progressBar.getDrawable()).start();
        }
    }

    private static void setupProgressBar(BaseActivity activity) {
        View progressBarContainer = activity.findViewById(R.id.progressBarContainer);
        ViewGroup contentView = activity.findViewById(R.id.baseContentView);
        if (progressBarContainer == null) {
            progressBarContainer = LayoutInflater.from(activity).inflate(R.layout.layout_progress_bar, null);
            contentView.addView(progressBarContainer);
        }
        progressBarContainer.setOnClickListener(view -> {
        });
    }

    public interface OnLoginReqeustListener {
        void onLoginRequest(Activity activity);
    }
}
