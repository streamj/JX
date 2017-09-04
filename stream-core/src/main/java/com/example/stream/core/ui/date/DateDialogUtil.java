package com.example.stream.core.ui.date;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by StReaM on 9/5/2017.
 */

public class DateDialogUtil {

    public interface  IDateListener {
        void onChangeBirth(String date);
    }

    private IDateListener mIDateListener = null;

    public void setIDateListener(IDateListener IDateListener) {
        mIDateListener = IDateListener;
    }

    public void showDialog(Context context) {
        final LinearLayout ll = new LinearLayout(context);
        final DatePicker picker = new DatePicker(context);
        final LinearLayout.LayoutParams lp =
                new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
        picker.setLayoutParams(lp);
        picker.init(1990, 1, 1, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker datePicker, int year, int month, int day) {
                final Calendar calendar = Calendar.getInstance();
                calendar.set(year,month, day);
                final SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日",
                        Locale.getDefault());
                final String data = format.format(calendar.getTime());
                if (mIDateListener != null) {
                    mIDateListener.onChangeBirth(data);
                }
            }
        });
        ll.addView(picker);

        new AlertDialog.Builder(context)
                .setTitle("选择日期")
                .setView(ll)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .show();
    }
}
