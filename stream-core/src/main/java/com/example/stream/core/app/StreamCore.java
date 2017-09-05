package com.example.stream.core.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;


/**
 * Created by StReaM on 8/12/2017.
 */

public final class StreamCore {
    public static Configurator init(Context context) {
        Configurator.getInstance()
                .getStreamConfigs()
                .put(ConfigKey.APPLICATION_CONTEXT, context.getApplicationContext());
        return Configurator.getInstance();
    }

    public static Configurator getConfigurator() {
        return Configurator.getInstance();
    }

    public static <T> T getConfigurations(Object key){
        return getConfigurator().getConfiguration(key);
    }

    public static Application getApplicationContext() {
        return getConfigurator().getConfiguration(ConfigKey.APPLICATION_CONTEXT);
    }

    public static Handler getHandler() {
        return getConfigurator().getConfiguration(ConfigKey.HANDLER);
    }
}
