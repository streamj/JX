package com.example.stream.eb.detail;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.DefaultTransformer;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.banner.BannerImageHolderCreator;
import com.example.stream.core.ui.loader.StreamLoader;
import com.example.stream.core.ui.view.CircleTextView;
import com.example.stream.core.util.log.StreamLogger;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * Created by StReaM on 8/26/2017.
 */

public class ProductDetailDelegate extends StreamDelegate implements AppBarLayout.OnOffsetChangedListener{

    @BindView(R2.id.goods_detail_toolbar)
    Toolbar mToolbar = null;
    @BindView(R2.id.tab_layout)
    TabLayout mTabLayout = null;
    @BindView(R2.id.view_pager)
    ViewPager mViewPager = null;
    @BindView(R2.id.detail_banner)
    ConvenientBanner<String> mBanner = null;
    @BindView(R2.id.collapsing_toolbar_detail)
    CollapsingToolbarLayout mCollapsingToolbarLayout = null;
    @BindView(R2.id.app_bar_detail)
    AppBarLayout mAppBar = null;

    //底部
    @BindView(R2.id.icon_favor)
    IconTextView mIconFavor = null;
    @BindView(R2.id.tv_shopping_cart_amount)
    CircleTextView mCircleTextView = null;
    @BindView(R2.id.rl_add_shop_cart)
    RelativeLayout mRlAddShopCart = null;
    @BindView(R2.id.icon_shop_cart)
    IconTextView mIconShopCart = null;

    private static final String PRODUCT_ID = "PRODUCT_ID";
    private int mProductId = -1;

    public static ProductDetailDelegate create(int productId) {
        final Bundle args = new Bundle();
        args.putInt(PRODUCT_ID, productId);
        ProductDetailDelegate productDetailDelegate = new ProductDetailDelegate();
        productDetailDelegate.setArguments(args);
        return productDetailDelegate;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            mProductId = args.getInt(PRODUCT_ID);
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_product_detail;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
        mCollapsingToolbarLayout.setContentScrimColor(Color.WHITE);
        initData();
        initTabLayout();
    }

    private void initData(){
        RestClient.Builder()
                .url("goods_detail.php")
                .params("goods_id", mProductId)
                .loaderStyle(getContext())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        if (!TextUtils.isEmpty(response)) {
                            JSONObject data = JSON.parseObject(response).getJSONObject("data");
                            initBanner(data);
                            initProductInfo(data);
                            if (data.getJSONArray("tabs") != null) {
                                initPager(data);
                            }
                        } else {
                            Toast.makeText(getContext(), "暂时没有详情", Toast.LENGTH_LONG).show();
                        }
                    }
                })
                .build()
                .get();
    }

    private void initBanner(JSONObject data) {
        JSONArray array = data.getJSONArray("banners");
        List<String> images = new ArrayList<>();
        int size = array.size();
        for(int i = 0; i <size; i++) {
            images.add(array.getString(i));
        }
        mBanner.setPages(new BannerImageHolderCreator(), images)
                .setPageIndicator(new int[]{R.drawable.dot_normal,R.drawable.dot_focus})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setPageTransformer(new DefaultTransformer())
                .startTurning(3000)
                .setCanLoop(true);
    }

    private void initProductInfo(JSONObject info) {
        String productInfo = info.toJSONString();
        loadRootFragment(R.id.frame_product_info, ProductInfoDelegate.create(productInfo));
    }

    private void initTabLayout(){
        // 平均分开
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(getContext(),
                R.color.item_choose));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initPager(JSONObject jsonObject) {
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(), jsonObject);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        // 水平动画
        return new DefaultHorizontalAnimator();
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
