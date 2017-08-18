package com.example.stream.eb.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.launcher.ScrollLauncherTag;
import com.example.stream.core.util.storage.Preference;
import com.example.stream.core.util.timer.BaseTimerTask;
import com.example.stream.core.util.timer.ITimerListener;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import java.text.MessageFormat;
import java.util.Timer;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * Created by StReaM on 8/17/2017.
 */

public class LauncherDelegate extends StreamDelegate implements ITimerListener{

    @BindView(R2.id.tv_launcher_timer)
    AppCompatTextView textViewTimer = null;

    private Timer mTimer = null;
    private int count = 5;

    @OnClick(R2.id.tv_launcher_timer)
    void OnClickTimer(){
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
            checkIfShowSrcoll();
        }
    }

    private void initTimer(){
        mTimer = new Timer();
        final BaseTimerTask baseTimerTask = new BaseTimerTask(this);
        // 马上开始倒计时，每个1000ms 处理一次
        mTimer.schedule(baseTimerTask, 0, 1000);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initTimer();
    }

    private void checkIfShowSrcoll() {
        // 如果不是第一次登录
        if (!Preference.getAppFlag(ScrollLauncherTag.LAUNCH_FIRST_TIME.name())) {
            start(new LauncherScrollDelegate(), SINGLETASK);
        } else {
            // todo 检查用户是否登录了 app
        }
    }


    @Override
    public void onTimer() {
        getProxyActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (textViewTimer != null) {
                    textViewTimer.setText(MessageFormat.format("跳过\n{0}s",count));
                    count--;
                    if (count < 0) {
                        if (mTimer != null) {
                            mTimer.cancel();
                            mTimer = null;
                            checkIfShowSrcoll();
                        }
                    }
                }
            }
        });
    }
}
