package com.example.stream.core.ui.recycler;

import java.util.ArrayList;

/**
 * Created by StReaM on 8/22/2017.
 */

public abstract class DataConverter {
    protected final ArrayList<ComplexItemEntity> ENTITIES = new ArrayList<>();
    private String mJsonData = null;

    public abstract ArrayList<ComplexItemEntity> convert();

    public DataConverter setJsonData(String json) {
        this.mJsonData = json;
        return this;
    }

    protected String getJsonData() {
        if (mJsonData == null || mJsonData.isEmpty()) {
            throw new NullPointerException("ComplexItem Data is Null");
        }
        return mJsonData;
    }
}
