package com.example.stream.core.ui.recycler;

import java.util.LinkedHashMap;
import java.util.WeakHashMap;

/**
 * Created by StReaM on 8/22/2017.
 */

public class ComplexItemEntityBuilder {

    private static final LinkedHashMap<Object, Object> FIELDS = new LinkedHashMap<>();

    public ComplexItemEntityBuilder() {
        // clear last data
        FIELDS.clear();
    }

    public final ComplexItemEntityBuilder setItemType(int itemType) {
        FIELDS.put(ComplexFields.ITEM_TYPE, itemType);
        return this;
    }

    public final ComplexItemEntityBuilder setField(Object key, Object value) {
        FIELDS.put(key, value);
        return this;
    }

    public final ComplexItemEntityBuilder setFileds(LinkedHashMap<?, ?> fields) {
        FIELDS.putAll(fields);
        return this;
    }

    public final ComplexItemEntity build() {
        return new ComplexItemEntity(FIELDS);
    }
}
