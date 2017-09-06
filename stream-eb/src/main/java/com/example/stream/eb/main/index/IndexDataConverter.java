package com.example.stream.eb.main.index;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.DataConverter;
import com.example.stream.core.ui.recycler.ItemType;
import com.example.stream.core.util.log.StreamLogger;

import java.util.ArrayList;

/**
 * Created by StReaM on 8/22/2017.
 */

public class IndexDataConverter extends DataConverter {
    @Override
    public ArrayList<ComplexItemEntity> convert() {
        final JSONArray jsonArray = JSON.parseObject(getJsonData()).getJSONArray("data");
        final int size = jsonArray.size();
        for (int i = 0; i < size; i++) {
            final JSONObject data = jsonArray.getJSONObject(i);
            final String imageUrl = data.getString("imageUrl");
            final String text = data.getString("text");
            final int spanSize = data.getInteger("spanSize");
            final int id = data.getInteger("goodsId");
            final JSONArray banners = data.getJSONArray("banners");

            final ArrayList<String> bannerImages = new ArrayList<>();
            int type = 0;
            if (imageUrl == null && text != null) {
                type = ItemType.TEXT;
            } else if (imageUrl != null && text == null) {
                type = ItemType.IMAGE;
            } else if (imageUrl != null) {
                type = ItemType.IMAGE_TEXT;
            } else if (banners != null) {
                type = ItemType.BANNER;
                final int bannerSize = banners.size();
                for(int j = 0; j < bannerSize; j++) {
                    final String banner = banners.getString(i);
                    bannerImages.add(banner);
                }
            }
            final ComplexItemEntity entity = ComplexItemEntity.Builder()
                    .setField(ComplexFields.ITEM_TYPE, type)
                    .setField(ComplexFields.SPAN_SIZE, spanSize)
                    .setField(ComplexFields.ID, id)
                    .setField(ComplexFields.TEXT, text)
                    .setField(ComplexFields.IMAGE_URL, imageUrl)
                    .setField(ComplexFields.BANNERS, bannerImages)
                    .build();

            ENTITIES.add(entity);
        }
        return ENTITIES;
    }
}
