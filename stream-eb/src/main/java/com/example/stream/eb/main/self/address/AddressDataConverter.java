package com.example.stream.eb.main.self.address;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.DataConverter;

import java.util.ArrayList;

/**
 * Created by StReaM on 9/6/2017.
 */

public class AddressDataConverter extends DataConverter {
    @Override
    public ArrayList<ComplexItemEntity> convert() {
        final JSONArray array = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = array.size();
        for(int i = 0; i< size; i++) {
            final JSONObject object = array.getJSONObject(i);
            final int id = object.getInteger("id");
            final String name = object.getString("name");
            final String phone = object.getString("phone");
            final String address = object.getString("address");
            final boolean isDefault = object.getBoolean("default");

            final ComplexItemEntity entity = ComplexItemEntity.Builder()
                    .setItemType(AddressItemType.ITEM_ADDRESS)
                    .setField(ComplexFields.ID, id)
                    .setField(ComplexFields.NAME, name)
                    .setField(AddressFields.ADDRESS, address)
                    .setField(AddressFields.PHONE, phone)
                    .setField(ComplexFields.TAG, isDefault)
                    .build();
            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
