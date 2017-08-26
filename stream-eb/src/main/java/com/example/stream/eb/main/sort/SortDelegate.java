package com.example.stream.eb.main.sort;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.stream.core.delegates.bottom.BottomPageDelegate;
import com.example.stream.eb.R;
import com.example.stream.eb.main.sort.content.ContentDelegate;
import com.example.stream.eb.main.sort.list.VerticalListDelegate;

/**
 * Created by StReaM on 8/21/2017.
 */

public class SortDelegate extends BottomPageDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_sort;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        final VerticalListDelegate listDelegate = new VerticalListDelegate();
        loadRootFragment(R.id.sort_list_container, listDelegate);
        loadRootFragment(R.id.sort_content_container, ContentDelegate.newInstance(1));
    }
}
