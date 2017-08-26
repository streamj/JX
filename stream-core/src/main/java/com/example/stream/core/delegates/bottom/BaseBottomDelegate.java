package com.example.stream.core.delegates.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.example.stream.core.R;
import com.example.stream.core.R2;
import com.example.stream.core.delegates.StreamDelegate;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.BindView;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by StReaM on 8/21/2017.
 */
// todo refactor a good name
public abstract class BaseBottomDelegate extends StreamDelegate implements View.OnClickListener {

    private final ArrayList<BottomPageDelegate> PAGE_DELEGATES = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEANS = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean, BottomPageDelegate> ITEMS = new LinkedHashMap<>();
    private int mCurrentDelegate = 0;
    private int mIndexDelegate = 0;
    private int mClickedColor = Color.RED;

    @BindView(R2.id.bottom_bar)
    LinearLayoutCompat mBottomBar;

    public abstract LinkedHashMap<BottomTabBean, BottomPageDelegate> setItems(ItemBuilder builder);

    public abstract int setIndexDelegate();

    @ColorInt
    public abstract int setClickedColor();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mIndexDelegate = setIndexDelegate();
        if (setClickedColor() != 0) {
            mClickedColor = setClickedColor();
        }

        final ItemBuilder builder = ItemBuilder.Builder();
        final LinkedHashMap<BottomTabBean, BottomPageDelegate> items = setItems(builder);
        ITEMS.putAll(items);

        for (Map.Entry<BottomTabBean, BottomPageDelegate> item : ITEMS.entrySet()) {
            final BottomTabBean key = item.getKey();
            final BottomPageDelegate value = item.getValue();
            TAB_BEANS.add(key);
            PAGE_DELEGATES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final int size = ITEMS.size();
        for (int i = 0; i < size; i++) {

            // inflate bottom layout into bottom bar
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout, mBottomBar);

            // get the every single bottom layout
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView iconTextView = (IconTextView) item.getChildAt(0);
            final AppCompatTextView textView = (AppCompatTextView) item.getChildAt(1);
            final BottomTabBean bottomTabBean = TAB_BEANS.get(i);

            iconTextView.setText(bottomTabBean.getIcon());
            textView.setText(bottomTabBean.getTitle());
            if (i == mIndexDelegate) {
                iconTextView.setTextColor(mClickedColor);
                textView.setTextColor(mClickedColor);
            }
        }

        final SupportFragment[] delegateArray = PAGE_DELEGATES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container, mIndexDelegate, delegateArray);
    }

    private void resetColor() {
        final int count = mBottomBar.getChildCount();
        for (int i = 0; i < count; i++) {
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            final IconTextView iconTextView = (IconTextView) item.getChildAt(0);
            final AppCompatTextView textView = (AppCompatTextView) item.getChildAt(1);
            iconTextView.setTextColor(Color.GRAY);
            textView.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View view) {
        final int tag = (int) view.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout) view;
        final IconTextView iconTextView = (IconTextView) item.getChildAt(0);
        final AppCompatTextView textView = (AppCompatTextView) item.getChildAt(1);
        iconTextView.setTextColor(mClickedColor);
        textView.setTextColor(mClickedColor);
        showHideFragment(PAGE_DELEGATES.get(tag), PAGE_DELEGATES.get(mCurrentDelegate));
        // must keep order
        mCurrentDelegate = tag;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_bottom;
    }
}
