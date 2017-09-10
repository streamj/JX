package com.example.stream.eb.main.index.search;

import android.support.v7.widget.AppCompatTextView;

import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.ComplexRecyclerAdapter;
import com.example.stream.core.ui.recycler.ComplexViewHolder;
import com.example.stream.eb.R;

import java.util.List;

/**
 * Created by StReaM on 9/10/2017.
 */

public class SearchAdapter extends ComplexRecyclerAdapter {

    protected SearchAdapter(List<ComplexItemEntity> data) {
        super(data);
        addItemType(SearchItemType.ITEM_SEARCH, R.layout.item_search);
    }

    @Override
    protected void convert(ComplexViewHolder helper, ComplexItemEntity item) {
        super.convert(helper, item);
        switch (item.getItemType()) {
            case SearchItemType.ITEM_SEARCH:
                AppCompatTextView searchTv= helper.getView(R.id.tv_search_item);
                String history = item.getField(ComplexFields.TEXT);
                searchTv.setText(history);
                break;
            default:
                break;
        }
    }
}
