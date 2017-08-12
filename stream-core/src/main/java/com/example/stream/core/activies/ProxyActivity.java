package com.example.stream.core.activies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.ContentFrameLayout;

import com.example.stream.core.R;
import com.example.stream.core.delegates.StreamDelegate;

import me.yokeyword.fragmentation.SupportActivity;

/**
 * Created by StReaM on 8/12/2017.
 */

public abstract class ProxyActivity extends SupportActivity {

    public abstract StreamDelegate setRootDelegate();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContaineer(savedInstanceState);
    }

    private void initContaineer(@Nullable Bundle savedInstanceState){
        final ContentFrameLayout container = new ContentFrameLayout(this);
        container.setId(R.id.delegate_container);

        setContentView(container);
        if (savedInstanceState == null) {
            loadRootFragment(R.id.delegate_container, setRootDelegate());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        System.gc();
        System.runFinalization();
    }
}
