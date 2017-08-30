package com.example.stream.eb.main.shopping;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.ComplexRecyclerAdapter;
import com.example.stream.core.ui.recycler.ComplexViewHolder;
import com.example.stream.eb.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

/**
 * Created by StReaM on 8/30/2017.
 */

public class ShopCartAdapter extends ComplexRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop();

    protected ShopCartAdapter(List<ComplexItemEntity> data) {
        super(data);
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    @Override
    protected void convert(ComplexViewHolder helper, ComplexItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                final int id= item.getField(ComplexFields.ID);
                final String thumb = item.getField(ComplexFields.IMAGE_URL);
                final String title = item.getField(ShopCartItemFields.TITLE);
                final String desc = item.getField(ShopCartItemFields.DESC);
                final int count = item.getField(ShopCartItemFields.COUNT);
                final double price = item.getField(ShopCartItemFields.PRICE);

                final AppCompatImageView imgThumb = helper.getView(R.id.cart_product_image);
                final AppCompatTextView tvTitle = helper.getView(R.id.tv_cart_item_title);
                final AppCompatTextView tvDesc = helper.getView(R.id.tv_cart_item_desc);
                final AppCompatTextView tvCount = helper.getView(R.id.tv_cart_item_count);
                final AppCompatTextView tvPrice = helper.getView(R.id.tv_cart_item_price);
                final IconTextView minus = helper.getView(R.id.icon_item_minus);
                final IconTextView plus = helper.getView(R.id.icon_item_plus);

                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvCount.setText(String.valueOf(count));
                tvPrice.setText(String.valueOf(price));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);
                break;
            default:
                break;
        }
    }
}
