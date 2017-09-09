package com.example.stream.core.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import com.example.stream.core.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * Created by StReaM on 9/7/2017.
 */

public class RatingLayout extends LinearLayoutCompat implements View.OnClickListener{

    private static final CharSequence ICON_UNSELECTED = "{fa-star-o}";
    private static final CharSequence ICON_SELECTED = "{fa-star}";
    private static final int START_TOTAL_COUNT = 5;
    private static final ArrayList<IconTextView> STARS = new ArrayList<>();

    public RatingLayout(Context context) {
        this(context, null);
    }

    public RatingLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initStar();
    }

    private void initStar() {
        for(int i = 0; i < START_TOTAL_COUNT; i++) {
            final IconTextView star = new IconTextView(getContext());
            star.setGravity(Gravity.CENTER);
            final LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.weight = 1; // 平均分配
            star.setLayoutParams(lp);
            star.setText(ICON_UNSELECTED);
            star.setTag(R.id.star_count, i);
            star.setTag(R.id.star_is_selected, false);
            star.setOnClickListener(this);
            STARS.add(star);
            addView(star); // call by viewGroup
        }
    }

    @Override
    public void onClick(View view) {
        final IconTextView star = (IconTextView) view;
        final int count = (int) star.getTag(R.id.star_count);
        final boolean isSelected = (boolean) star.getTag(R.id.star_is_selected);
        if (!isSelected) {
            selectStar(count);
        } else {
            unSelectStar(count);
        }
    }

    private void selectStar(int count) {
        for(int i = 0; i <= count; i++) {
            final IconTextView star = STARS.get(i);
            star.setText(ICON_SELECTED);
            star.setTag(R.id.star_is_selected, true);
            star.setTextColor(AppCompatResources
                    .getColorStateList(getContext(), R.color.gold));
        }
    }

    private void unSelectStar(int count) {
        for(int i = count; i < START_TOTAL_COUNT; i++) {
            final IconTextView star = STARS.get(i);
            star.setText(ICON_UNSELECTED);
            star.setTag(R.id.star_is_selected, false);
            star.setTextColor(Color.GRAY);
        }
    }
}
