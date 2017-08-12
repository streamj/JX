package com.example.stream.jx;

import com.example.stream.core.activies.ProxyActivity;
import com.example.stream.core.delegates.StreamDelegate;


public class MainActivity extends ProxyActivity {

    @Override
    public StreamDelegate setRootDelegate() {
        return new MainDelegate();
    }
}
