package com.example.stream.eb.main.self.setting;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.main.self.list.ListBean;

/**
 * Created by StReaM on 9/7/2017.
 */

public class SettingClickListener extends SimpleClickListener {
    private StreamDelegate mDelegate;

    public SettingClickListener(StreamDelegate delegate) {
        mDelegate = delegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        final ListBean bean = (ListBean)adapter.getData().get(position);
        int id = bean.getId();
        switch (id) {
            case 3:
                //这是消息推送的开关
                break;
            case 2:
                mDelegate.getSupportDelegate().start(bean.getDelegate());
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
