package com.example.stream.core.wechat;

import android.app.Activity;

import com.example.stream.core.app.ConfigKey;
import com.example.stream.core.app.StreamCore;
import com.example.stream.core.wechat.callback.IWeChatLoginCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * Created by StReaM on 8/21/2017.
 */

public class StreamWeChat {
    private final IWXAPI WXAPI;
    static final String APP_ID = StreamCore.getConfigurations(ConfigKey.WE_CHAT_APP_ID);
    static final String SERCET = StreamCore.getConfigurations(ConfigKey.WE_CHAT_APP_SECRET);
    private final String WE_CHAT_SPEC_SCOPE = "snsapi_userinfo";
    private final String WE_CHAT_SPEC_STATE = "random_state";

    private IWeChatLoginCallback mIWeChatLoginCallback = null;

    private static final class Holder {
        private static final StreamWeChat INSTANCE = new StreamWeChat();
    }

    public IWeChatLoginCallback getIWeChatLoginCallback() {
        return mIWeChatLoginCallback;
    }

    public StreamWeChat onLoginFinish(IWeChatLoginCallback iWeChatLoginCallback){
        mIWeChatLoginCallback = iWeChatLoginCallback;
        return this;
    }

    public static StreamWeChat getInstance() {
        return Holder.INSTANCE;
    }

    private StreamWeChat() {
        final Activity activity = StreamCore.getConfigurations(ConfigKey.ACTIVITY);
        WXAPI = WXAPIFactory.createWXAPI(activity, APP_ID, true);
        WXAPI.registerApp(APP_ID);
    }

    public final IWXAPI getWXAPI() {
        return WXAPI;
    }

    public final void Login() {
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = WE_CHAT_SPEC_SCOPE;
        req.state = WE_CHAT_SPEC_STATE;
        WXAPI.sendReq(req);
    }

}
