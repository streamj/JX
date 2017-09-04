package com.example.stream.eb.main.self.profile;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;
import com.example.stream.eb.main.self.list.ListAdapter;
import com.example.stream.eb.main.self.list.ListBean;
import com.example.stream.eb.main.self.list.ListItemType;
import com.example.stream.eb.main.self.setting.NameDelegate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by StReaM on 9/4/2017.
 */

public class ProfileDelegate extends StreamDelegate {
    @BindView(R2.id.rv_user_profile)
    RecyclerView mRecyclerView = null;


    @Override
    public Object setLayout() {
        return R.layout.delegate_profile;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        ListBean img = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_AVATAR)
                .setId(1)
                .setImageUrl("https://s3-eu-west-1.amazonaws.com/static.screenweek.it/interpretation/original_low-180662.jpg?1460019912")
                .build();

        ListBean name = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(2)
                .setText("用户名")
                .setValue("Gavin")
                .setDelegate(new NameDelegate())
                .build();

        ListBean gender = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(3)
                .setText("性别")
                .setValue("男")
                .build();

        ListBean birth = new ListBean.Builder()
                .setItemType(ListItemType.ITEM_NORMAL)
                .setId(4)
                .setText("生日")
                .setValue("未设置")
                .build();

        final List<ListBean> data = new ArrayList<>();
        data.add(img);
        data.add(name);
        data.add(gender);
        data.add(birth);
        final ListAdapter adapter = new ListAdapter(data);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.addOnItemTouchListener(new UserProfileClickListener(this));
    }
}
