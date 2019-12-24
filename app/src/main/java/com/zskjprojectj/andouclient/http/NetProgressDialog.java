package com.zskjprojectj.andouclient.http;

import android.app.Dialog;
import android.content.Context;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.zskjprojectj.andouclient.R;

import io.reactivex.annotations.NonNull;

public class NetProgressDialog extends Dialog {
    private TextView textview_message;

    public NetProgressDialog(@NonNull Context context) {
        this(context, R.style.loading_dialog);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setCancelable(false);
        setCanceledOnTouchOutside(true);

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_progress_bar, null);
        setContentView(view, params);
        textview_message = view.findViewById(R.id.textview_message);
        Window win = getWindow();
        WindowManager.LayoutParams layoutParams = win.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        win.setAttributes(layoutParams);
    }

    public NetProgressDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setMsg(String msg) {
        if (!TextUtils.isEmpty(msg) && textview_message != null) textview_message.setText(msg);
    }
}
