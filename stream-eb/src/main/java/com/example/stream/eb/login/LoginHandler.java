package com.example.stream.eb.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.app.AccountManager;
import com.example.stream.eb.database.DatabaseManager;
import com.example.stream.eb.database.UserProfile;

/**
 * Created by StReaM on 8/20/2017.
 */

public class LoginHandler {
    public static void onLogIn(String response, ILoginListener iLoginListener) {
//        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");
//
//        final long userId = profileJson.getLong("userId");
//        final String name = profileJson.getString("name");
//        final String avatar = profileJson.getString("avatar");
//        final String gender = profileJson.getString("gender");
//        final String address = profileJson.getString("address");
//
//        final UserProfile newUserProfile = new UserProfile(userId, name, avatar, gender, address);
//        DatabaseManager.getInstance().getDao().insert(newUserProfile);

        AccountManager.setSessionState(true);
        iLoginListener.onLoginSuccess();
    }

    public static void onSignUp(String response, ILoginListener iLoginListener) {
        final JSONObject profileJson = JSON.parseObject(response).getJSONObject("data");

        final long userId = profileJson.getLong("userId");
        final String name = profileJson.getString("name");
        final String avatar = profileJson.getString("avatar");
        final String gender = profileJson.getString("gender");
        final String address = profileJson.getString("address");

        final UserProfile newUserProfile = new UserProfile(userId, name, avatar, gender, address);
        DatabaseManager.getInstance().getDao().insert(newUserProfile);

        // 已经注册并且登录成功
        AccountManager.setSessionState(true);
        // 一般app 注册成功会自动登录，所以这个回调包含更多
        iLoginListener.onSignUpSuccess();
    }
}
