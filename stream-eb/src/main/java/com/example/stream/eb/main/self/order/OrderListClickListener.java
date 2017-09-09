package com.example.stream.eb.main.self.order;

import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;

/**
 * Created by StReaM on 9/7/2017.
 */

public class OrderListClickListener extends SimpleClickListener {

    private final OrderListDelegate mOrderListDelegate;

    public OrderListClickListener(OrderListDelegate orderListDelegate) {
        mOrderListDelegate = orderListDelegate;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        mOrderListDelegate.start(new OrderCommentDelegate());
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
