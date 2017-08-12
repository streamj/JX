package com.example.stream.jx;

import android.app.Application;

import com.example.stream.core.app.StreamCore;
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
//                .withApiHost()
                .withIcon(new FontAwesomeModule())
                .withIcon(new FontEbModule())
                .congfigure();
    }
}
