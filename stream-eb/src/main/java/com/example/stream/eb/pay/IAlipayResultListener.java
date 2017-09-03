package com.example.stream.eb.pay;

/**
 * Created by StReaM on 9/3/2017.
 */

public interface IAlipayResultListener {

    void onPaySuccess();

    void onProgressing();

    void onPayFail();

    void onPayCancel();

    void onPayNetworkError();
}
