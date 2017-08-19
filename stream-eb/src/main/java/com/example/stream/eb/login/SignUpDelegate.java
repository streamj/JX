package com.example.stream.eb.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.util.Patterns;
import android.view.View;
import android.widget.Toast;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.eb.R;
import com.example.stream.eb.R2;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by StReaM on 8/19/2017.
 */

public class SignUpDelegate  extends StreamDelegate {

    @BindView(R2.id.edit_sign_name)
    TextInputEditText mUserName = null;
    @BindView(R2.id.edit_sign_email)
    TextInputEditText mEmail= null;
    @BindView(R2.id.edit_sign_phone)
    TextInputEditText mPhone = null;
    @BindView(R2.id.edit_sign_password)
    TextInputEditText mPassword = null;
    @BindView(R2.id.edit_sign_password_re)
    TextInputEditText mPasswordRe = null;

    @OnClick(R2.id.btn_sign_up)
    void onClickSignUp(){
        if (checkForm()) {
//            RestClient.Builder()
//                    .url("sign_up")
//                    .params("", "")
//                    .success(new ISuccess() {
//                        @Override
//                        public void onSuccess(String response) {
//
//                        }
//                    })
//                    .build()
//                    .post();
            Toast.makeText(getContext(), "Let's check it out",Toast.LENGTH_LONG).show();;

        }
    }

    private boolean checkForm() {
        final String userName = mUserName.getText().toString();
        final String email = mEmail.getText().toString();
        final String phone = mPhone.getText().toString();
        final String password = mPassword.getText().toString();
        final String passwordRe = mPasswordRe.getText().toString();

        boolean pass = true;
        if (userName.isEmpty()) {
            mUserName.setError("please enter username");
            pass = false;
        } else {
            mUserName.setError(null);
        }
        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mEmail.setError("invalid email format");
            pass = false;
        } else {
            mEmail.setError(null);
        }
        if (phone.isEmpty() || phone.length() != 11) {
            mPhone.setError("invalid phone number");
            pass = false;
        } else {
            mPhone.setError(null);
        }
        if (password.isEmpty() || password.length() < 6) {
            mPassword.setError("at least 6 bit password");
            pass = false;
        } else {
            mPhone.setError(null);
        }
        if (passwordRe.isEmpty() || passwordRe.length() < 6 || !passwordRe.equals(password)) {
            mPasswordRe.setError("password doesn't match");
            pass = false;
        } else {
            mPasswordRe.setError(null);
        }
        return pass;
    }


    @Override
    public Object setLayout() {
        return R.layout.delegate_sign_up;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, View rootView) {

    }
}
