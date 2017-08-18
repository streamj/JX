package com.example.stream.core.network.rx;


import android.content.Context;

import com.example.stream.core.network.RestCreator;
import com.example.stream.core.ui.loader.LoadStyle;

import java.io.File;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by StReaM on 8/13/2017.
 */

public class RxRestClientBuilder {

    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private RequestBody mBody = null;
    private LoadStyle mLoadStyle = null;
    private File mFile = null;
    private Context mContext = null;

    RxRestClientBuilder() {
    }

    public final RxRestClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public final RxRestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RxRestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RxRestClientBuilder raw(String raw) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }


    public final RxRestClientBuilder loaderStyle(Context context) {
        mContext = context;
        mLoadStyle = LoadStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RxRestClientBuilder loaderStyle(Context context, LoadStyle style) {
        mContext = context;
        mLoadStyle = style;
        return this;
    }

    public final RxRestClientBuilder file(File file) {
        mFile = file;
        return this;
    }

    public final RxRestClientBuilder file(String path) {
        mFile = new File(path);
        return this;
    }

    public final RxRestClient build(){
        return new RxRestClient(mUrl, PARAMS,  mBody, mLoadStyle, mFile, mContext);
    }

}
