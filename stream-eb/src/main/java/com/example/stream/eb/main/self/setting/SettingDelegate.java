package com.example.stream.eb.main.self.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;
import com.example.stream.eb.main.self.address.AddressDelegate;
import com.example.stream.eb.main.self.list.ListAdapter;
import com.example.stream.eb.main.self.list.ListBean;
import com.example.stream.eb.main.self.list.ListItemType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by StReaM on 9/6/2017.
 */

public class SettingDelegate extends StreamDelegate {

    @BindView(R2.id.rv_settings)
    RecyclerView mRecyclerView = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_setting;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ListBean addr = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_SWITCH)
                .setId(3)
                .setText("消息推送")
                .setDelegate(new AddressDelegate())
                .setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton compoundButton, boolean on) {
                        if (on) {
                            Toast.makeText(getContext(), "打开推送", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "关闭推送", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .setValue("")
                .build();

        ListBean sys = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("关于")
                .setDelegate(new AboutDelegate())
                .setValue("")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(addr);
        data.add(sys);
        final ListAdapter adapter = new ListAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new SettingClickListener(this));
    }
}
