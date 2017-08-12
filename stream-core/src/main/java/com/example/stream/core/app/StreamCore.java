package com.example.stream.core.app;

import android.content.Context;

import java.util.HashMap;

/**
 * Created by StReaM on 8/12/2017.
 */

public final class StreamCore {
    public static Configurator init(Context context) {
        getConfigurations().put(ConfigType.APPLICATION_CONTEXT.name(), context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static HashMap<String, Object> getConfigurations(){
        return Configurator.getInstance().getStreamConfigs();
    }

    public static Context getApplicationContext() {
        return (Context) getConfigurations().get(ConfigType.APPLICATION_CONTEXT.name());
    }
}
