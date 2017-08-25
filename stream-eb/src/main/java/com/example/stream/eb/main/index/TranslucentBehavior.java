package com.example.stream.eb.main.index;

import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.View;

import com.example.stream.core.ui.recycler.RgbValue;
import com.example.stream.eb.R;

/**
 * Created by StReaM on 8/26/2017.
 */

public class TranslucentBehavior extends CoordinatorLayout.Behavior<Toolbar> {
    private int mDistanceToTop = 0;
    private static final int velocity = 3;
    private final RgbValue RGB_VALUE = RgbValue.create(51,181,229);

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
        // 增加滑动距离
        mDistanceToTop += dy;
        final int targetHeight = child.getBottom();

        if (mDistanceToTop > 0 && mDistanceToTop <= targetHeight) {
            final float scale = (float) mDistanceToTop/targetHeight;
            final float alpha = scale * 255;
            child.setBackgroundColor(
                    Color.argb((int)alpha, RGB_VALUE.red(), RGB_VALUE.green(),RGB_VALUE.blue())
            );
        } else if (mDistanceToTop > targetHeight) {
            child.setBackgroundColor(Color.rgb(RGB_VALUE.red(), RGB_VALUE.green(),RGB_VALUE.blue()));
        }
    }
}
