package com.example.stream.core.delegates.web;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by StReaM on 8/28/2017.
 */

public class WebViewHelper {

    @SuppressLint("NewApi")
    public WebView createWebView(WebView webView) {
        WebView.setWebContentsDebuggingEnabled(true);
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
