package com.example.stream.jx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;

/**
 * Created by StReaM on 8/13/2017.
 */

public class MainDelegate extends StreamDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_main;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
