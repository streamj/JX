package com.example.stream.eb.main.sort.content;

import com.chad.library.adapter.base.entity.SectionEntity;

/**
 * Created by StReaM on 8/27/2017.
 */

public class SectionBean extends SectionEntity<SectionContentEntity> {

    private boolean mIsMore = false;
    private int mId = -1;

    public SectionBean(SectionContentEntity sectionContentEntity) {
        super(sectionContentEntity);
    }

    public SectionBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public boolean isMore() {
        return mIsMore;
    }

    public void setMore(boolean more) {
        mIsMore = more;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }
}
