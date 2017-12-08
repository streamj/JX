package com.example.stream.core.app;

import com.example.stream.core.util.storage.StreamPreference;

/**
 * Created by StReaM on 8/20/2017.
 */

public class AccountManager {

    // 保存登录状态
    private enum sessionTag {
        SESSION_TAG,
    }

    public static void setSessionState(boolean loginState) {
        StreamPreference.setAppFlag(sessionTag.SESSION_TAG.name(), loginState);
    }

    private static boolean isLoggedIn() {
        return StreamPreference.getAppFlag(sessionTag.SESSION_TAG.name());
    }

    public static void checkAccount(IUserChecker checker) {
        if (isLoggedIn()) {
            checker.onLoggedIn();
        } else {
            checker.onLoggedOut();
        }
    }
}
