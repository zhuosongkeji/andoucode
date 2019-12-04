package com.zskjprojectj.andouclient.http;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private NetProgressDialog pd;

    private WeakReference<Context> context;
    private boolean cancelable;
    private ProgressCancelListener mProgressCancelListener;

    public ProgressDialogHandler(Context context, ProgressCancelListener
            mProgressCancelListener, boolean cancelable) {
        super();
        this.context = new WeakReference<>(context);
        this.mProgressCancelListener = mProgressCancelListener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        try {
            if (pd == null ) {
                pd = new NetProgressDialog(context.get());
                pd.setCancelable(cancelable);
                if (cancelable) {
                    pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                        @Override
                        public void onCancel(DialogInterface dialogInterface) {
                            mProgressCancelListener.onCancelProgress();
                        }
                    });
                }
                if (!pd.isShowing()) {
                    pd.show();
                }
            }
        } catch (Exception e) {
        }
    }

    private void dismissProgressDialog() {
        try {
            if (pd != null && pd.isShowing()) {
                pd.dismiss();
                pd = null;
            }
        } catch (Exception e) {

        }

    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }
}
