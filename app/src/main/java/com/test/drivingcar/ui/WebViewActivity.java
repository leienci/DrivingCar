package com.test.drivingcar.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.test.drivingcar.base.BaseActivity;
import com.test.drivingcar.databinding.ActivityWebviewBinding;

public class WebViewActivity extends BaseActivity<ActivityWebviewBinding> {
    private ActivityWebviewBinding mBinding;
    private WebView webView;

    public static void start(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);
    }

    @Override
    protected ActivityWebviewBinding getViewBinding() {
        mBinding = ActivityWebviewBinding.inflate(getLayoutInflater());
        return mBinding;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        initWebView();
    }

    @Override
    protected void initData() {
        mBinding.ivBack.setOnClickListener(v -> {
            finish();
        });


    }

    @Override
    public void onBaseClick(View v) {

    }

    private void initWebView() {
        webView = new WebView(getApplicationContext());
        mBinding.viewWeb.addView(webView, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));
        webView.setWebViewClient(new AppWebViewClient());

    }

    /**
     * WebViewClient
     */
    private class AppWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView webView, final String url) {
            if (webView != null) {
                webView.loadUrl(url);
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String url, Bitmap bitmap) {

        }

        @Override
        public void onPageFinished(WebView webView, String url) {
            if (webView != null) {

                String title = webView.getTitle();
                if (!title.contains("about:blank") && !title.contains("text/html") && !title.contains("找不到网页")) {
                    mBinding.tvTitle.setText(title);
                } else {
                    mBinding.tvTitle.setText("");
                }
            }
        }

        // 处理https请求
        @Override
        public void onReceivedSslError(WebView webView, final SslErrorHandler handler, SslError sslError) {
        }
    }

}
