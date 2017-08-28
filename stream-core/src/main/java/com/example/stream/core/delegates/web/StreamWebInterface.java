package com.example.stream.core.delegates.web;

import com.alibaba.fastjson.JSON;

/**
 * Created by StReaM on 8/28/2017.
 */

public class StreamWebInterface {
    private final WebDelegate DELEGATE;

    private StreamWebInterface(WebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    static StreamWebInterface create(WebDelegate delegate) {
        return new StreamWebInterface(delegate);
    }

    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        return null;
    }
}
