package com.zskjprojectj.andouclient.activity;

import android.app.Dialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.SPUtils;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.utils.DialogUtil;

public class SplashActivity extends AppCompatActivity {

    private static final String KEY_PROTOCOL_BEEN_SHOWN = "KEY_PROTOCOL_BEEN_SHOWN";

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (SPUtils.getInstance().getBoolean(KEY_PROTOCOL_BEEN_SHOWN, false)) {
            start();
        } else {
            showProtocolDialog();
        }
    }

    private void showProtocolDialog() {
        findViewById(R.id.logo).postDelayed(() ->
                        dialog = DialogUtil.showProtocolDialog(this, v -> start(), v -> finish()),
                500);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    private void start() {
        SPUtils.getInstance().put(KEY_PROTOCOL_BEEN_SHOWN, true);
        findViewById(R.id.logo).postDelayed(() -> {
            ActivityUtils.startActivity(MainActivity.class);
            finish();
        }, 1000);
    }
}
