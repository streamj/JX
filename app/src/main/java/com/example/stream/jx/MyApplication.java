package com.example.stream.jx;

import android.app.Application;

import com.example.stream.core.app.StreamCore;
import com.example.stream.core.network.interceptors.DebugInterceptor;
import com.example.stream.eb.Icon.FontEbModule;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by StReaM on 8/12/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StreamCore.init(this)
                .withApiHost("http://127.0.0.1")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEbModule())
                .withInterceptor(new DebugInterceptor("index", R.raw.mock))
                .congfigure();
    }
}
