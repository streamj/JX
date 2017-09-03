package com.example.stream.core.delegates.bottom;

import android.widget.Toast;

import com.example.stream.core.R;
import com.example.stream.core.app.StreamCore;
import com.example.stream.core.delegates.StreamDelegate;

/**
 * Created by StReaM on 8/21/2017.
 */

public abstract class BottomPageDelegate extends StreamDelegate {

    // 上次点击的时间
    private long mLastTouchTime = 0;
    // 两次点击间隔
    private static final long TIME_BETWEEN_TWO_TOUCH = 2000;

    @Override
    public boolean onBackPressedSupport() {
        if (System.currentTimeMillis() - mLastTouchTime < TIME_BETWEEN_TWO_TOUCH) {
            _mActivity.finish();
        } else {
            mLastTouchTime = System.currentTimeMillis();
            Toast.makeText(_mActivity,
                    "双击退出" + StreamCore.getApplicationContext().getString(R.string.app_name),
                    Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}
