package com.example.stream.eb.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import butterknife.BindView;

/**
 * Created by StReaM on 9/11/2017.
 */

public class ProductInfoDelegate extends StreamDelegate {
    @BindView(R2.id.tv_product_info_title)
    AppCompatTextView mInfo = null;
    @BindView(R2.id.tv_product_info_desc)
    AppCompatTextView mDesc = null;
    @BindView(R2.id.tv_product_info_price)
    AppCompatTextView mPrice = null;

    private static final String PRODUCT_INFO = "PRODUCT_INFO";
    private JSONObject mData = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_product_info;
    }

    public static ProductInfoDelegate create(String info) {
        final Bundle args = new Bundle();
        args.putString(PRODUCT_INFO, info);
        ProductInfoDelegate delegate = new ProductInfoDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        String info = args.getString(PRODUCT_INFO);
        mData = JSON.parseObject(info);
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        String name = mData.getString("name");
        String desc = mData.getString("description");
        double price = mData.getDouble("price");
        mInfo.setText(name);
        mDesc.setText(desc);
        mPrice.setText(String.valueOf(price));
    }
}
