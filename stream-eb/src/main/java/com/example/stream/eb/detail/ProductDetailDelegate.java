package com.example.stream.eb.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;

import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by StReaM on 8/26/2017.
 */

public class ProductDetailDelegate extends StreamDelegate {

    public static ProductDetailDelegate create() {
        return new ProductDetailDelegate();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_product_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 水平动画
        return new DefaultHorizontalAnimator();
    }
}
