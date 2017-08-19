package com.example.stream.eb.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by StReaM on 8/19/2017.
 */

public class LoginDelegate extends StreamDelegate {
    @BindView(R2.id.edit_login_email)
    TextInputEditText mEmail = null;
    @BindView(R2.id.edit_login_password)
    TextInputEditText mPassword = null;

    @OnClick(R2.id.btn_login)
    void onClickLogin() {
        checkForm();
    }

    @OnClick(R2.id.icon_login_with_wechat)
    void onClickWeChat() {

    }

    @OnClick(R2.id.tv_link_sign_up)
    void onClickLinkSignUp(){
        start(new SignUpDelegate());
    }

    private boolean checkForm() {
        final String email = mEmail.getText().toString();
        final String password = mPassword.getText().toString();

        boolean pass = true;

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("invalid email format");
            pass = false;
        } else {
            mEmail.setError(null);
        }

        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("at least 6 bit password");
            pass = false;
        } else {
            mPassword.setError(null);
        }

        return pass;
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_login;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
