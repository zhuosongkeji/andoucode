package com.zskjprojectj.andouclient.activity;


import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.blankj.utilcode.util.ActivityUtils;
import com.zhuosongkj.android.library.app.BaseActivity;
import com.zhuosongkj.android.library.util.ActionBarUtil;
import com.zskjprojectj.andouclient.R;

/**
 * 直播界面
 */
public class WebViewActivity extends BaseActivity {

    private static final String KEY_INFO = "KEY_INFO";
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBarUtil.setTitle(mActivity, "加载中");
        webView = findViewById(R.id.webView);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }
        });
    }


    public static void start(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_INFO, url);
        ActivityUtils.startActivity(bundle, WebViewActivity.class);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_web_view;
    }
}
