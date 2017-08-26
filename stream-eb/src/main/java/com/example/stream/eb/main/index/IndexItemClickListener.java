package com.example.stream.eb.main.index;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.detail.ProductDetailDelegate;

/**
 * Created by StReaM on 8/26/2017.
 */

public class IndexItemClickListener extends SimpleClickListener {

    private final StreamDelegate DELEGATE;

    private IndexItemClickListener(StreamDelegate delegate) {
        this.DELEGATE = delegate;
    }

    public static IndexItemClickListener create(StreamDelegate delegate) {
        return new IndexItemClickListener(delegate);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ProductDetailDelegate productDetailDelegate = ProductDetailDelegate.create();
        DELEGATE.start(productDetailDelegate);
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
