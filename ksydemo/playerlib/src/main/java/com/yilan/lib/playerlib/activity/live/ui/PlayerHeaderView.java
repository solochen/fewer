package com.yilan.lib.playerlib.activity.live.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.listener.OnPlayerHeaderViewListener;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class PlayerHeaderView extends FrameLayout {

    private static final String TAG = PlayerHeaderView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    ImageButton mBtnBack;

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
        setClickListener();

    }

    void setClickListener(){
        mBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackClick();
            }
        });

    }


    public void setClickListener(OnPlayerHeaderViewListener listener) {
        mListener = listener;
    }

}