package com.example.stream.eb.main.sort.list;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.DataConverter;
import com.example.stream.core.ui.recycler.ItemType;

import java.util.ArrayList;

/**
 * Created by StReaM on 8/27/2017.
 */

public final class VerticalListDataConvert extends DataConverter{
    @Override
    public ArrayList<ComplexItemEntity> convert() {
        final ArrayList<ComplexItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON
                .parseObject(getJsonData())
                .getJSONObject("data")
                .getJSONArray("list");
        final int size = dataArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String name = data.getString("name");

            final ComplexItemEntity entity = ComplexItemEntity.Builder()
                    .setField(ComplexFields.ITEM_TYPE, ItemType.VERTICAL_MENU_LIST)
                    .setField(ComplexFields.ID, id)
                    .setField(ComplexFields.NAME, name)
                    .setField(ComplexFields.TAG, false)
                    .build();
            dataList.add(entity);
            // 设置第一个数据为被选中状态
            dataList.get(0).setField(ComplexFields.TAG, true);
        }
        return dataList;
    }
}
