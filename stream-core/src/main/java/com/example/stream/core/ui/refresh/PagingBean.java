package com.example.stream.core.ui.refresh;

/**
 * Created by StReaM on 8/25/2017.
 */

public final class PagingBean {
    private int mPageIndex = 0;

    private int mTotalItemCount = 0;

    private int mPageCapacity = 0;

    private int mCurrentItemCount = 0;

    private int mLoadDelay = 0;

    public int getPageIndex() {
        return mPageIndex;
    }

    public PagingBean setPageIndex(int pageIndex) {
        mPageIndex = pageIndex;
        return this;
    }

    public int getTotalItemCount() {
        return mTotalItemCount;
    }

    public PagingBean setTotalItemCount(int totalItemCount) {
        mTotalItemCount = totalItemCount;
        return this;
    }

    public int getPageCapacity() {
        return mPageCapacity;
    }

    public PagingBean setPageCapacity(int pageCapacity) {
        mPageCapacity = pageCapacity;
        return this;
    }

    public int getCurrentItemCount() {
        return mCurrentItemCount;
    }

    public PagingBean setCurrentItemCount(int currentItemCount) {
        mCurrentItemCount = currentItemCount;
        return this;
    }

    public int getLoadDelay() {
        return mLoadDelay;
    }

    public PagingBean setLoadDelay(int loadDelay) {
        mLoadDelay = loadDelay;
        return this;
    }

    PagingBean addIndex() {
        mPageIndex++;
        return this;
    }
}
