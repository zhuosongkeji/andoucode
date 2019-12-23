package com.zskjprojectj.andouclient.http;

import android.app.Activity;

import java.io.IOException;

import io.reactivex.disposables.Disposable;

public abstract class BaseObserver<T> extends BaseHandleObserver<BaseResult<T>> implements ProgressCancelListener {
    private static final String TAG = "BaseObserver";
    private ProgressDialogHandler mProgressDialogHandler;
    private Activity context;
    private Disposable d;
    private BaseResult<T> mData;

    public BaseObserver(Activity aty) {
        this(aty, true);
    }

    public BaseObserver(Activity activity, boolean showLoading) {
        this.context = activity;
        if (showLoading && context != null && !context.isDestroyed()) {
            this.mProgressDialogHandler = new ProgressDialogHandler(context, this, true);
        }
    }


    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG)
                    .sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
        this.d = d;
        showProgressDialog();
        onStart();
    }

    @Override
    public void onNext(BaseResult<T> t) {
        mData = t;
        try {
//            if (t.getCode().equals("200")) { // 请求成功
//                onHandleSuccess(t.getData());
//            } else {
//                onError(new ApiException(t.getCode(), t.getMsg()));
//            }
            if (t.getCode().equals("200"))
            {
                onHandleSuccess(t.getData());
            }else {
                onError(new ApiException(t.getCode(), t.getMsg()));
            }
        } catch (Exception e) {
            onError(new ApiException(ApiException.TYPE_SYSTEM, e.getMessage()));
            e.printStackTrace();
        }
    }

    @Override
    public void onError(Throwable e) {
        dismissProgressDialog();
        super.onError(e);
        onFinish();
    }

    @Override
    public void onComplete() {
        dismissProgressDialog();
        onFinish();
    }

    @Override
    public void onCancelProgress() {
        //如果处于订阅状态，则取消订阅
        if (d != null && !d.isDisposed()) {
            d.dispose();
        }
        onHandleError(new ApiException(ApiException.TYPE_USER_CANCEL, null));
    }

    public void onStart() {
    }

    public void onFinish() {
    }

    /**
     * 成功处理
     *
     * @param t
     */
    public abstract void onHandleSuccess(T t) throws IOException;

    public void onHandleSuccess(BaseResult<T> t) {
    }


    public String getMsg() {
        if (mData != null) {
            //return mData.getMsg();
            return mData.getMsg();
        }
        return null;
    }
}
