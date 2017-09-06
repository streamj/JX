package com.example.stream.core.ui.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.stream.core.R;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.banner.BannerCreator;
import com.example.stream.core.ui.loader.StreamLoader;
import com.example.stream.core.util.log.StreamLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StReaM on 8/24/2017.
 */

public class ComplexRecyclerAdapter
        extends BaseMultiItemQuickAdapter<ComplexItemEntity, ComplexViewHolder>
        implements BaseQuickAdapter.SpanSizeLookup, OnItemClickListener{

    private static final RequestOptions OPTIONS = new RequestOptions();

    private boolean mIsBannerInitialized = false;

    protected ComplexRecyclerAdapter(List<ComplexItemEntity> data) {
        super(data);
        init();
    }

    private void init() {
        // 父类方法
        addItemType(ItemType.TEXT, R.layout.item_complex_text);
        addItemType(ItemType.IMAGE, R.layout.item_complex_image);
        addItemType(ItemType.IMAGE_TEXT, R.layout.item_complex_image_text);
        addItemType(ItemType.BANNER, R.layout.item_complex_banner);
        // 设置宽度监听
        setSpanSizeLookup(this);
        openLoadAnimation();
        // 多次执行动画
        isFirstOnly(false);

        OPTIONS.dontAnimate()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop();

    }

    public static ComplexRecyclerAdapter create(List<ComplexItemEntity> data) {
        return new ComplexRecyclerAdapter(data);
    }

    public static ComplexRecyclerAdapter create(DataConverter converter) {
        return new ComplexRecyclerAdapter(converter.convert());
    }

    @Override
    protected void convert(ComplexViewHolder helper, ComplexItemEntity item) {
        final String text;
        final String imageUrl;
        final ArrayList<String> bannerImages;
        switch (helper.getItemViewType()) {
            case ItemType.TEXT:
                text = item.getField(ComplexFields.TEXT);
                helper.setText(R.id.complex_text, text);
                break;
            case ItemType.IMAGE:
                imageUrl = item.getField(ComplexFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.complex_img));
                break;
            case ItemType.BANNER:
                if (!mIsBannerInitialized) {
                    bannerImages = item.getField(ComplexFields.BANNERS);
                    final ConvenientBanner<String> convenientBanner = helper.getView(R.id.rv_banner);
                    BannerCreator.setDefault(convenientBanner, bannerImages, this);
                    mIsBannerInitialized = true;
                }
                break;
            case ItemType.IMAGE_TEXT:
                text = item.getField(ComplexFields.TEXT);
                imageUrl = item.getField(ComplexFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.complex_img_text_img));
                helper.setText(R.id.complex_img_text_text, text);
                break;
            default:
                break;
        }
    }

    @Override
    protected ComplexViewHolder createBaseViewHolder(View view) {
        return ComplexViewHolder.create(view);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(ComplexFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
