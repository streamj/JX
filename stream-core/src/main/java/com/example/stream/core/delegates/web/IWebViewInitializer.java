package com.example.stream.core.delegates.web;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Created by StReaM on 8/28/2017.
 */

public interface IWebViewInitializer {

    WebView initWebView(WebView webView);

    WebViewClient initWebViewClient();

    WebChromeClient initWebChromeClient();
}
