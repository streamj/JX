package com.example.stream.core.network;


import android.content.Context;

import com.example.stream.core.network.callback.IError;
import com.example.stream.core.network.callback.IFailure;
import com.example.stream.core.network.callback.IRequest;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.LoadStyle;

import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by StReaM on 8/13/2017.
 */

public class RestClientBuilder {

    private String mUrl = null;
    private static final WeakHashMap<String, Object> PARAMS = RestCreator.getParams();
    private IRequest mIRequest = null;
    private ISuccess mISuccess = null;
    private IError mIError = null;
    private IFailure mIFailure = null;
    private RequestBody mBody = null;
    private LoadStyle mLoadStyle = null;
    private Context mContext = null;

    RestClientBuilder() {
    }

    public final RestClientBuilder url(String url) {
        mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String, Object> params) {
        PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key, Object value) {
        PARAMS.put(key, value);
        return this;
    }

    public final RestClientBuilder raw(String raw) {
        mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"), raw);
        return this;
    }

    public final RestClientBuilder request(IRequest request) {
        mIRequest = request;
        return this;
    }

    public final RestClientBuilder success(ISuccess success) {
        mISuccess = success;
        return this;
    }

    public final RestClientBuilder error(IError error) {
        mIError = error;
        return this;
    }

    public final RestClientBuilder failure(IFailure failure) {
        mIFailure = failure;
        return this;
    }

    public final RestClientBuilder loaderStyle(Context context) {
        mContext = context;
        mLoadStyle = LoadStyle.BallClipRotatePulseIndicator;
        return this;
    }

    public final RestClientBuilder loaderStyle(Context context, LoadStyle style) {
        mContext = context;
        mLoadStyle = style;
        return this;
    }

    public final RestClient build(){
        return new RestClient(mUrl, PARAMS, mIRequest, mISuccess, mIError,
                mIFailure, mBody, mLoadStyle, mContext);
    }

}
