package com.zskjprojectj.andouclient.activity;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.blankj.utilcode.util.ActivityUtils;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;

/**
 * 直播界面
 */
public class WebViewActivity extends BaseActivity {

    private static final String KEY_INFO = "KEY_INFO";
    WebView webView;

    @Override
    protected void setRootView() {
        setContentView(R.layout.activity_web_view);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initViews() {
        topView.setTitle("加载中");
        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                webView.loadUrl(request.getUrl().toString());
                return true;
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                topView.setTitle(title);
            }
        });
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        String url = getIntent().getStringExtra(KEY_INFO);
        webView.loadUrl(url);
    }

    @Override
    public void getDataFromServer() {

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    public static void start(String url) {
        Bundle bundle = new Bundle();
        bundle.putString(KEY_INFO, url);
        ActivityUtils.startActivity(bundle, WebViewActivity.class);
    }
}
