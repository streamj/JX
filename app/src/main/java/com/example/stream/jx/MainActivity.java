package com.example.stream.jx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.example.stream.core.activies.ProxyActivity;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.launcher.LauncherDelegate;
import com.example.stream.eb.launcher.LauncherScrollDelegate;
import com.example.stream.eb.login.ILoginListener;
import com.example.stream.eb.login.SignUpDelegate;


public class MainActivity extends ProxyActivity implements ILoginListener{

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
        return new SignUpDelegate();
    }

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "Sign up success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Log in success!", Toast.LENGTH_LONG).show();
    }
}
