package com.example.stream.eb.main.shopping;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ViewStubCompat;
import android.view.View;
import android.widget.Toast;

import com.example.stream.core.delegates.bottom.BottomPageDelegate;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;
import com.joanzapata.iconify.widget.IconTextView;
import com.tencent.mm.opensdk.utils.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by StReaM on 8/30/2017.
 */

public class ShopCartDelegate extends BottomPageDelegate
        implements ISuccess, ICartItemListener {
    private ShopCartAdapter mAdapter = null;

    @BindView(R2.id.rv_shop_cart)
    RecyclerView mRecyclerView = null;
    @BindView(R2.id.icon_select_all)
    IconTextView mIconSelectAll = null;
    @BindView(R2.id.stub_empty_cart)
    ViewStubCompat mEmptyCartStub = null;
    @BindView(R2.id.tv_cart_total_price)
    AppCompatTextView mTvTotalPrice = null;

    // 对整个 recyclerView 设置全选
    @OnClick(R2.id.icon_select_all)
    void onClickSelectAll(View view) {
        final int tag = (int) mIconSelectAll.getTag();
        if (tag == 0) {
            mIconSelectAll
                    .setTextColor(ContextCompat.getColor(getContext(), R.color.item_choose));
            mIconSelectAll.setTag(1);
            mAdapter.setSelectAll(true);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        } else {
            mIconSelectAll.setTextColor(Color.GRAY);
            mIconSelectAll.setTag(0);
            mAdapter.setSelectAll(false);
            mAdapter.notifyItemRangeChanged(0, mAdapter.getItemCount());
        }
    }

    @OnClick(R2.id.tv_delete_product)
    void onClickDelete() {
        // 没法一边迭代一边删除元素
        final List<ComplexItemEntity> dataList = mAdapter.getData();
        final List<Integer> deleteIndex = new ArrayList<>();
        final int size = dataList.size();
        for (int i = 0; i < size; i++) {
            ComplexItemEntity entity = dataList.get(i);
            final boolean selected = entity.getField(ShopCartItemFields.SELECTED);
            if (selected) {
                deleteIndex.add(i);
            }
        }
        // reverse deleting, prevent from IndexOutOfBoundsException
        Collections.reverse(deleteIndex);
        double priceDecrese = 0.0;
        for (Integer i : deleteIndex) {
            ComplexItemEntity entity = dataList.get(i);
            int count = entity.getField(ShopCartItemFields.COUNT);
            double price = entity.getField(ShopCartItemFields.PRICE);
            dataList.remove((int)i);
            priceDecrese += (count * price);
        }
        mAdapter.totalPriceDecrease(priceDecrese);
        mTvTotalPrice.setText(String.valueOf(mAdapter.getTotalPrice()));
        mAdapter.notifyDataSetChanged();
        checkItemCounnt();
    }

    @OnClick(R2.id.tv_clear_cart)
    void onClearCart() {
        mAdapter.getData().clear();
        mAdapter.notifyDataSetChanged();
        checkItemCounnt();
    }

    private void checkItemCounnt() {
        final int count = mAdapter.getItemCount();
        if (count == 0) {
            final View stubView = mEmptyCartStub.inflate();
            final AppCompatTextView emptyCartText =
                    (AppCompatTextView)stubView.findViewById(R.id.tv_stub_empty_cart);
            emptyCartText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getContext(), "购物车是空的啊",
                            Toast.LENGTH_LONG).show();
                }
            });
            mRecyclerView.setVisibility(View.GONE);
        } else {
            mRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_shop_cart;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mIconSelectAll.setTag(0);
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
        mAdapter.setICartItemListener(this);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        mTvTotalPrice.setText(String.valueOf(mAdapter.getTotalPrice()));
        checkItemCounnt();
    }

    @Override
    public void onItemCountChange(double changedPrice) {
        final double totalPrice = mAdapter.getTotalPrice();
        mTvTotalPrice.setText(String.valueOf(totalPrice));
    }
}
