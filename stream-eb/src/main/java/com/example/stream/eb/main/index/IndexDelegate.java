package com.example.stream.eb.main.index;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.stream.core.delegates.bottom.BottomPageDelegate;
import com.example.stream.core.ui.refresh.RefreshHandler;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;
import com.joanzapata.iconify.widget.IconTextView;

import butterknife.BindView;

/**
 * Created by StReaM on 8/21/2017.
 */

public class IndexDelegate extends BottomPageDelegate {
    @BindView(R2.id.rv_index)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.swipe_index)
    SwipeRefreshLayout mSwipeRefreshLayout = null;

    @BindView(R2.id.index_toolbar)
    Toolbar mToolbar = null;

    @BindView(R2.id.index_scan)
    IconTextView mIconScan = null;

    @BindView(R2.id.index_search_view)
    AppCompatEditText mSearchView = null;

    private RefreshHandler mRefreshHandler = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_index;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mRefreshHandler = new RefreshHandler(mSwipeRefreshLayout);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // put here is best
        initSwipeRefreshLayout();
    }

    private void initSwipeRefreshLayout() {
        mSwipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        // first arg let the circle scale from small to big,
        // then big to small
        // the two other indicate the start height and end height
        mSwipeRefreshLayout.setProgressViewOffset(true, 120, 300);
    }
}