package com.zskjprojectj.andouclient.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;

import com.zskjprojectj.andouclient.R;

public class DialogUtil {
    public static Dialog showProtocolDialogNoBtns(Activity activity) {
        return showProtocolDialog(activity, null, null);
    }

    public static Dialog showProtocolDialog(Activity activity,
                                            View.OnClickListener okListener,
                                            View.OnClickListener cancelListener) {
        View view = LayoutInflater.from(activity).inflate(R.layout.layout_protocol, null);
        final Dialog dialog = new AlertDialog.Builder(activity)
                .setView(view)
                .show();
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.getWindow().setGravity(Gravity.CENTER);
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setBackgroundDrawable(null);
        if (okListener != null && cancelListener != null) {
            view.findViewById(R.id.quitBtn).setOnClickListener((v) -> {
                dialog.dismiss();
                cancelListener.onClick(v);
            });
            view.findViewById(R.id.agreeBtn).setOnClickListener((v) -> {
                dialog.dismiss();
                okListener.onClick(v);
            });
            dialog.setCancelable(false);
        } else {
            view.findViewById(R.id.btnsContainer).setVisibility(View.GONE);
        }
        ((WebView) view.findViewById(R.id.webView)).loadUrl("http://andou.zhuosongkj.com/privacy.html");
        return dialog;
    }
}
