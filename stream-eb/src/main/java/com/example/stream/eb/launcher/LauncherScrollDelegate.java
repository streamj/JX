package com.example.stream.eb.launcher;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.example.stream.core.app.AccountManager;
import com.example.stream.core.app.IUserChecker;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.launcher.ILauncherListener;
import com.example.stream.core.ui.launcher.LauncherHolderCreator;
import com.example.stream.core.ui.launcher.OnLauncherFinishTag;
import com.example.stream.core.ui.launcher.ScrollLauncherTag;
import com.example.stream.core.util.storage.StreamPreference;
import com.example.stream.eb.R;

import java.util.ArrayList;

/**
 * Created by StReaM on 8/18/2017.
 */

public class LauncherScrollDelegate extends StreamDelegate implements OnItemClickListener{

    private ConvenientBanner<Integer> mConvenientBanner = null;
    private static final ArrayList<Integer> INTEGERS =  new ArrayList<>();

    private ILauncherListener mILauncherListener = null;

    private void initBanners(){
        if (INTEGERS.isEmpty()) {
            INTEGERS.add(R.mipmap.launcher_01);
            INTEGERS.add(R.mipmap.launcher_02);
            INTEGERS.add(R.mipmap.launcher_03);
            INTEGERS.add(R.mipmap.launcher_04);
            INTEGERS.add(R.mipmap.launcher_05);
        }
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof ILauncherListener) {
            mILauncherListener = (ILauncherListener) activity;
        }
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
            StreamPreference.setAppFlag(ScrollLauncherTag.LAUNCH_FIRST_TIME.name(), true);
            //  检查用户是否已经登录
            // 检查用户是否登录了 app
            AccountManager.checkAccount(new IUserChecker() {
                @Override
                public void onLoggedIn() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLaunchFinish(OnLauncherFinishTag.LOGGED_IN);
                    }
                }

                @Override
                public void onLoggedOut() {
                    if (mILauncherListener != null) {
                        mILauncherListener.onLaunchFinish(OnLauncherFinishTag.LOGGED_OUT);
                    }
                }
            });
        }
    }
}
