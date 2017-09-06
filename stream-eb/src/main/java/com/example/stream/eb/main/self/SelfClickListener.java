package com.example.stream.eb.main.self;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.main.self.list.ListBean;

import retrofit2.http.DELETE;

/**
 * Created by StReaM on 9/6/2017.
 */

public class SelfClickListener extends SimpleClickListener {
    private final StreamDelegate mDelegate;

    public SelfClickListener(StreamDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean) adapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 1:
                mDelegate.getParentDelegate().start(bean.getDelegate());
                break;
            case 2:
                mDelegate.getParentDelegate().start(bean.getDelegate());
                break;
            default:
                break;
        }
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
