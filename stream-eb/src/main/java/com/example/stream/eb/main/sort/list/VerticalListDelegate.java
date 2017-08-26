package com.example.stream.eb.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;

/**
 * Created by StReaM on 8/26/2017.
 */

public class VerticalListDelegate extends StreamDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
