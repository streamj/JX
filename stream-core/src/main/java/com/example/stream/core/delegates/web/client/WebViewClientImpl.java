package com.example.stream.core.delegates.web.client;

import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.stream.core.delegates.web.BaseWebDelegate;
import com.example.stream.core.delegates.web.route.Router;
import com.example.stream.core.util.log.StreamLogger;

/**
 * Created by StReaM on 8/28/2017.
 */

public class WebViewClientImpl extends WebViewClient {
    private final BaseWebDelegate DELEGATE;

    public WebViewClientImpl(BaseWebDelegate delegate) {
        this.DELEGATE = delegate;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        StreamLogger.d("shouldOverFuckingRideUrlLoading");
        return Router.getInstance().handleWebUrl(DELEGATE, url);
    }
}
