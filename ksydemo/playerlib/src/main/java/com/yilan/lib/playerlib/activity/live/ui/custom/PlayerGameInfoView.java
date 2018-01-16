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


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerGameInfoView extends FrameLayout implements View.OnClickListener{

    private static final String TAG = PlayerGameInfoView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    TextView mTvBonus;
    TextView mTvBonusUnit;
    TextView mTvStartTimeLabel;
    TextView mTvStartTime;
    Button mBtnGetReviveCode;
    TextView mTvRule;

    private int mBtnType = 1;  // 1 立即报名， 2.邀请好友，3登录才能赢钱

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

    void setClickListener(){
        mBtnGetReviveCode.setOnClickListener(this);
        mTvRule.setOnClickListener(this);
    }


    public void setClickListener(OnPlayerGameInfoViewListener listener) {
        mListener = listener;
    }


    public void setGameInfo(int bonus, String unit, String gameDate, String gameTime){
        mTvBonus.setText(String.valueOf(bonus));
        mTvBonusUnit.setText(unit);
        mTvStartTimeLabel.setText(gameDate);
        mTvStartTime.setText(gameTime);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == mBtnGetReviveCode.getId()){
            mListener.getReviveCode();
        } else if(id == mTvRule.getId()){
            //活动规则
            mListener.goRule();
        }
    }
}