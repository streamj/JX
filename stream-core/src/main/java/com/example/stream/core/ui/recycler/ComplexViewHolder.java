package com.example.stream.core.ui.recycler;

import android.view.View;

import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by StReaM on 8/24/2017.
 */

public class ComplexViewHolder extends BaseViewHolder {
    public ComplexViewHolder(View view) {
        super(view);
    }

    public static ComplexViewHolder create(View view) {
        return new ComplexViewHolder(view);
    }
}
