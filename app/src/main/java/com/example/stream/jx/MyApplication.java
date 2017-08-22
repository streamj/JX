package com.example.stream.jx;

import android.app.Application;

import com.example.stream.core.app.StreamCore;
import com.example.stream.core.network.interceptors.DebugInterceptor;
import com.example.stream.eb.Icon.FontEbModule;
import com.example.stream.eb.database.DatabaseManager;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by StReaM on 8/12/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StreamCore.init(this)
                .withApiHost("http://115.159.35.152/api/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEbModule())
//                .withInterceptor(new DebugInterceptor("index", R.raw.mock))
                .withWeChatAppID("")
                .withWeChatAppSecret("")
                .congfigure();
        initStetho();
        DatabaseManager.getInstance().init(this);
    }

    private void initStetho() {
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                .enableWebKitInspector(Stetho.defaultInspectorModulesProvider(this))
                .build()
        );
    }
}
