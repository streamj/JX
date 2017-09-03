package com.example.stream.eb.pay;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.eb.R;

/**
 * Created by StReaM on 9/3/2017.
 */

public class RapidPay implements View.OnClickListener {

    private IAlipayResultListener mIAlipayResultListener = null;

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

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.icon_ali_pay) {
            mDialog.cancel();
        } else if (id == R.id.icon_weixin_pay) {
            mDialog.cancel();
        } else if (id == R.id.btn_cancel_pay) {
            mDialog.cancel();
        }
    }
}
