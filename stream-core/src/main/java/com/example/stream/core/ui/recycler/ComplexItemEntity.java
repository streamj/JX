package com.example.stream.core.ui.recycler;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.LinkedHashMap;


/**
 * Created by StReaM on 8/22/2017.
 */

public class ComplexItemEntity implements MultiItemEntity {
    private final ReferenceQueue<LinkedHashMap<Object, Object>> ITEM_QUEUE = new ReferenceQueue<>();
    private final LinkedHashMap<Object, Object> MULTIPLE_FIELDS = new LinkedHashMap<>();
    private final SoftReference<LinkedHashMap<Object, Object>> FIELDS_REF =
            new SoftReference<>(MULTIPLE_FIELDS, ITEM_QUEUE);

    public ComplexItemEntity(LinkedHashMap<?,?> fields) {
        FIELDS_REF.get().putAll(fields);
    }

    public static ComplexItemEntityBuilder Builder(){
        return new ComplexItemEntityBuilder();
    }

    @Override
    public int getItemType() {
        return (int)FIELDS_REF.get().get(ComplexFields.ITEM_TYPE);
    }

    public final <T> T getField(Object key) {
        return (T) FIELDS_REF.get().get(key);
    }

    public final LinkedHashMap<?, ?> getField() {
        return FIELDS_REF.get();
    }

    public final ComplexItemEntity setField(Object key, Object value){
        FIELDS_REF.get().put(key,value);
        return this;
    }
}
