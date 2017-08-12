package com.example.stream.stream_core.app;

import java.util.WeakHashMap;

/**
 * Created by StReaM on 8/12/2017.
 */

public class Configurator {

    private static final WeakHashMap<String, Object> STREAM_CONFIGS = new WeakHashMap<>();

    private Configurator(){
        STREAM_CONFIGS.put(ConfigType.CONFIG_READY.name(), false);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final WeakHashMap<String, Object> getStreamConfigs(){
        return STREAM_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void congfigure() {
        STREAM_CONFIGS.put(ConfigType.CONFIG_READY.name(), true);
    }

    public final Configurator withApiHost(String host) {
        STREAM_CONFIGS.put(ConfigType.API_HOST.name(), host);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) STREAM_CONFIGS.get(ConfigType.CONFIG_READY.name());
        if(!isReady) {
            throw  new RuntimeException("Configuration was not completed, call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Enum<ConfigType> key){
        checkConfiguration();
        return (T) STREAM_CONFIGS.get(key.name());
    }
}
