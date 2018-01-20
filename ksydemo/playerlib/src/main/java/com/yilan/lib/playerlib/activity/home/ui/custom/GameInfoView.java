package com.yilan.lib.playerlib.activity.home.ui.custom;

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

public class GameInfoView extends FrameLayout implements View.OnClickListener{

    private static final String TAG = GameInfoView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    TextView mTvBonusLabel;
    TextView mTvBonus;
    TextView mTvBonusUnit;
    TextView mTvStartTimeLabel;
    TextView mTvStartTime;
    Button mBtnLogin;
    Button mBtnGetInviteCode;

    private int mBtnType = 1;  // 1 立即报名， 2.邀请好友，3登录才能赢钱

    OnBonusViewClickListener mListener;

    public GameInfoView(Context context) {
        super(context);
        init(context);
    }

    public GameInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_bouns_view, this);
        mTvBonusLabel = (TextView) findViewById(R.id.lab_tv_bonus_label);
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
     * 显示登录状态按钮
     */
    public void showLoginBtn(){
        mBtnType = 3;
        mBtnLogin.setText(R.string.btn_lib_login_bonus);
        mBtnLogin.setVisibility(View.VISIBLE);
    }


    /**
     * 是否显示输入邀请码按钮
     * @param canBeInvited
     */
    public void canBeShowInviteBtn(boolean canBeInvited){
        if(canBeInvited){
            mBtnGetInviteCode.setVisibility(VISIBLE);
//            mBtnLogin.setText(R.string.btn_lib_apply_to_friend);
            mBtnLogin.setVisibility(View.GONE);
            mBtnType = 1;
        } else {
            mBtnGetInviteCode.setVisibility(GONE);

            mBtnLogin.setText(R.string.btn_lib_share_to_friend);
            mBtnLogin.setVisibility(View.VISIBLE);
            mBtnType = 2;
        }
    }

    public void updateGameInfo(int bonus, String unit, String gameDate, String gameTime){
//        mTvBonusLabel.setText();
        mTvBonus.setText(String.valueOf(bonus));
        mTvBonusUnit.setText(unit);
        mTvStartTimeLabel.setText(gameDate);
        mTvStartTime.setText(gameTime);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == mBtnLogin.getId()){
            switch (mBtnType) {
                case 1: //立即报名
                    mListener.onApplyClick();
                    break;
                case 2: //分享
                    mListener.onShreClick();
                    break;
                case 3: //登录
                    mListener.onLoginClick();
                    break;
            }
        } else if(id == mBtnGetInviteCode.getId()){
            mListener.onGetInviteCodeClick();
        }
    }
}