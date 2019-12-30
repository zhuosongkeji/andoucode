package com.zskjprojectj.andouclient.activity;


import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.blankj.utilcode.util.ActivityUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zskjprojectj.andouclient.R;
import com.zskjprojectj.andouclient.adapter.LiveAdapter;
import com.zskjprojectj.andouclient.base.BaseActivity;
import com.zskjprojectj.andouclient.base.BaseListActivity;
import com.zskjprojectj.andouclient.base.BasePresenter;
import com.zskjprojectj.andouclient.entity.TestBean;
import com.zskjprojectj.andouclient.http.ApiUtils;
import com.zskjprojectj.andouclient.http.BaseResult;
import com.zskjprojectj.andouclient.http.HttpRxObservable;
import com.zskjprojectj.andouclient.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

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

    @Override
    protected void initViews() {
        topView.setTitle("加载中");
        webView = findViewById(R.id.webView);
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
