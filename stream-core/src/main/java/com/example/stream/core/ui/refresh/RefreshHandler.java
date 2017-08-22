package com.example.stream.core.ui.refresh;

import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;

import com.example.stream.core.app.StreamCore;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;

/**
 * Created by StReaM on 8/22/2017.
 */

public class RefreshHandler implements SwipeRefreshLayout.OnRefreshListener {

    private final SwipeRefreshLayout SW_REFRESH;

    public RefreshHandler(SwipeRefreshLayout srl) {
        this.SW_REFRESH = srl;
        SW_REFRESH.setOnRefreshListener(this);
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
        RestClient.Builder()
                .url(url)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
//                        Toast.makeText(StreamCore.getApplicationContext(),
//                                response, Toast.LENGTH_LONG).show();
                    }
                })
                .build()
                .get();
    }

    @Override
    public void onRefresh() {
        refresh();
    }
}
