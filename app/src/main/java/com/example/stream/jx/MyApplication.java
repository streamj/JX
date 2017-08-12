package com.example.stream.jx;

import android.app.Application;

import com.example.stream.stream_core.app.StreamCore;

/**
 * Created by StReaM on 8/12/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        StreamCore.init(this)
//                .withApiHost()
                .congfigure();
    }
}
