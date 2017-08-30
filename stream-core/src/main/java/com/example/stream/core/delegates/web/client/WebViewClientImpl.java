package com.example.stream.core.delegates.web.client;

import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.stream.core.app.ConfigKey;
import com.example.stream.core.app.StreamCore;
import com.example.stream.core.delegates.IPageLoadListener;
import com.example.stream.core.delegates.web.BaseWebDelegate;
import com.example.stream.core.delegates.web.route.Router;
import com.example.stream.core.ui.loader.StreamLoader;
import com.example.stream.core.util.log.StreamLogger;
import com.example.stream.core.util.storage.Preference;

/**
 * Created by StReaM on 8/28/2017.
 */

public class WebViewClientImpl extends WebViewClient {
    private final BaseWebDelegate DELEGATE;
    private IPageLoadListener mIPageLoadListener = null;
    private static final Handler HANDLER = StreamCore.getHandler();

    public void setIPageLoadListener(IPageLoadListener IPageLoadListener) {
        mIPageLoadListener = IPageLoadListener;
    }

    public WebViewClientImpl(BaseWebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        StreamLogger.d("shouldOverFuckingRideUrlLoading");
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        syncCookie();
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadEnd();
        }

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (mIPageLoadListener != null) {
            mIPageLoadListener.onLoadStart();
        }
        StreamLoader.showLoading(view.getContext());
        HANDLER.postDelayed(new Runnable() {
            @Override
            public void run() {
                StreamLoader.stopLoading();
            }
        }, 1000);
    }

    private void syncCookie(){
        final CookieManager cookieManager = CookieManager.getInstance();
        // 这里和 api 请求的 cookie 不一样
        final String webHost = StreamCore.getConfigurations(ConfigKey.WEB_HOST);
        if (webHost != null) {
            final String cookieStr = cookieManager.getCookie(webHost);
            if (cookieStr != null && !TextUtils.isEmpty(cookieStr)) {
                Preference.addCustomAppProfile("cookie", cookieStr);
            }
        }

    }
}
