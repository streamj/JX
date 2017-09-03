package com.example.stream.eb.main.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stream.core.delegates.bottom.BottomPageDelegate;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;
import com.example.stream.eb.main.profile.list.ListAdapter;
import com.example.stream.eb.main.profile.list.ListBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by StReaM on 9/3/2017.
 */

public class ProfileDelegate extends BottomPageDelegate {

    @BindView(R2.id.rv_profile_setting)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.profile_delegate;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ListBean addr = new ListBean.Builder()
                .setItemType(20)
                .setId(1)
                .setText("收货地址")
                .setValue("")
                .build();

        ListBean sys = new ListBean.Builder()
                .setItemType(20)
                .setId(2)
                .setText("系统设置")
                .setValue("")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(addr);
        data.add(sys);
        final ListAdapter adapter = new ListAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
    }
}
