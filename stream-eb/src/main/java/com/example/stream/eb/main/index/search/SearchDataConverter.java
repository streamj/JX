package com.example.stream.eb.main.index.search;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONArray;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.DataConverter;
import com.example.stream.core.util.storage.StreamPreference;

import java.util.ArrayList;

/**
 * Created by StReaM on 9/10/2017.
 */

public class SearchDataConverter extends DataConverter {

    public static final String TAG_SEARCH_HISTORY = "search_history";

    @Override
    public ArrayList<ComplexItemEntity> convert() {
        String jsonStr = StreamPreference.getCustomAppProfile(TAG_SEARCH_HISTORY);
        if (!TextUtils.isEmpty(jsonStr)) {
            JSONArray array = JSONArray.parseArray(jsonStr);
            int size = array.size();
            for (int i = 0; i < size; i++) {
                String itemText = array.getString(i);
                ComplexItemEntity entity = ComplexItemEntity.Builder()
                        .setItemType(SearchItemType.ITEM_SEARCH)
                        .setField(ComplexFields.TEXT, itemText)
                        .build();
                ENTITIES.add(entity);
            }
        }
        return ENTITIES;
    }
}
