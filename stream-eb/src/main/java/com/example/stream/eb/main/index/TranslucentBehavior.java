package com.example.stream.eb.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.example.stream.core.ui.recycler.RgbValue;
import com.example.stream.eb.R;
import com.tencent.mm.opensdk.utils.Log;

/**
 * Created by StReaM on 8/26/2017.
 */

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private int mOffset_Y = 0;
    private static final int velocity = 3;
    private final RgbValue RGB_VALUE = RgbValue.create(1,66,96);

    // must have this constructor, or will get error
    public TranslucentBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, Toolbar child, View dependency) {
        // depend on index recyclerView
        return dependency.getId() == R.id.index_rv;
    }

    @Override
    public boolean onStartNestedScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View directTargetChild, View target, int nestedScrollAxes) {
        return true;
    }

    @Override
    public void onNestedPreScroll(CoordinatorLayout coordinatorLayout, Toolbar child, View target, int dx, int dy, int[] consumed) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed);

        final int toolbarHeight = child.getBottom();

        // 增加滑动距离
        // dy 向上滑动为 +, 向下滑动为 -
        mOffset_Y += dy;
        Log.d("Nested", "the dy is " +dy  + "the offset is " + mOffset_Y + "toolbar height is " + toolbarHeight);

        if (mOffset_Y > 0 && mOffset_Y <= toolbarHeight) {
            final float scale = (float) mOffset_Y / toolbarHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(
                    Color.argb((int)alpha, RGB_VALUE.red(), RGB_VALUE.green(),RGB_VALUE.blue())
            );
        } else if (mOffset_Y > toolbarHeight) {
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(),RGB_VALUE.blue()));
        } else if (mOffset_Y <= 0) {
            // when offset < 0, the view can't scroll, so do not decrease
            mOffset_Y = 0;
        }
    }
}
