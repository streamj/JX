package com.example.stream.core.delegates.web.route;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.webkit.URLUtil;
import android.webkit.WebView;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.delegates.web.BaseWebDelegate;
import com.example.stream.core.delegates.web.WebDelegateImpl;

/**
 * Created by StReaM on 8/28/2017.
 */

public class Router {

    private Router(){

    }

    private static class Holder {
        private static final Router INSTANCE = new Router();
    }

    public static Router getInstance(){
        return Holder.INSTANCE;
    }

    public final boolean handleWebUrl(BaseWebDelegate delegate, String url) {
        if (url.contains("tel:")) {
            callPhone(delegate.getContext(), url);
            return true;
        }

        final StreamDelegate topDelegate = delegate.getTopDelegate();
        final WebDelegateImpl webDelegate = WebDelegateImpl.create(url);
        topDelegate.start(webDelegate);

        return true;
    }

    private void loadWebPage(WebView webView, String url){
        if (webView != null) {
            webView.loadUrl(url);
        } else {
            throw new NullPointerException("webView is null");
        }
    }

    private void loadLocalPage(WebView webView, String url){
        loadWebPage(webView, "file:///android_asset/" + url);
    }

    private void loadPage(WebView webView, String url) {
        if (URLUtil.isNetworkUrl(url) || URLUtil.isAssetUrl(url)) {
            loadWebPage(webView,url);
        } else {
            loadLocalPage(webView, url);
        }
    }

    public void loadPage(BaseWebDelegate delegate, String url) {
        loadPage(delegate.getWebView(), url);
    }

    private void callPhone(Context context, String uri) {
        final Intent intent = new Intent(Intent.ACTION_DIAL);
        final Uri data = Uri.parse(uri);
        intent.setData(data);
        ContextCompat.startActivity(context, intent, null);
    }
}
