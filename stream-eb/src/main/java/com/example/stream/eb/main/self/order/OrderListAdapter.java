package com.example.stream.eb.main.self.order;

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

import java.util.List;

/**
 * Created by StReaM on 9/4/2017.
 */

public class OrderListAdapter extends ComplexRecyclerAdapter {

    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    protected OrderListAdapter(List<ComplexItemEntity> data) {
        super(data);
        addItemType(OrderListItemType.ITEM_ORDER_LIST, R.layout.item_order_list);
    }

    @Override
    protected void convert(ComplexViewHolder helper, ComplexItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case OrderListItemType.ITEM_ORDER_LIST:
                final AppCompatImageView imageView = helper.getView(R.id.image_order_list);
                final AppCompatTextView tvPrice = helper.getView(R.id.tv_order_list_price);
                final AppCompatTextView tvTitle = helper.getView(R.id.tv_order_list_title);
                final AppCompatTextView tvTime = helper.getView(R.id.tv_order_list_time);

                final String title = item.getField(ComplexFields.TITLE);
                final String imageUrl = item.getField(ComplexFields.IMAGE_URL);
                final double price = item.getField(OrderItemFields.PRICE);
                final String time = item.getField(OrderItemFields.TIME);
                tvPrice.setText(String.valueOf(price));
                tvTime.setText(time);
                tvTitle.setText(title);
                Glide.with(mContext)
                        .load(imageUrl)
                        .apply(OPTIONS)
                        .into(imageView);
                break;
            default:
                break;
        }
    }
}
