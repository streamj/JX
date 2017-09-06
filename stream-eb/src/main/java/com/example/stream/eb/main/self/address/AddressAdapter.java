package com.example.stream.eb.main.self.address;

import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.recycler.ComplexFields;
import com.example.stream.core.ui.recycler.ComplexItemEntity;
import com.example.stream.core.ui.recycler.ComplexRecyclerAdapter;
import com.example.stream.core.ui.recycler.ComplexViewHolder;
import com.example.stream.eb.R;

import java.util.List;

/**
 * Created by StReaM on 9/6/2017.
 */

public class AddressAdapter extends ComplexRecyclerAdapter {
    protected AddressAdapter(List<ComplexItemEntity> data) {
        super(data);
        addItemType(AddressItemType.ITEM_ADDRESS, R.layout.item_address);
    }

    @Override
    protected void convert(final ComplexViewHolder helper, ComplexItemEntity item) {
        super.convert(helper, item);
        switch (helper.getItemViewType()) {
            case AddressItemType.ITEM_ADDRESS:
                final String name = item.getField(ComplexFields.NAME);
                final String phone = item.getField(AddressFields.PHONE);
                final String address = item.getField(AddressFields.ADDRESS);
                final boolean isDefault = item.getField(ComplexFields.TAG);
                final int id = item.getField(ComplexFields.ID);

                final AppCompatTextView tvName = helper.getView(R.id.tv_address_name);
                final AppCompatTextView tvPhoneText = helper.getView(R.id.tv_address_phone);
                final AppCompatTextView tvAddress = helper.getView(R.id.tv_address_address);
                final AppCompatTextView tvDelete = helper.getView(R.id.tv_address_delete);

                tvName.setText(name);
                tvPhoneText.setText(phone);
                tvAddress.setText(address);

                tvDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        RestClient.Builder()
                                .url("address.php")
                                .params("id", id)
                                .success(new ISuccess() {
                                    @Override
                                    public void onSuccess(String response) {
                                        remove(helper.getLayoutPosition());
                                    }
                                })
                                .build()
                                .post();
                    }
                });

                break;
            default:
                break;
        }
    }
}
