package com.example.stream.stream_core.app;

import android.content.Context;

import java.util.WeakHashMap;

/**
 * Created by StReaM on 8/12/2017.
 */

public final class StreamCore {
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    private static WeakHashMap<String, Object> getConfigurations(){
        return Configurator.getInstance().getStreamConfigs();
    }
}
