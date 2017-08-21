package com.example.stream.core.delegates.bottom;

import java.util.LinkedHashMap;

/**
 * Created by StReaM on 8/21/2017.
 */

public final class ItemBuilder {

    private final LinkedHashMap<BottomTabBean, BottomPageDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder Builder() {
        return new ItemBuilder();
    }

    public final ItemBuilder addItems(BottomTabBean bean, BottomPageDelegate delegate) {
        ITEMS.put(bean, delegate);
        return this;
    }

    public final ItemBuilder addItems(LinkedHashMap<BottomTabBean, BottomPageDelegate> items) {
        ITEMS.putAll(items);
        return this;
    }

    public final LinkedHashMap<BottomTabBean, BottomPageDelegate> build() {
        return ITEMS;
    }
}
