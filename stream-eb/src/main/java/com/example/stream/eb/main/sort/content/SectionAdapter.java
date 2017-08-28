package com.example.stream.eb.main.sort.content;

import android.support.v7.widget.AppCompatImageView;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseSectionQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.stream.eb.R;

import java.util.List;

/**
 * Created by StReaM on 8/28/2017.
 */

public class SectionAdapter extends BaseSectionQuickAdapter<SectionBean, BaseViewHolder> {
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public SectionAdapter(int layoutResId, int sectionHeadResId, List<SectionBean> data) {
        super(layoutResId, sectionHeadResId, data);
    }

    @Override
    protected void convertHead(BaseViewHolder helper, SectionBean item) {
        Log.d("Defuck", item.header);
        helper.setText(R.id.content_header, item.header);
        helper.setVisible(R.id.more, item.isMore());
        helper.addOnClickListener(R.id.more);
    }

    @Override
    protected void convert(BaseViewHolder helper, SectionBean item) {
        // t 保存了 SectionBean 的类型
        final String thumb = item.t.getProductThumb();
        final String name = item.t.getProductName();
        final int productId = item.t.getProductId();
        final SectionContentEntity entity = item.t;
        helper.setText(R.id.content_tv, name);
        final AppCompatImageView iv = helper.getView(R.id.content_iv);
        Glide.with(mContext)
                .load(thumb)
                .apply(OPTIONS)
                .into(iv);
    }
}
