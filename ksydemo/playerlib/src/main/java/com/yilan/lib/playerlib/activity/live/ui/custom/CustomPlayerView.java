package com.yilan.lib.playerlib.activity.live.ui.custom;

import android.content.Context;
import android.graphics.PixelFormat;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.widget.RelativeLayout;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaPlayer;
import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.playkit.VideoSurfaceView;

import java.io.IOException;

/**
 * Created by chenshaolong on 2018/1/12.
 */

public class CustomPlayerView extends RelativeLayout {
    private static final String TAG = CustomPlayerView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;
    VideoSurfaceView mVideoSurfaceView;
    SurfaceHolder mSurfaceHolder;
    KSYMediaPlayer mKsyMediaPlayer;

    public CustomPlayerView(Context context) {
        super(context);
        initView(context);
    }

    public CustomPlayerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }


    public CustomPlayerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }


    private void initView(Context context) {
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.custom_lib_player_view, this);
        mVideoSurfaceView = (VideoSurfaceView) findViewById(R.id.lib_player_surface);
    }

    public void initPlayer(String url){

        mSurfaceHolder = mVideoSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mVideoSurfaceView.setKeepScreenOn(true);

        mKsyMediaPlayer = new KSYMediaPlayer.Builder(mContext).build();
        mKsyMediaPlayer.setBufferTimeMax(5);
        mKsyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        mKsyMediaPlayer.setScreenOnWhilePlaying(true);


        //下面两行代码设置surfaceview透明背景
        mVideoSurfaceView.setZOrderOnTop(true);
        mVideoSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        try {
            mKsyMediaPlayer.setDataSource(url);
            mKsyMediaPlayer.prepareAsync();
        } catch (IOException e) {
        }

    }

    public void reloadVideo(String url) {
        if (mKsyMediaPlayer != null && !TextUtils.isEmpty(url)) {
            mKsyMediaPlayer.reload(url, true);
        }
    }

    public void releaseVideo(){
        if(mKsyMediaPlayer != null){
            mKsyMediaPlayer.release();
            mKsyMediaPlayer = null;
        }
    }


    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            mKsyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
            if (mKsyMediaPlayer != null)
                mKsyMediaPlayer.start();
        }
    };



    private final SurfaceHolder.Callback mSurfaceCallback = new SurfaceHolder.Callback() {
        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (mKsyMediaPlayer != null && mKsyMediaPlayer.isPlaying())
                mKsyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (mKsyMediaPlayer != null)
                mKsyMediaPlayer.setDisplay(holder);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (mKsyMediaPlayer != null) {
                mKsyMediaPlayer.setDisplay(null);
            }
        }
    };

}

