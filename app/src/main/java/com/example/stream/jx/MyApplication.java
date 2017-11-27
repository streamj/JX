package com.example.stream.jx;

import android.support.multidex.MultiDexApplication;

import com.example.stream.core.app.StreamCore;
import com.example.stream.core.network.rx.AddCookieInterceptor;
import com.example.stream.eb.Icon.FontEbModule;
import com.example.stream.eb.database.DatabaseManager;
import com.example.stream.jx.event.TestEvent;
import com.facebook.stetho.Stetho;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

/**
 * Created by StReaM on 8/12/2017.
 */

public class MyApplication extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        StreamCore.init(this)
                .withApiHost("http://114.67.235.114/RestServer/api/")
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEbModule())
//                .withInterceptor(new DebugInterceptor("index", R.raw.mock))
                .withWeChatAppID("yourappid")
                .withWeChatAppSecret("yoursecrect")
                .withJavaScriptInterface("bear")
                .withWebEvent("test", new TestEvent())
                .withWebHost("https://www.baidu.com/")
                .withInterceptor(new AddCookieInterceptor())
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
