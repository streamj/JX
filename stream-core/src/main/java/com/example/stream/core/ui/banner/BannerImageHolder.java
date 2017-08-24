package com.example.stream.core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

/**
 * Created by StReaM on 8/24/2017.
 */

public class BannerImageHolder implements Holder<String> {

    private AppCompatImageView mImageView = null;
    private RequestOptions mOptions = new RequestOptions();

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        mOptions.dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context)
                .load(data)
                .apply(mOptions)
                .into(mImageView);
    }
}
