package com.example.stream.core.ui.scanner;

import android.content.Context;
import android.util.AttributeSet;

import me.dm7.barcodescanner.core.IViewFinder;
import me.dm7.barcodescanner.zbar.ZBarScannerView;

/**
 * Created by StReaM on 9/9/2017.
 */

public class ScanView extends ZBarScannerView {
    public ScanView(Context context) {
        this(context, null);
    }

    public ScanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    @Override
    protected IViewFinder createViewFinderView(Context context) {
        return new StreamViewFinderView(context);
    }
}
