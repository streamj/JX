package com.example.stream.eb.main.sort.content;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by StReaM on 8/27/2017.
 */

public class SectionDataConvert {

    final List<SectionBean> convert(String json) {
        final List<SectionBean> dataList = new ArrayList<>();
        final JSONArray dataArray = JSON.parseObject(json).getJSONArray("data");

        final int size = dataArray.size();
        for(int i = 0; i < size; i++) {
            final JSONObject data = dataArray.getJSONObject(i);
            final int id = data.getInteger("id");
            final String title = data.getString("section");

            final SectionBean sectionTitleBean =  new SectionBean(true, title);
            sectionTitleBean.setId(id);
            sectionTitleBean.setMore(true);
            dataList.add(sectionTitleBean);

            final JSONArray products = data.getJSONArray("goods");
            final int productsSize = products.size();
            for(int j = 0; j < productsSize; j++) {
                final JSONObject content = products.getJSONObject(j);
                final int productId = content.getInteger("goods_id");
                final String productName = content.getString("goods_name");
                final String productThumb = content.getString("goods_thumb");

                final SectionContentEntity contentEntity = new SectionContentEntity();
                contentEntity.setProductId(productId);
                contentEntity.setProductName(productName);
                contentEntity.setProductThumb(productThumb);
                dataList.add(new SectionBean(contentEntity));
            }
        }
        return dataList;
    }
}
