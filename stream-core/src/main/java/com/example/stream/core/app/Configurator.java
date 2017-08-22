package com.example.stream.core.app;

import android.app.Activity;
import android.os.Handler;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * Created by StReaM on 8/12/2017.
 */

public class Configurator {

    private static final HashMap<Object, Object> STREAM_CONFIGS = new HashMap<>();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();
    private static final Handler HANDLER = new Handler();

    private Configurator(){
        STREAM_CONFIGS.put(ConfigKey.CONFIG_READY.name(), false);
        STREAM_CONFIGS.put(ConfigKey.HANDLER, HANDLER);
    }

    public static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final HashMap<Object, Object> getStreamConfigs(){
        return STREAM_CONFIGS;
    }

    private static class Holder {
        private static final Configurator INSTANCE = new Configurator();
    }

    public final void congfigure() {
        initIcons();
        STREAM_CONFIGS.put(ConfigKey.CONFIG_READY.name(), true);
    }

    public final Configurator withApiHost(String host) {
        STREAM_CONFIGS.put(ConfigKey.API_HOST.name(), host);
        return this;
    }

    private void initIcons() {
        if (ICONS.size() > 0) {
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for (int i = 1; i < ICONS.size(); i++) {
                initializer.with(ICONS.get(i));
            }
        }
    }

    public final Configurator withWeChatAppID(String appID) {
        STREAM_CONFIGS.put(ConfigKey.WE_CHAT_APP_ID, appID);
        return this;
    }

    public final Configurator withWeChatAppSecret(String appSecret) {
        STREAM_CONFIGS.put(ConfigKey.WE_CHAT_APP_SECRET, appSecret);
        return this;
    }

    public final Configurator withActivity(Activity activity) {
        STREAM_CONFIGS.put(ConfigKey.ACTIVITY, activity);
        return this;
    }



    public final Configurator withIcon(IconFontDescriptor descriptor) {
        ICONS.add(descriptor);
        return this;
    }

    public final Configurator withInterceptor(Interceptor interceptor) {
        INTERCEPTORS.add(interceptor);
        STREAM_CONFIGS.put(ConfigKey.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors) {
        INTERCEPTORS.addAll(interceptors);
        STREAM_CONFIGS.put(ConfigKey.INTERCEPTOR, INTERCEPTORS);
        return this;
    }

    private void checkConfiguration() {
        final boolean isReady = (boolean) STREAM_CONFIGS.get(ConfigKey.CONFIG_READY.name());
        if(!isReady) {
            throw  new RuntimeException("Configuration was not completed, call configure");
        }
    }

    @SuppressWarnings("unchecked")
    final <T> T getConfiguration(Object key){
        checkConfiguration();
        return (T) STREAM_CONFIGS.get(key);
    }
}
