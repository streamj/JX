package com.example.stream.eb.main.self.address;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.util.log.StreamLogger;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import java.util.List;

import butterknife.BindView;

/**
 * Created by StReaM on 9/6/2017.
 */

public class AddressDelegate extends StreamDelegate implements ISuccess{

    @BindView(R2.id.rv_address)
    RecyclerView mRecyclerView= null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_address;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        RestClient.Builder()
                .url("address.php")
                .loaderStyle(getContext())
                .success(this)
                .build()
                .get();
    }

    @Override
    public void onSuccess(String response) {
        List<ComplexItemEntity> data = new AddressDataConverter()
                .setJsonData(response).convert();
        final AddressAdapter adapter = new AddressAdapter(data);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(adapter);
    }
}
