package com.example.stream.eb.main.shopping;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.stream.core.app.StreamCore;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.ComplexRecyclerAdapter;
import com.example.stream.core.ui.recycler.ComplexViewHolder;
import com.example.stream.eb.R;
import com.joanzapata.iconify.widget.IconTextView;
import com.tencent.mm.opensdk.utils.Log;

import java.util.List;

/**
 * Created by StReaM on 8/30/2017.
 */

public class ShopCartAdapter extends ComplexRecyclerAdapter {

    private boolean mSelectAll = false;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop();

    protected ShopCartAdapter(List<ComplexItemEntity> data) {
        super(data);
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    public void setSelectAll(boolean selected){
        mSelectAll = selected;
    }


    @Override
    protected void convert(ComplexViewHolder helper, final ComplexItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ShopCartItemType.SHOP_CART_ITEM:
                final int id = item.getField(ComplexFields.ID);
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
                final IconTextView selectedIText = helper.getView(R.id.cart_icon_select);
                final IconTextView minus = helper.getView(R.id.icon_item_minus);
                final IconTextView plus = helper.getView(R.id.icon_item_plus);
                final IconTextView selectAll = helper.getView(R.id.icon_select_all);

                tvTitle.setText(title);
                tvDesc.setText(desc);
                tvCount.setText(String.valueOf(count));
                tvPrice.setText(String.valueOf(price));
                Glide.with(mContext)
                        .load(thumb)
                        .apply(OPTIONS)
                        .into(imgThumb);

                final int selectedColor = ContextCompat
                        .getColor(StreamCore.getApplicationContext(), R.color.item_choose);

                // 设置全选状态并显示
                item.setField(ShopCartItemFields.SELECTED, mSelectAll);
                final boolean selected = item.getField(ShopCartItemFields.SELECTED);
                if (selected) {
                    selectedIText.setTextColor(selectedColor);
                } else {
                    selectedIText.setTextColor(Color.GRAY);
                }

                // 对单独的 item 进行点击事件，和全局全选状态分开
                // todo 设置和 item 数量相关的 SelectALl
                selectedIText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final boolean currentSelect =
                                item.getField(ShopCartItemFields.SELECTED);
                        if (currentSelect) {
                            selectedIText.setTextColor(Color.GRAY);
                            item.setField(ShopCartItemFields.SELECTED, false);
                        } else {
                            selectedIText.setTextColor(selectedColor);
                            item.setField(ShopCartItemFields.SELECTED, true);
                        }
                    }
                });
                minus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = item.getField(ShopCartItemFields.COUNT);
                        if (currentCount > 1) {
                            RestClient.Builder()
                                    .url("shop_cart_count.php")
//                                    .loaderStyle(mContext)
                                    .params("count", currentCount)
                                    .success(new ISuccess() {
                                        @Override
                                        public void onSuccess(String response) {
                                            int count = currentCount;
                                            count--;
                                            tvCount.setText(String.valueOf(count));
                                            item.setField(ShopCartItemFields.COUNT, count);
                                        }
                                    })
                                    .build().post();
                        }
                    }
                });
                plus.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentCount = item.getField(ShopCartItemFields.COUNT);
                        RestClient.Builder()
                                .url("shop_cart_count.php")
//                                    .loaderStyle(mContext)
                                .params("count", currentCount)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        int count = currentCount;
                                        count++;
                                        tvCount.setText(String.valueOf(count));
                                        item.setField(ShopCartItemFields.COUNT, count);
                                    }
                                })
                                .build().post();
                    }
                });
                break;
            default:
                break;
        }
    }
}
