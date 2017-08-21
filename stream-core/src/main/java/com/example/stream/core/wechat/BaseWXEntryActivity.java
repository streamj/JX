package com.example.stream.core.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.IError;
import com.example.stream.core.network.callback.IFailure;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.util.log.StreamLogger;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;

/**
 * Created by StReaM on 8/21/2017.
 */

public abstract class BaseWXEntryActivity extends BaseWXActivity {

    public static final String WX_AUTH_URL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=";
    public static final String SECRET_PARAM = "&secret=";
    public static final String CODE_PARAM = "&code=";
    public static final String GRANT_PARAM = "&grant_type=authorization_code";
    public static final String WX_USER_INFO_URL = "https://api.weixin.qq.com/sns/userinfo?access_token=";
    public static final String OPEN_ID = "&openId=";
    public static final String LANG_PARAM = "&lang=";
    public static final String LANG_VALUE = "zh-CN";

    protected abstract void onLoginSuccess (String userInfo);

    // 微信发送请求到第三方应用的回调
    @Override
    public void onReq(BaseReq baseReq) {

    }

    // 第三方应用发送请求到微信后的回调
    @Override
    public void onResp(BaseResp baseResp) {
        final String code = ((SendAuth.Resp)baseResp).code;
        final StringBuilder authUrl = new StringBuilder();
        authUrl.append(WX_AUTH_URL)
                .append(StreamWeChat.APP_ID)
                .append(SECRET_PARAM)
                .append(StreamWeChat.SERCET)
                .append(CODE_PARAM)
                .append(code)
                .append(GRANT_PARAM);
        StreamLogger.d("authUrl", authUrl.toString());
        getAuth(authUrl.toString());
    }

    private void getAuth(String authUrl) {
        RestClient.Builder()
                .url(authUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject authObj = JSON.parseObject(response);
                        final String accessToken = authObj.getString("access_token");
                        final String openId = authObj.getString("openId");

                        final StringBuilder userInfoUrl = new StringBuilder();
                        userInfoUrl.append(WX_USER_INFO_URL)
                                .append(accessToken)
                                .append(OPEN_ID)
                                .append(openId)
                                .append(LANG_PARAM)
                                .append(LANG_VALUE);
                        StreamLogger.d("userInfoUrl", userInfoUrl.toString());
                        getUserInfo(userInfoUrl.toString());
                    }
                })
                .build()
                .get();
    }

    private void getUserInfo(String userInfoUrl) {
        RestClient.Builder()
                .url(userInfoUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .build()
                .get();
    }
}
