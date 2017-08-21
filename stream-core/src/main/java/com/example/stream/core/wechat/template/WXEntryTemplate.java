package com.example.stream.core.wechat.template;


import com.example.stream.core.wechat.BaseWXEntryActivity;
import com.example.stream.core.wechat.StreamWeChat;

/**
 * Created by StReaM on 8/20/2017.
 */

public class WXEntryTemplate extends BaseWXEntryActivity {

    @Override
    protected void onResume() {
        super.onResume();
        finish();
        overridePendingTransition(0,0);
    }

    @Override
    protected void onLoginSuccess(String userInfo) {
        StreamWeChat.getInstance().getIWeChatLoginCallback().onLoginSuccess(userInfo);
    }
}
