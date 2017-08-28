package com.example.stream.core.delegates.web.chrome;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Created by StReaM on 8/28/2017.
 */

public class WebChromeClientImpl extends WebChromeClient {

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }
}
