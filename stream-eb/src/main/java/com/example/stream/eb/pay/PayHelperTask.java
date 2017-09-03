package com.example.stream.eb.pay;

import android.app.Activity;
import android.os.AsyncTask;

import com.alipay.sdk.app.PayTask;
import com.example.stream.core.ui.loader.StreamLoader;

/**
 * Created by StReaM on 9/3/2017.
 */

public class PayHelperTask extends AsyncTask<String, Void, String> {

    // 支付宝规定的 ACTIVITY
    private final Activity ACTIVITY;
    private IAliPayResultListener mIAliPayResultListener;

    // 支付成功
    private static final String ALI_PAY_STATUS_SUCCESS = "9000";
    // 支付中
    private static final String ALI_PAY_STATUS_PROGRESS = "8000";
    // 支付失败
    private static final String ALI_PAY_STATUS_FAIL = "4000";
    // 支付取消
    private static final String ALI_PAY_STATUS_CANCEL = "6001";
    // 网络错误
    private static final String ALI_PAY_STATUS_NETWORK_ERROR = "6002";

    public PayHelperTask(Activity activity, IAliPayResultListener IAliPayResultListener) {
        this.ACTIVITY = activity;
        mIAliPayResultListener = IAliPayResultListener;
    }

    @Override
    protected String doInBackground(String... strings) {
        final String aliPaySign = strings[0];
        final PayTask payTask = new PayTask(ACTIVITY);
        // 返回签名+允许验证
        return payTask.pay(aliPaySign, true);
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        StreamLoader.stopLoading();
        final PayResult payResult = new PayResult(result);
        final String resultInfo = payResult.getResult();
        final String resultStatus = payResult.getResultStatus();
        switch (resultStatus) {
            case ALI_PAY_STATUS_SUCCESS:
                if (mIAliPayResultListener != null) {
                    mIAliPayResultListener.onPaySuccess();
                }
                break;
            case ALI_PAY_STATUS_PROGRESS:
                if (mIAliPayResultListener != null) {
                    mIAliPayResultListener.onProgressing();
                }
                break;
            case ALI_PAY_STATUS_FAIL:
                if (mIAliPayResultListener != null) {
                    mIAliPayResultListener.onPayFail();
                }
                break;
            case ALI_PAY_STATUS_CANCEL:
                if (mIAliPayResultListener != null) {
                    mIAliPayResultListener.onPayCancel();
                }
                break;
            case ALI_PAY_STATUS_NETWORK_ERROR:
                if (mIAliPayResultListener != null) {
                    mIAliPayResultListener.onPayNetworkError();
                }
                break;
            default:
                break;
        }
    }
}
