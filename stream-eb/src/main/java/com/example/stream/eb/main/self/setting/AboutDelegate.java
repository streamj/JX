package com.example.stream.eb.main.self.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;

/**
 * Created by StReaM on 9/7/2017.
 */

public class AboutDelegate extends StreamDelegate {

    @Override
    public Object setLayout() {
        return R.layout.delegate_about;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
