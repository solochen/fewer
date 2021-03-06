package com.yilan.lib.playerlib.activity.home.ui.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.glide.Glides;
import com.yilan.lib.playerlib.listener.OnHeaderViewClickListener;


/**
 * Created by chenshaolong on 2018/1/14.
 */

public class HomeHeaderView extends FrameLayout {

    private static final String TAG = HomeHeaderView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    ImageButton mBtnBack;
    ImageView mIvAvatar;
    ImageButton mBtnHelp;

    OnHeaderViewClickListener mListener;

    public HomeHeaderView(Context context) {
        super(context);
        init(context);
    }

    public HomeHeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public HomeHeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_header_view, this);
        mBtnBack = (ImageButton) findViewById(R.id.lib_btn_back);
        mIvAvatar = (ImageView) findViewById(R.id.lib_iv_avatar);
        mBtnHelp = (ImageButton) findViewById(R.id.lib_btn_help);
        setClickListener();

    }

    void setClickListener(){
        mBtnBack.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onBackClick();
            }
        });
        mIvAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onAvatarClick();
            }
        });
        mBtnHelp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onHelpClick();
            }
        });

    }


    public void setUserAvatar(String avatarUrl) {
        Glides.getInstance().loadAvatar(mContext, avatarUrl, mIvAvatar);
    }

    public void setClickListener(OnHeaderViewClickListener listener) {
        mListener = listener;
    }

}