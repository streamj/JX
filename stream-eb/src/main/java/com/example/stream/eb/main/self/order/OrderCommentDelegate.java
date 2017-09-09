package com.example.stream.eb.main.self.order;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.view.AutoPhotoLayout;
import com.example.stream.core.ui.view.RatingLayout;
import com.example.stream.core.util.callback.CallbackManager;
import com.example.stream.core.util.callback.CallbackType;
import com.example.stream.core.util.callback.IGlobalCallback;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import butterknife.BindView;

/**
 * Created by StReaM on 9/7/2017.
 */

public class OrderCommentDelegate extends StreamDelegate {
    @BindView(R2.id.comment_rating_layout)
    RatingLayout mRatingLayout = null;

    @BindView(R2.id.auto_photo_layout)
    AutoPhotoLayout mAutoPhotoLayout = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_order_comment;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mAutoPhotoLayout.setDelegate(this);
        CallbackManager.getInstance()
                .addCallback(CallbackType.ON_CROP, new IGlobalCallback<Uri>() {
                    @Override
                    public void executeCallback(Uri args) {
                        mAutoPhotoLayout.onCrop(args);
                    }
                });
    }
}
