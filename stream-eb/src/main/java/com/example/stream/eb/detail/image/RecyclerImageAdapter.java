package com.example.stream.eb.detail.image;

import android.support.v7.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.ComplexRecyclerAdapter;
import com.example.stream.core.ui.recycler.ComplexViewHolder;
import com.example.stream.core.ui.recycler.ItemType;
import com.example.stream.eb.R;

import java.util.List;

/**
 * Created by StReaM on 9/12/2017.
 */

public class RecyclerImageAdapter extends ComplexRecyclerAdapter {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .centerCrop()
            .dontAnimate();

    protected RecyclerImageAdapter(List<ComplexItemEntity> data) {
        super(data);
        addItemType(ItemType.SINGLE_BIG_IMAGE, R.layout.item_image);
    }

    @Override
    protected void convert(ComplexViewHolder helper, ComplexItemEntity item) {
        super.convert(helper, item);
        final int type  = helper.getItemViewType();
        switch (type) {
            case ItemType.SINGLE_BIG_IMAGE:
                final AppCompatImageView imageView = helper.getView(R.id.image_rv_item);
                final String imgUrl = item.getField(ComplexFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(imgUrl)
                        .apply(OPTIONS)
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
