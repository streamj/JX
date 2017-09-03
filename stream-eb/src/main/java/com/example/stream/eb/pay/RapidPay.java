package com.example.stream.eb.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.stream.core.app.ConfigKey;
import com.example.stream.core.app.StreamCore;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.network.RestClient;
import com.example.stream.core.network.callback.ISuccess;
import com.example.stream.core.ui.loader.StreamLoader;
import com.example.stream.core.util.log.StreamLogger;
import com.example.stream.core.wechat.StreamWeChat;
import com.example.stream.eb.R;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;

/**
 * Created by StReaM on 9/3/2017.
 */

public class RapidPay implements View.OnClickListener {

    private IAliPayResultListener mIAliPayResultListener = null;

    private Activity mActivity = null;

    private AlertDialog mDialog = null;
    private int mOrderId = -1;


    private RapidPay(StreamDelegate delegate) {
        this.mActivity = delegate.getProxyActivity();
        this.mDialog = new AlertDialog.Builder(delegate.getContext()).create();
    }

    public static RapidPay create(StreamDelegate delegate) {
        return new RapidPay(delegate);
    }

    public void startPayDialog() {
        mDialog.show();
        final Window window = mDialog.getWindow();
        if (window != null) {
            window.setContentView(R.layout.dialog_pay_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND; // 让后面变暗一点
            window.setAttributes(params);

            window.findViewById(R.id.icon_ali_pay).setOnClickListener(this);
            window.findViewById(R.id.icon_weixin_pay).setOnClickListener(this);
            window.findViewById(R.id.btn_cancel_pay).setOnClickListener(this);
        }
    }

    public final void aliPay(int orderId) {
        // 签名 url
        final String signUrl = "yourSignUrl" + orderId;
        // 获取签名字符串
        RestClient.Builder()
                .url(signUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        StreamLogger.d("sign",response);
                        // 假设服务端返回的字段在 result 里
                        final String paySign  = JSONObject.parseObject(response).getString("result");
                        // 支付宝规定客户端调用是异步
                        final PayHelperTask payHelperTask =
                                new PayHelperTask(mActivity, mIAliPayResultListener);
                        payHelperTask.executeOnExecutor(
                                AsyncTask.THREAD_POOL_EXECUTOR,
                                paySign
                        );

                    }
                })
                .build()
                .post();
    }

    public final void weChatPay(int orderId) {
        StreamLoader.stopLoading();
        final String weChatPrePayUrl = "yourUrl";
        final IWXAPI iwxapi = StreamWeChat.getInstance().getWXAPI();
        final String appId = StreamCore.getConfigurations(ConfigKey.WE_CHAT_APP_ID);
        iwxapi.registerApp(appId);
        RestClient.Builder()
                .url(weChatPrePayUrl)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        final JSONObject result = JSON.parseObject(response).getJSONObject("result");
                        final String prepayId = result.getString("prepayid");
                        final String partnerId = result.getString("partnerid");
                        final String packageValue = result.getString("package");
                        final String timestamp = result.getString("timestamp");
                        final String nonceStr = result.getString("noncestr");
                        final String paySign = result.getString("sign");

                        final PayReq payReq = new PayReq();
                        payReq.appId = appId;
                        payReq.prepayId = prepayId;
                        payReq.partnerId = partnerId;
                        payReq.packageValue = packageValue;
                        payReq.timeStamp = timestamp;
                        payReq.nonceStr = nonceStr;
                        payReq.sign = paySign;
                        iwxapi.sendReq(payReq);
                    }
                })
                .build()
                .post();
    }

    public RapidPay setIAliPayResultListener(IAliPayResultListener IAliPayResultListener) {
        mIAliPayResultListener = IAliPayResultListener;
        return this;
    }

    public RapidPay setOrderId(int orderId) {
        mOrderId = orderId;
        return this;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.icon_ali_pay) {
            aliPay(mOrderId);
            mDialog.cancel();
        } else if (id == R.id.icon_weixin_pay) {
            weChatPay(mOrderId);
            mDialog.cancel();
        } else if (id == R.id.btn_cancel_pay) {
            mDialog.cancel();
        }
    }
}
