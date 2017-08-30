package com.example.stream.core.delegates.web;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by StReaM on 8/28/2017.
 */

public class WebViewHelper {

    @SuppressLint({"NewApi", "SetJavaScriptEnabled"})
    public WebView createWebView(WebView webView) {
        WebView.setWebContentsDebuggingEnabled(true);

        // cookie
        final CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(webView, true);
        }
        CookieManager.setAcceptFileSchemeCookies(true);

        // disable horizontal scroll
        webView.setHorizontalScrollBarEnabled(false);
        // disable vertical scroll
        webView.setVerticalScrollBarEnabled(false);
        // allow screenshot
        webView.setDrawingCacheEnabled(true);
        // block long click
        webView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                return true;
            }
        });

        final WebSettings settings = webView.getSettings();
        // enable javascript
        settings.setJavaScriptEnabled(true); // careful
        final String userAgent = settings.getUserAgentString();
        settings.setUserAgentString(userAgent + "polar_bear");
        // hide zoom controls
        settings.setBuiltInZoomControls(false);
        settings.setDisplayZoomControls(false);
        // disable zoom
        settings.setSupportZoom(false);

        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);
        // cache
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);

        return webView;
    }
}
