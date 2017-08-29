package com.example.stream.core.delegates.web.event;

import com.example.stream.core.util.log.StreamLogger;

/**
 * Created by StReaM on 8/29/2017.
 */

class UndefinedEvent extends Event {
    @Override
    public String execute(String params) {
        StreamLogger.e("Undefined event", params);
        return null;
    }
}
