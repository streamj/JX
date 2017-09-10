package com.example.stream.eb.main.index.search;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.blankj.utilcode.util.StringUtils;
import com.choices.divider.Divider;
import com.choices.divider.DividerItemDecoration;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.util.storage.StreamPreference;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by StReaM on 9/10/2017.
 */

public class SearchDelegate extends StreamDelegate {
    @BindView(R2.id.rv_search)
    RecyclerView mRecyclerView = null;

    @BindView(R2.id.et_search_view)
    AppCompatEditText mSearchEdit = null;

    @OnClick(R2.id.tv_top_search)
    void onClickSearch() {
        String searchText = mSearchEdit.getText().toString();
        saveText(searchText);
    }

    @OnClick(R2.id.icon_top_search_back)
    void onClickBack() {
        pop();
    }

    @SuppressWarnings("unchecked")
    private void saveText(String text) {
        if (!TextUtils.isEmpty(text) && !StringUtils.isSpace(text)) {
            List<String> history;
            String historyStr = StreamPreference
                    .getCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY);
            if (TextUtils.isEmpty(historyStr)) {
                history = new ArrayList<>();
            } else {
                history = JSON.parseObject(historyStr, ArrayList.class);
            }
            history.add(text);
            String json = JSON.toJSONString(history);
            StreamPreference.addCustomAppProfile(SearchDataConverter.TAG_SEARCH_HISTORY, json);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_search;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        List<ComplexItemEntity> data = new SearchDataConverter().convert();
        SearchAdapter adapter = new SearchAdapter(data);
        mRecyclerView.setAdapter( adapter);

        DividerItemDecoration decoration = new DividerItemDecoration();
        decoration.setDividerLookup(new DividerItemDecoration.DividerLookup() {
            @Override
            public Divider getVerticalDivider(int position) {
                return null;
            }

            @Override
            public Divider getHorizontalDivider(int position) {
                return new Divider.Builder()
                        .size(2)
                        .margin(20,20)
                        .color(Color.GRAY)
                        .build();
            }
        });
        mRecyclerView.addItemDecoration(decoration);
    }
}
