package com.example.stream.jx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.widget.Toast;

import com.example.stream.core.activies.ProxyActivity;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.launcher.ILauncherListener;
import com.example.stream.core.ui.launcher.OnLauncherFinishTag;
import com.example.stream.eb.launcher.LauncherDelegate;
import com.example.stream.eb.launcher.LauncherScrollDelegate;
import com.example.stream.eb.login.ILoginListener;
import com.example.stream.eb.login.SignUpDelegate;


public class MainActivity extends ProxyActivity implements ILoginListener, ILauncherListener{

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

    @Override
    public void onSignUpSuccess() {
        Toast.makeText(this, "Sign up success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLoginSuccess() {
        Toast.makeText(this, "Log in success!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onLaunchFinish(OnLauncherFinishTag tag) {
        switch (tag) {
            case LOGGED_IN:
                Toast.makeText(this, "You are official user", Toast.LENGTH_LONG).show();
                startWithPop(new MainDelegate());
                break;
            case LOGGED_OUT:
                Toast.makeText(this, "You are tourist", Toast.LENGTH_LONG).show();
                startWithPop(new SignUpDelegate());
                break;
            default:
                break;
        }
    }
}
