package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerHeaderViewListener;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerHeaderView extends FrameLayout {

    private Context mContext;
    private LayoutInflater mInflater;

    ImageButton mBtnBack;
    TextView mTvReviveCount;
    TextView mTvOnlineCount;
    ImageButton mBtnShare;

    OnPlayerHeaderViewListener mListener;

    public PlayerHeaderView(Context context) {
        super(context);
        init(context);
    }

    public PlayerHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_player_header_view, this);
        mBtnBack = (ImageButton) findViewById(R.id.lib_btn_back);
        mTvReviveCount = (TextView) findViewById(R.id.lib_revive_count);
        mTvOnlineCount = (TextView) findViewById(R.id.lib_online_count);
        mBtnShare = (ImageButton) findViewById(R.id.lib_btn_share);

        setClickListener();

    }

    void setClickListener(){
        mBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackClick();
            }
        });

        mBtnShare.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBtnShareClick();
            }
        });

    }

    public void setClickListener(OnPlayerHeaderViewListener listener) {
        mListener = listener;
    }

    /**
     * 设置复活卡数量
     * @param reviveCount
     */
    public void setReviveCount(int reviveCount){
        mTvReviveCount.setText(mContext.getString(R.string.label_lib_revive_count) + reviveCount);
    }

    /**
     * 在线人数
     * @param onlineCount
     */
    public void setOnlineCount(int onlineCount){
        mTvOnlineCount.setText(onlineCount + "人");
    }



}