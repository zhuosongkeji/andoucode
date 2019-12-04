package com.zskjprojectj.andouclient.utils;

import android.os.Handler;
import android.os.Message;

import android.text.TextUtils;
import android.widget.Toast;

import com.zskjprojectj.andouclient.base.BaseApplication;

import io.reactivex.annotations.NonNull;


public class ToastUtil {
    private static Toast toast;

    private static Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 0x110:
                    if (toast == null) {
                        toast = Toast.makeText(BaseApplication.getInstance(), (String) msg.obj, Toast.LENGTH_SHORT);
                    } else {
                        toast.setText(msg.obj.toString());
                    }
                    toast.show();
                    break;
            }
            return false;
        }
    });

    public static void showToast(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            return;
        }
        Message message = Message.obtain();
        message.obj = text;
        message.what = 0x110;
        handler.sendMessage(message);
    }
}
