package com.example.stream.core.util.callback;

import java.util.WeakHashMap;

/**
 * Created by StReaM on 9/5/2017.
 */

public class CallbackManager {

    private static final WeakHashMap<Object, IGlobalCallback> CALLBACKS
            = new WeakHashMap<>();

    private static class Holder {
        private static final CallbackManager INSTANCE = new CallbackManager();
    }

    public static CallbackManager getInstance() {
        return Holder.INSTANCE;
    }

    public CallbackManager addCallback(Object obj, IGlobalCallback callback) {
        CALLBACKS.put(obj, callback);
        return this;
    }

    public IGlobalCallback getCallback(Object obj) {
        return CALLBACKS.get(obj);
    }
}
