package com.example.stream.eb.main.cart;

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
import com.example.stream.eb.R2;
import com.joanzapata.iconify.Icon;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by StReaM on 8/30/2017.
 */

public class ShopCartAdapter extends ComplexRecyclerAdapter {

    private boolean mSelectAll = false;
    private ICartItemListener mICartItemListener = null;
    private double mTotalPrice = 0.0;
    private List<ComplexItemEntity> mData = null;
    private int mSize = 0;

    public double getTotalPrice() {
        return mTotalPrice;
    }

    public void totalPriceDecrease(double price) {
        mTotalPrice -= price;
    }

    public void setICartItemListener(ICartItemListener ICartItemListener) {
        mICartItemListener = ICartItemListener;
    }

    private static final RequestOptions OPTIONS = new RequestOptions()
            .dontAnimate()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .centerCrop();

    protected ShopCartAdapter(List<ComplexItemEntity> data) {
        super(data);
        mData = data;
        calculateTotalPrice();
        addItemType(ShopCartItemType.SHOP_CART_ITEM, R.layout.item_shop_cart);
    }

    private void calculateTotalPrice() {
        mTotalPrice = 0;
        for (ComplexItemEntity entity: mData) {
            final boolean selected = entity.getField(ShopCartItemFields.SELECTED);
            if (selected) {
                final double price = entity.getField(ShopCartItemFields.PRICE);
                final int count = entity.getField(ShopCartItemFields.COUNT);
                mTotalPrice += (price * count);
            }
        }
    }

    public void setSelectAll(boolean selected){
        mSelectAll = selected;
        for(ComplexItemEntity entity: mData) {
            entity.setField(ShopCartItemFields.SELECTED, selected);
        }
        calculateTotalPrice();
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
                        mSize =  mData.size();
                        final int currentCount = item.getField(ShopCartItemFields.COUNT);
                        final boolean currentSelect =
                                item.getField(ShopCartItemFields.SELECTED);
                        if (currentSelect) {
                            selectedIText.setTextColor(Color.GRAY);
                            item.setField(ShopCartItemFields.SELECTED, false);
                            mTotalPrice -= price * currentCount;
                            mICartItemListener.onItemCountChange();
                            int i = 0;
                            for(ComplexItemEntity entity: mData) {
                                boolean selected = entity.getField(ShopCartItemFields.SELECTED);
                                if (selected) {
                                    i++;
                                }
                            }
                            if (mSize - i == 1) {
                                mSelectAll = false;
                                if (mICartItemListener != null) {
                                    mICartItemListener.onSelectAllChange(mSelectAll);
                                }
                            }
                        } else {
                            selectedIText.setTextColor(selectedColor);
                            item.setField(ShopCartItemFields.SELECTED, true);
                            mTotalPrice += price * currentCount;
                            mICartItemListener.onItemCountChange();
                            int i = 0;
                            for(ComplexItemEntity entity: mData) {
                                boolean selected = entity.getField(ShopCartItemFields.SELECTED);
                                if (selected) {
                                    i++;
                                }
                            }
                            if (mSize == i) {
                                mSelectAll = true;
                                if (mICartItemListener != null) {
                                    mICartItemListener.onSelectAllChange(mSelectAll);
                                }
                            }
                        }
                    }
                });
                //  这个加减只为模拟，并不是最好的做法
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
                                            boolean selected = item.getField(ShopCartItemFields.SELECTED);
                                            if (selected) {
                                                if (mICartItemListener != null) {
                                                    mTotalPrice -= price;
                                                    mICartItemListener.onItemCountChange();
                                                }
                                            }
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
                                        boolean selected = item.getField(ShopCartItemFields.SELECTED);
                                        if (selected) {
                                            if (mICartItemListener != null) {
                                                mTotalPrice += price;
                                                mICartItemListener.onItemCountChange();
                                            }
                                        }
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
