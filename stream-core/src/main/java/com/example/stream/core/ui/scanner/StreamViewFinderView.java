package com.example.stream.core.ui.scanner;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.ViewFinderView;

/**
 * Created by StReaM on 9/9/2017.
 */

public class StreamViewFinderView extends ViewFinderView {
    public StreamViewFinderView(Context context) {
        this(context, null);
    }

    public StreamViewFinderView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        // 正方形
        mSquareViewFinder = true;
        mBorderPaint.setColor(Color.GREEN);
        mLaserPaint.setColor(Color.GREEN);
    }
}
