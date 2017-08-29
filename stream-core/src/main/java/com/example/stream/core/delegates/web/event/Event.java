package com.example.stream.core.delegates.web.event;

import android.content.Context;
import android.webkit.WebView;

import com.example.stream.core.delegates.BaseDelegate;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.delegates.web.BaseWebDelegate;


/**
 * Created by StReaM on 8/29/2017.
 */

public abstract class Event implements IEvent {
    private Context mContext = null;
    private String mAction = null;
    private BaseWebDelegate mDelegate = null;
    private String mUrl = null;
    private WebView mWebView = null;

    public WebView getWebView() {
        return mDelegate.getWebView();
    }

    public void setWebView(WebView webView) {
        mWebView = webView;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public String getAction() {
        return mAction;
    }

    public void setAction(String action) {
        mAction = action;
    }

    public StreamDelegate getDelegate() {
        return mDelegate;
    }

    public void setDelegate(BaseWebDelegate delegate) {
        mDelegate = delegate;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String url) {
        mUrl = url;
    }
}
