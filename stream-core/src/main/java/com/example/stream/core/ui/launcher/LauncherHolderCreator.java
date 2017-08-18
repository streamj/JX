package com.example.stream.core.ui.launcher;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by StReaM on 8/18/2017.
 */

public class LauncherHolderCreator implements CBViewHolderCreator<LauncherHolder> {
    @Override
    public LauncherHolder createHolder() {
        return new LauncherHolder();
    }
}
