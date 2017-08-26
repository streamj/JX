package com.example.stream.eb.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.stream.core.delegates.StreamDelegate;

/**
 * Created by StReaM on 8/26/2017.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final StreamDelegate DELEGATE;

    private IndexItemClickListener(StreamDelegate DELEGATE) {
        this.DELEGATE = DELEGATE;
    }

    public IndexItemClickListener create(StreamDelegate DELEGATE) {
        return new IndexItemClickListener(DELEGATE);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
