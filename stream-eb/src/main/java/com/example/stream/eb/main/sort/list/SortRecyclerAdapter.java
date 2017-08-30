package com.example.stream.eb.main.sort.list;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.ComplexRecyclerAdapter;
import com.example.stream.core.ui.recycler.ComplexViewHolder;
import com.example.stream.core.ui.recycler.ItemType;
import com.example.stream.eb.R;
import com.example.stream.eb.main.sort.SortDelegate;
import com.example.stream.eb.main.sort.content.ContentDelegate;
import com.tencent.mm.opensdk.utils.Log;

import java.util.List;

/**
 * Created by StReaM on 8/27/2017.
 */

public class SortRecyclerAdapter extends ComplexRecyclerAdapter {

    private final SortDelegate DELEGATE;
    private int mPrePosition = 0;

    protected SortRecyclerAdapter(List<ComplexItemEntity> data, SortDelegate delegate) {
        super(data);
        this.DELEGATE = delegate;
        addItemType(ItemType.VERTICAL_MENU_LIST, R.layout.item_vertical_menu_list);
    }

    @Override
    protected void convert(final ComplexViewHolder helper, final ComplexItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case ItemType.VERTICAL_MENU_LIST:
                final String name = item.getField(ComplexFields.NAME);
                final boolean choosed = item.getField(ComplexFields.TAG);
                final AppCompatTextView atv = helper.getView(R.id.tv_vertical_item_name);
                final View line = helper.getView(R.id.view_line);
                // get the whole view
                final View itemView = helper.itemView;
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final int currentPosition = helper.getAdapterPosition();
                        if (mPrePosition != currentPosition) {
                            getData().get(mPrePosition).setField(ComplexFields.TAG, false);
                            notifyItemChanged(mPrePosition);

                            item.setField(ComplexFields.TAG, true);
                            notifyItemChanged(currentPosition);

                            mPrePosition = currentPosition;

                            final int contentId = getData()
                                    .get(currentPosition).getField(ComplexFields.ID);
                            showContent(contentId);
                        }
                    }
                });
                if (!choosed) {
                    line.setVisibility(View.INVISIBLE);
                    atv.setTextColor(ContextCompat.getColor(mContext, R.color.we_chat_black));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                } else {
                    line.setVisibility(View.VISIBLE);
                    line.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_choose));
                    atv.setTextColor(ContextCompat.getColor(mContext, R.color.item_choose));
                    itemView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.item_background));
                }
                atv.setText(name);
                break;
            default:
                break;
        }
    }

    private void showContent(int contentId) {
        final ContentDelegate contentDelegate = ContentDelegate.newInstance(contentId);
        switchContent(contentDelegate);
    }

    private void switchContent(ContentDelegate contentDelegate) {
        final StreamDelegate delegate= DELEGATE.findChildFragment(ContentDelegate.class);
        if (delegate != null) {
            delegate.replaceFragment(contentDelegate, false);
        }
    }
}
