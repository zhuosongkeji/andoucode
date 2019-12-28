package com.zskjprojectj.andouclient.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.blankj.utilcode.util.ActivityUtils;
import com.zskjprojectj.andouclient.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        findViewById(R.id.logo).postDelayed(() ->
                ActivityUtils.startActivity(MainActivity.class), 1000);
    }
}
