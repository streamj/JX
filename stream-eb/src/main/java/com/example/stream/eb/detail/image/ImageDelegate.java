package com.example.stream.eb.detail.image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.ItemType;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by StReaM on 9/12/2017.
 */

public class ImageDelegate extends StreamDelegate {

    @BindView(R2.id.rv_image_container)
    RecyclerView mRecyclerView = null;

    private static final String ARG_PICTURES = "ARG_PIC";

    public static ImageDelegate create(ArrayList<String> pictures) {
        final Bundle args = new Bundle();
        args.putStringArrayList(ARG_PICTURES, pictures);
        ImageDelegate delegate = new ImageDelegate();
        delegate.setArguments(args);
        return delegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_image;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(manager);
        initImage();
    }

    private void initImage() {
        final ArrayList<String> pictures = getArguments().getStringArrayList(ARG_PICTURES);
        final ArrayList<ComplexItemEntity> entities = new ArrayList<>();
        final int size;
        if (pictures!= null) {
            size = pictures.size();
            for(int i = 0; i < size; i++) {
                final String imgUrl = pictures.get(i);
                final ComplexItemEntity entity = ComplexItemEntity.Builder()
                        .setItemType(ItemType.SINGLE_BIG_IMAGE)
                        .setField(ComplexFields.IMAGE_URL, imgUrl)
                        .build();
                entities.add(entity);
            }
            final RecyclerImageAdapter adapter = new RecyclerImageAdapter(entities);
            mRecyclerView.setAdapter(adapter);
        }
    }
}
