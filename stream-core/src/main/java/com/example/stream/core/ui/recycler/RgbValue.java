package com.example.stream.core.ui.recycler;

import com.google.auto.value.AutoValue;

/**
 * Created by StReaM on 8/26/2017.
 */
@AutoValue
public abstract class RgbValue {

    public abstract int red();

    public abstract int green();

    public abstract int blue();

    public static RgbValue create(int red, int green, int blue) {
        return new AutoValue_RgbValue(red, green, blue);
    }
}
