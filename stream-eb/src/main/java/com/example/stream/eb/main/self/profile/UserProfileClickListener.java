package com.example.stream.eb.main.self.profile;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.example.stream.core.delegates.StreamDelegate;
import com.example.stream.core.ui.date.DateDialogUtil;
import com.example.stream.core.util.callback.CallbackManager;
import com.example.stream.core.util.callback.CallbackType;
import com.example.stream.core.util.callback.IGlobalCallback;
import com.example.stream.core.util.log.StreamLogger;
import com.example.stream.eb.R;
import com.example.stream.eb.main.self.list.ListBean;


/**
 * Created by StReaM on 9/5/2017.
 */

public class UserProfileClickListener extends SimpleClickListener {

    private final StreamDelegate mDelegate;

    private String[] mGenders = new String[] {"男", "女", "保密"};


    public UserProfileClickListener(StreamDelegate delegate) {
        mDelegate = delegate;
    }

    private void getGenderDialog(DialogInterface.OnClickListener listener) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mDelegate.getContext());
        builder.setSingleChoiceItems(mGenders, 0, listener);
        builder.show();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, final View view, int position) {
        final ListBean bean = (ListBean) adapter.getData().get(position);
        final int id = bean.getId();
        switch (id) {
            case 1:
                // 开始相机或者选择图片
                CallbackManager.getInstance().addCallback(CallbackType.ON_CROP,
                        new IGlobalCallback<Uri>() {
                            @Override
                            public void executeCallback(Uri args) {
                                StreamLogger.d("callfuck", args);
                                ImageView avatar = (ImageView) view.findViewById(R.id.img_arrow_avatar);
                                Glide.with(mDelegate)
                                        .load(args)
                                        .into(avatar);
                                // todo 通知服务器更新, 并且更新本地数据库
                            }
                        });
                mDelegate.startCameraWithCheck();
                break;
            case 2:
                final StreamDelegate nameDelegate = bean.getDelegate();
                mDelegate.start(nameDelegate);
                break;
            case 3:
                getGenderDialog(new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final TextView textView = (TextView) view.findViewById(R.id.tv_arrow_value);
                        textView.setText(mGenders[i]);
                    }
                });
                break;
            case 4:
                final DateDialogUtil dateDialogUtil = new DateDialogUtil();
                dateDialogUtil.setIDateListener(new DateDialogUtil.IDateListener() {
                    @Override
                    public void onChangeBirth(String date) {
                        final TextView textView = (TextView) view.findViewById(R.id.tv_arrow_value);
                        textView.setText(date);
                    }
                });
                dateDialogUtil.showDialog(mDelegate.getContext());
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

    }

    @Override
    public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
