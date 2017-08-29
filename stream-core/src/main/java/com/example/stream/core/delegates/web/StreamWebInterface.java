package com.example.stream.core.delegates.web;

import android.webkit.JavascriptInterface;

import com.alibaba.fastjson.JSON;
import com.example.stream.core.delegates.web.event.Event;
import com.example.stream.core.delegates.web.event.EventManager;

/**
 * Created by StReaM on 8/28/2017.
 */

class StreamWebInterface {
    private final BaseWebDelegate DELEGATE;

    private StreamWebInterface(BaseWebDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    static StreamWebInterface create(BaseWebDelegate delegate) {
        return new StreamWebInterface(delegate);
    }

    @JavascriptInterface
    public String event(String params){
        final String action = JSON.parseObject(params).getString("action");
        final Event event = EventManager.getInstance().createEvent(action);
        if (event != null) {
            event.setAction(action);
            event.setDelegate(DELEGATE);
            event.setContext(DELEGATE.getContext());
            event.setUrl(DELEGATE.getUrl());
            return event.execute(params);
        }
        return null;
    }
}
