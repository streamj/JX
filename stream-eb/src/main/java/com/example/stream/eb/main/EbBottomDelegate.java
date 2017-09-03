package com.example.stream.eb.main;

import android.graphics.Color;

import com.example.stream.core.delegates.bottom.BaseBottomDelegate;
import com.example.stream.core.delegates.bottom.BottomPageDelegate;
import com.example.stream.core.delegates.bottom.BottomTabBean;
import com.example.stream.core.delegates.bottom.ItemBuilder;
import com.example.stream.eb.main.discover.DiscoverDelegate;
import com.example.stream.eb.main.index.IndexDelegate;
import com.example.stream.eb.main.cart.ShopCartDelegate;
import com.example.stream.eb.main.sort.SortDelegate;

import java.util.LinkedHashMap;

/**
 * Created by StReaM on 8/21/2017.
 */

public class EbBottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomPageDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean, BottomPageDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","主页"), new IndexDelegate());
        items.put(new BottomTabBean("{fa-sort}","分类"), new SortDelegate());
        items.put(new BottomTabBean("{fa-compass}","发现"), new DiscoverDelegate());
        items.put(new BottomTabBean("{fa-shopping-cart}","购物车"), new ShopCartDelegate());
        items.put(new BottomTabBean("{fa-user}","我的"), new IndexDelegate());

        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return Color.parseColor("#1C86EE");
    }
}
