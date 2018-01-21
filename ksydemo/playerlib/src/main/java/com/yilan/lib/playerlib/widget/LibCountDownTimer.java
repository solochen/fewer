package com.yilan.lib.playerlib.widget;

import android.os.CountDownTimer;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;

/**
 * Created by chenshaolong on 2018/1/18.
 */

public class LibCountDownTimer extends CountDownTimer {

    TextView mTvCountDown;

    public LibCountDownTimer(long millisInFuture, long interval, TextView textView) {
        super(millisInFuture, interval);
        mTvCountDown = textView;
    }

    @Override
    public void onTick(long millisUntilFinished) {
        long time = millisUntilFinished / 1000;
        if (time <= 59) {
            mTvCountDown.setText(String.format("00:%02d", time));
        } else if(time <= 0){
            mTvCountDown.setText(R.string.label_lib_start_now);
        }else {
            mTvCountDown.setText(String.format("%02d:%02d", time / 60, time % 60));
        }

    }

    @Override
    public void onFinish() {
        mTvCountDown.setText("00:00");

    }
}
