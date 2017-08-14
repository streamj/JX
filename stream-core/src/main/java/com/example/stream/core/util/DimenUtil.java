package com.example.stream.core.util;

import android.content.res.Resources;
import android.util.DisplayMetrics;

import com.example.stream.core.app.StreamCore;

/**
 * Created by StReaM on 8/14/2017.
 */

public class DimenUtil {

    public static int getScreenWidth() {
        final Resources resources = StreamCore.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.widthPixels;
    }

    public static int getScreenHeight() {
        final Resources resources = StreamCore.getApplicationContext().getResources();
        final DisplayMetrics dm = resources.getDisplayMetrics();

        return dm.heightPixels;
    }
}
