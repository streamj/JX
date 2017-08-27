package com.example.stream.eb.main.sort.list;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;
import com.example.stream.eb.main.sort.SortDelegate;

import java.util.List;

import butterknife.BindView;

/**
 * Created by StReaM on 8/26/2017.
 */

public class VerticalListDelegate extends StreamDelegate {
    @BindView(R2.id.rv_vertical_menu_list)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_vertical_list;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        initRecyclerView();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.Builder()
                .url("sort_list.php")
                .loaderStyle(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final List<ComplexItemEntity> data =
                                new VerticalListDataConvert()
                                        .setJsonData(response)
                                        .convert();
                        final SortDelegate sortDelegate = getParentDelegate();
                        final SortRecyclerAdapter adapter = new SortRecyclerAdapter(data, sortDelegate);
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .build()
                .get();
    }

    private void initRecyclerView() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        // block animation
        mRecyclerView.setItemAnimator(null);
    }
}
