package com.example.stream.eb.main.self.order;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.DataConverter;

import java.util.ArrayList;

/**
 * Created by StReaM on 9/4/2017.
 */

public class OrderListDataConverter extends DataConverter {
    @Override
    public ArrayList<ComplexItemEntity> convert() {
        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = array.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final double price = data.getDouble("price");
            final String time = data.getString("time");

            final ComplexItemEntity entity = ComplexItemEntity.Builder()
                    .setItemType(OrderListItemType.ITEM_ORDER_LIST)
                    .setField(ComplexFields.ID, id)
                    .setField(ComplexFields.IMAGE_URL, thumb)
                    .setField(ComplexFields.TITLE, title)
                    .setField(OrderItemFields.PRICE,price)
                    .setField(OrderItemFields.TIME, time)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
