package com.example.stream.eb.main.shopping;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.DataConverter;

import java.util.ArrayList;

/**
 * Created by StReaM on 8/30/2017.
 */

public class ShopCartDataConverter extends DataConverter {
    @Override
    public ArrayList<ComplexItemEntity> convert() {
        final ArrayList<ComplexItemEntity> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = dataArray.size();
        for(int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final String thumb = data.getString("thumb");
            final String desc = data.getString("desc");
            final String title = data.getString("title");
            final int id = data.getInteger("id");
            final int count = data.getInteger("count");
            final double price = data.getDouble("price");

            final ComplexItemEntity itemEntity = ComplexItemEntity.Builder()
                    .setField(ComplexFields.ITEM_TYPE, ShopCartItemType.SHOP_CART_ITEM)
                    .setField(ComplexFields.IMAGE_URL, thumb)
                    .setField(ShopCartItemFields.DESC, desc)
                    .setField(ShopCartItemFields.TITLE, title)
                    .setField(ComplexFields.ID, id)
                    .setField(ShopCartItemFields.COUNT, count)
                    .setField(ShopCartItemFields.PRICE, price)
                    .setField(ShopCartItemFields.SELECTED, false)
                    .build();

            dataList.add(itemEntity);
        }
        return dataList;
    }
}
