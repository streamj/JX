package com.example.stream.eb.main.profile.list;

import android.widget.CompoundButton;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.example.stream.core.delegates.StreamDelegate;

/**
 * Created by StReaM on 9/4/2017.
 */

public class ListBean implements MultiItemEntity {

    private int mItemType = 0;
    private String mImageUrl = null;
    private String mText = null;
    private String mValue = null;
    private int mId = 0;
    private StreamDelegate mDelegate = null;
    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = null;

    public ListBean(int itemType, String imageUrl, String text, String value,
                    int id, StreamDelegate delegate,
                    CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
        mItemType = itemType;
        mImageUrl = imageUrl;
        mText = text;
        mValue = value;
        mId = id;
        mDelegate = delegate;
        mOnCheckedChangeListener = onCheckedChangeListener;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getText() {
        if (mText != null) {
            return mText;
        }
        return "";
    }

    public String getValue() {
        if (mValue != null) {
            return mValue;
        }
        return "";
    }

    public int getId() {
        return mId;
    }

    public StreamDelegate getDelegate() {
        return mDelegate;
    }

    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener() {
        return mOnCheckedChangeListener;
    }

    @Override
    public int getItemType() {
        return mItemType;
    }

    public static final class Builder {
        private int bmItemType = 0;
        private String bmImageUrl = null;
        private String bmText = null;
        private String bmValue = null;
        private int bmId = 0;
        private StreamDelegate bmDelegate = null;
        private CompoundButton.OnCheckedChangeListener bmOnCheckedChangeListener = null;

        public Builder setItemType(int itemType) {
            bmItemType = itemType;
            return this;
        }

        public Builder setImageUrl(String imageUrl) {
            bmImageUrl = imageUrl;
            return this;
        }

        public Builder setText(String text) {
            bmText = text;
            return this;
        }

        public Builder setValue(String value) {
            bmValue = value;
            return this;
        }

        public Builder setId(int id) {
            bmId = id;
            return this;
        }

        public Builder setDelegate(StreamDelegate delegate) {
            bmDelegate = delegate;
            return this;
        }

        public Builder
        setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener onCheckedChangeListener) {
            bmOnCheckedChangeListener = onCheckedChangeListener;
            return this;
        }

        public ListBean build() {
            return new ListBean(bmItemType, bmImageUrl, bmText, bmValue, bmId,
                    bmDelegate, bmOnCheckedChangeListener);
        }
    }
}
