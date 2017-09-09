package com.example.stream.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.stream.core.app.StreamCore;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.recycler.ComplexRecyclerAdapter;
import com.example.stream.core.ui.recycler.DataConverter;

/**
 * Created by StReaM on 8/22/2017.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    private final SwipeRefreshLayout SW_REFRESH;
    private final PagingBean BEAN;
    private final RecyclerView RECYCLER_VIEW;
    private final DataConverter CONVERTER;
    private ComplexRecyclerAdapter mAdapter = null;

    private RefreshHandler(SwipeRefreshLayout srl, RecyclerView recyclerView,
                          DataConverter converter, PagingBean bean) {
        this.SW_REFRESH = srl;
        this.RECYCLER_VIEW = recyclerView;
        this.CONVERTER = converter;
        this.BEAN = bean;
        SW_REFRESH.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SwipeRefreshLayout srl,  RecyclerView rv,
        DataConverter convert) {
        return new RefreshHandler(srl, rv, convert, new PagingBean());
    }

    private void refresh() {
        SW_REFRESH.setRefreshing(true);
        StreamCore.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 可以进行一些网络请求
                // 并且把下面的方法，放到网络请求的 Success 回调
                SW_REFRESH.setRefreshing(false);
            }
        },2000);
    }

    public void firstPage(String url) {
        BEAN.setLoadDelay(1000);
        RestClient.Builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        // get the total item count and item count per page for paging
                        final JSONObject jsonObject = JSON.parseObject(response);
                        BEAN.setTotalItemCount(jsonObject.getInteger("total"))
                                .setPageCapacity(jsonObject.getInteger("page_size"));
                        // pass response directly to the adapter
                        mAdapter = ComplexRecyclerAdapter.create(CONVERTER.setJsonData(response));
                        mAdapter.setOnLoadMoreListener(RefreshHandler.this, RECYCLER_VIEW);
                        RECYCLER_VIEW.setAdapter(mAdapter);
                        BEAN.addIndex();
                    }
                })
                .build()
                .get();
    }

    private void paging(final String url) {
        final int pageCapacity = BEAN.getPageCapacity();
        final int currentCount = BEAN.getCurrentItemCount();
        final int total = BEAN.getTotalItemCount();
        final int index = BEAN.getPageIndex();

        if (mAdapter.getData().size() < pageCapacity || currentCount >= total) {
            mAdapter.loadMoreEnd(true);
            BEAN.setTotalItemCount(currentCount);
        } else {
            StreamCore.getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    RestClient.Builder()
                            .url(url+index)
                            .success(new ISuccess() {
                                @Override
                                public void onSuccess(String response) {
                                    mAdapter.addData(CONVERTER.setJsonData(response).convert());
                                    BEAN.setCurrentItemCount(mAdapter.getData().size());
                                    mAdapter.loadMoreComplete();
                                    BEAN.addIndex();
                                }
                            })
                            .build()
                            .get();
                }
            }, 1000);
        }
    }


    @Override
    public void onRefresh() {
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        paging("refresh.php?index=");
    }
}
