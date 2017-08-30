package com.example.stream.eb.main.shopping;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stream.core.delegates.bottom.BottomPageDelegate;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by StReaM on 8/30/2017.
 */

public class ShopCartDelegate extends BottomPageDelegate implements ISuccess {
    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;

    private ShopCartAdapter mAdapter = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.Builder()
                .url("shop_cart.php")
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        final ArrayList<ComplexItemEntity> list = new ShopCartDataConverter()
                        .setJsonData(response)
                        .convert();
        mAdapter = new ShopCartAdapter(list);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }
}
