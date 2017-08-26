package com.example.stream.core.delegates;

/**
 * Created by StReaM on 8/13/2017.
 */

public abstract class StreamDelegate extends PermissionCheckerDelegate {

    @SuppressWarnings("unchecked")
    public <T extends StreamDelegate> T getParentDelegate() {
        return (T) getParentFragment();
    }
}
