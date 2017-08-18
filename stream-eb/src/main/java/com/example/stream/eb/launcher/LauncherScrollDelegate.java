package com.example.stream.eb.launcher;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.launcher.LauncherHolderCreator;
import com.example.stream.core.ui.launcher.ScrollLauncherTag;
import com.example.stream.core.util.storage.Preference;
import com.example.stream.eb.R;

import java.util.ArrayList;

/**
 * Created by StReaM on 8/18/2017.
 */

public class LauncherScrollDelegate extends StreamDelegate implements OnItemClickListener{

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS =  new ArrayList<>();

    private void initBanners(){
        INTEGERS.add(R.mipmap.launcher_01);
        INTEGERS.add(R.mipmap.launcher_02);
        INTEGERS.add(R.mipmap.launcher_03);
        INTEGERS.add(R.mipmap.launcher_04);
        INTEGERS.add(R.mipmap.launcher_05);
        mConvenientBanner
                .setPages(new LauncherHolderCreator(), INTEGERS)
                // 设定小圆点
                .setPageIndicator(new int[]{R.drawable.dot_normal, R.drawable.dot_focus})
                // 小圆点位置
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(this)
                .setCanLoop(false);
    }

    @Override
    public Object setLayout() {
        mConvenientBanner = new ConvenientBanner<>(getContext());
        return mConvenientBanner;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {
            initBanners();

    }

    @Override
    public void onItemClick(int position) {
        if(position == INTEGERS.size() - 1) {
            Preference.setAppFlag(ScrollLauncherTag.LAUNCH_FIRST_TIME.name(), true);
            // todo 检查用户是否已经登录
        }
    }
}
