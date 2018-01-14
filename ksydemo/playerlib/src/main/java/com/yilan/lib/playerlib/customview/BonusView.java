package com.yilan.lib.playerlib.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.home.listener.OnBonusViewClickListener;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class BonusView extends FrameLayout implements View.OnClickListener{

    private static final String TAG = BonusView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    TextView mTvBonus;
    TextView mTvBonusUnit;
    TextView mTvStartTimeLabel;
    TextView mTvStartTime;
    Button mBtnLogin;
    Button mBtnGetInviteCode;

    OnBonusViewClickListener mListener;

    public BonusView(Context context) {
        super(context);
        init(context);
    }

    public BonusView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BonusView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_bouns_view, this);
        mTvBonus = (TextView) findViewById(R.id.lib_tv_bonus);
        mTvBonusUnit = (TextView) findViewById(R.id.lib_tv_bonus_unit);
        mTvStartTimeLabel = (TextView) findViewById(R.id.lab_tv_time_label);
        mTvStartTime = (TextView) findViewById(R.id.lib_tv_time);
        mBtnLogin = (Button) findViewById(R.id.lib_btn_login);
        mBtnGetInviteCode = (Button) findViewById(R.id.lib_btn_get_invite_code);

        setClickListener();
    }

    void setClickListener(){
        mBtnLogin.setOnClickListener(this);
        mBtnGetInviteCode.setOnClickListener(this);
    }


    public void setClickListener(OnBonusViewClickListener listener) {
        mListener = listener;
    }

    /**
     * 显示输入邀请码按钮
     * @param canBeInvited
     */
    public void showInviteBtn(boolean canBeInvited){
        if(canBeInvited){
            mBtnGetInviteCode.setVisibility(VISIBLE);
        } else {
            mBtnGetInviteCode.setVisibility(GONE);
        }
    }

    public void updateGameInfo(int bonus, String unit, String gameDate, String gameTime){
        mTvBonus.setText(String.valueOf(bonus));
        mTvBonusUnit.setText(unit);
        mTvStartTimeLabel.setText(gameDate);
        mTvStartTime.setText(gameTime);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == mTvStartTime.getId()){
            mListener.onLoginBtnClick();
        } else if(id == mBtnGetInviteCode.getId()){
            mListener.onGetInviteCodeClick();
        }
    }
}