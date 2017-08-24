package com.example.stream.core.ui.banner;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;

/**
 * Created by StReaM on 8/24/2017.
 */

public class BannerImageHolderCreator implements CBViewHolderCreator<BannerImageHolder>{
    @Override
    public BannerImageHolder createHolder() {
        return new BannerImageHolder();
    }
}
