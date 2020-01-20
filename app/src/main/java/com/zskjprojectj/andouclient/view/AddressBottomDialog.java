package com.zskjprojectj.andouclient.view;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.zskjprojectj.andouclient.R;

import chihane.jdaddressselector.AddressProvider;
import chihane.jdaddressselector.AddressSelector;
import chihane.jdaddressselector.BottomDialog;
import chihane.jdaddressselector.OnAddressSelectedListener;
import mlxy.utils.Dev;

public class AddressBottomDialog extends BottomDialog {
    private AddressSelector selector;

    protected AddressBottomDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.init(context);
    }

    public AddressBottomDialog(Context context) {
        super(context, chihane.jdaddressselector.R.style.bottom_dialog);
        this.init(context);
    }

    public AddressBottomDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.init(context);
    }

    public   void setTitles(String title)
    {
       TextView textView= findViewById(R.id.tv_addresstitle);
       textView.setText(title);
    }
    private void init(Context context) {
        this.selector = new AddressSelector(context);
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.address_linearlayout, null);
        view.findViewById(R.id.close).setOnClickListener(v -> dismiss());
        view.addView(this.selector.getView());
        this.setContentView(view);
        Window window = this.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = -1;
        params.height = Dev.dp2px(context, 305.0F);
        window.setAttributes(params);
        window.setGravity(80);
    }

    public void setOnAddressSelectedListener(OnAddressSelectedListener listener) {
        this.selector.setOnAddressSelectedListener(listener);
    }

    public void setAddressProvider(AddressProvider provider) {
        this.selector.setAddressProvider(provider);
    }

    public static AddressBottomDialog show(Context context) {
        return show(context, null);
    }

    public static AddressBottomDialog show(Context context, OnAddressSelectedListener listener) {
        AddressBottomDialog dialog = new AddressBottomDialog(context, chihane.jdaddressselector.R.style.bottom_dialog);
        dialog.selector.setOnAddressSelectedListener(listener);
        dialog.show();
        return dialog;
    }
}
