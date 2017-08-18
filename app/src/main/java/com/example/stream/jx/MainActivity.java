package com.example.stream.jx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;

import com.example.stream.core.activies.ProxyActivity;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.launcher.LauncherDelegate;
import com.example.stream.eb.launcher.LauncherScrollDelegate;


public class MainActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    @Override
    public StreamDelegate setRootDelegate() {
        return new LauncherDelegate();
    }
}
