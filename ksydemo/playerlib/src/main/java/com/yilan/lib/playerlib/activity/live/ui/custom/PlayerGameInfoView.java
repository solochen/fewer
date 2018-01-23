package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerGameInfoViewListener;
import com.yilan.lib.playerlib.widget.LibCountDownTimer;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerGameInfoView extends FrameLayout implements View.OnClickListener {

    private Context mContext;
    private LayoutInflater mInflater;
    LibCountDownTimer mTimer;

    TextView mTvBonus;
    TextView mTvBonusUnit;
    TextView mTvStartTimeLabel;
    TextView mTvStartTime;
    Button mBtnGetReviveCode;
    TextView mTvRule;

    OnPlayerGameInfoViewListener mListener;

    public PlayerGameInfoView(Context context) {
        super(context);
        init(context);
    }

    public PlayerGameInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerGameInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_player_gameinfo_view, this);
        mTvBonus = (TextView) findViewById(R.id.lib_tv_bonus);
        mTvBonusUnit = (TextView) findViewById(R.id.lib_tv_bonus_unit);
        mTvStartTimeLabel = (TextView) findViewById(R.id.lab_tv_time_label);
        mTvStartTime = (TextView) findViewById(R.id.lib_tv_time);
        mBtnGetReviveCode = (Button) findViewById(R.id.lib_btn_get_revive_code);
        mTvRule = (TextView) findViewById(R.id.lib_tv_rule);
        setClickListener();
    }

    void setClickListener() {
        mBtnGetReviveCode.setOnClickListener(this);
        mTvRule.setOnClickListener(this);
    }


    public void setClickListener(OnPlayerGameInfoViewListener listener) {
        mListener = listener;
    }


    public void setGameInfo(int bonus, String unit, String gameDate, int gameTime) {
        mTvBonus.setText(String.valueOf(bonus));
        mTvBonusUnit.setText(unit);
        mTvStartTimeLabel.setText(gameDate);

        startTimer(gameTime * 1000, 1000, mTvStartTime);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == mBtnGetReviveCode.getId()) {
            mListener.getReviveCode();
        } else if (id == mTvRule.getId()) {
            //活动规则
            mListener.goRule();
        }
    }

    /**
     * 开始倒计时
     * @param millisInFuture  总时间
     * @param intever  间隔时间
     * @param textView
     */
    private void startTimer(long millisInFuture, long intever, TextView textView){
        if(mTimer == null) {
            mTimer = new LibCountDownTimer(millisInFuture, intever, textView);
        }
        mTimer.start();
    }

    /**
     * 取消倒计时
     */
    public void cancelTimer(){
        if(mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
    }

}