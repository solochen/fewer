package com.yilan.lib.playerlib.activity.live.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.View;
import android.view.ViewGroup;

import com.ksyun.media.player.IMediaPlayer;
import com.ksyun.media.player.KSYMediaMeta;
import com.ksyun.media.player.KSYMediaPlayer;
import com.yilan.lib.playerlib.R;
import com.yilan.lib.playerlib.activity.live.playkit.PlayerQosView;
import com.yilan.lib.playerlib.activity.live.playkit.QosObject;
import com.yilan.lib.playerlib.activity.live.playkit.QosThread;
import com.yilan.lib.playerlib.activity.live.playkit.VideoSurfaceView;

import java.io.IOException;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class PlayerKitFragment extends Fragment{

    public static final int UPDATE_QOS = 2;

    private static final String KEY_URL = "liveUrl";

    VideoSurfaceView mVideoSurfaceView;
    PlayerQosView mQosView;


    SurfaceHolder mSurfaceHolder;
    KSYMediaPlayer mKsyMediaPlayer;
    Surface mSurface;

    String mPlayerUrl;
    Context mContext;
    QosThread mQosThread;
    Handler mQosHandler;

    int mVideoWidth = 0;
    int mVideoHeight = 0;
    long mPauseStartTime = 0;
    long mPausedTime = 0;
    long mFirstVideoStartTime = 0;
    long mStartTime = 0;
    boolean mPause = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frmt_lib_player_kit, container, false);
        mVideoSurfaceView = (VideoSurfaceView) view.findViewById(R.id.lib_player_surface);
        mQosView = (PlayerQosView) view.findViewById(R.id.lib_qos_view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPlayerUrl = getArguments().getString(KEY_URL);
        mQosView.setUrl(mPlayerUrl);
        initMediaPlayer();
    }


    private void initMediaPlayer() {
        mSurfaceHolder = mVideoSurfaceView.getHolder();
        mSurfaceHolder.addCallback(mSurfaceCallback);
        mVideoSurfaceView.setKeepScreenOn(true);
        getActivity().setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mQosHandler = new UIHandler(this);
        ActivityManager activityManager = (ActivityManager) getActivity().getSystemService(getActivity().ACTIVITY_SERVICE);
        mQosThread = new QosThread(activityManager, mQosHandler);

        mKsyMediaPlayer = new KSYMediaPlayer.Builder(mContext).build();
        mKsyMediaPlayer.setBufferTimeMax(5);
        mKsyMediaPlayer.setOnBufferingUpdateListener(mOnBufferingUpdateListener);
        mKsyMediaPlayer.setOnCompletionListener(mOnCompletionListener);
        mKsyMediaPlayer.setOnPreparedListener(mOnPreparedListener);
        mKsyMediaPlayer.setOnInfoListener(mOnInfoListener);
        mKsyMediaPlayer.setOnVideoSizeChangedListener(mOnVideoSizeChangeListener);
        mKsyMediaPlayer.setOnErrorListener(mOnErrorListener);
        mKsyMediaPlayer.setOnLogEventListener(mOnLogErrorListener);
        mKsyMediaPlayer.setScreenOnWhilePlaying(true);

        //下面两行代码设置surfaceview透明背景
//        mVideoSurfaceView.setZOrderOnTop(true);
//        mVideoSurfaceView.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        try {
            mKsyMediaPlayer.setDataSource(mPlayerUrl);
            mKsyMediaPlayer.prepareAsync();
        } catch (IOException e) {
        }
    }


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
                mSurface = null;
            }
        }
    };

    private IMediaPlayer.OnBufferingUpdateListener mOnBufferingUpdateListener = new IMediaPlayer.OnBufferingUpdateListener() {
        @Override
        public void onBufferingUpdate(IMediaPlayer iMediaPlayer, int i) {

        }
    };

    private IMediaPlayer.OnVideoSizeChangedListener mOnVideoSizeChangeListener = new IMediaPlayer.OnVideoSizeChangedListener() {
        @Override
        public void onVideoSizeChanged(IMediaPlayer mp, int width, int height, int sarNum, int sarDen) {
            if (mVideoWidth > 0 && mVideoHeight > 0) {
                if (width != mVideoWidth || height != mVideoHeight) {
                    mVideoWidth = mp.getVideoWidth();
                    mVideoHeight = mp.getVideoHeight();
                    if (mKsyMediaPlayer != null)
                        mKsyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);
                }
            }
        }
    };

    private IMediaPlayer.OnCompletionListener mOnCompletionListener = new IMediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(IMediaPlayer iMediaPlayer) {
            /**
             * 推流服务器判断为该直播流已结束
             */
//            EventBus.getDefault().post(new PlayerEvent(PlayerEvent.PLAYER_LIVE_COMPLETION));
        }
    };


    private IMediaPlayer.OnErrorListener mOnErrorListener = new IMediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(IMediaPlayer iMediaPlayer, int what, int extra) {
            switch (what) {
                case KSYMediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                case KSYMediaPlayer.MEDIA_ERROR_IO:
//                    onIOErrorSwitchUrl();
                    break;
                case KSYMediaPlayer.MEDIA_ERROR_TARGET_NOT_FOUND:
                    /**
                     * SDK 判断为播放地址为找到，对于有需求的这里可以切换播放地址
                     */
//                    onTargetNotFoundSwitchUrl();
                    break;
                default:
            }
            return false;
        }
    };


    public com.ksyun.media.player.IMediaPlayer.OnInfoListener mOnInfoListener = new com.ksyun.media.player.IMediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(com.ksyun.media.player.IMediaPlayer iMediaPlayer, int i, int i1) {
            switch (i) {
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_START:
//                    EventBus.getDefault().post(new PlayerEvent(PlayerEvent.PLAYER_LIVE_BUFFERING_START));
                    break;
                case KSYMediaPlayer.MEDIA_INFO_BUFFERING_END:
//                    EventBus.getDefault().post(new PlayerEvent(PlayerEvent.PLAYER_LIVE_BUFFERING_END));
                    break;
                case KSYMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:
//                    EventBus.getDefault().post(new PlayerEvent(PlayerEvent.PLAYER_LIVE_BUFFERING_END));

                    //首屏时间
                    mFirstVideoStartTime = System.currentTimeMillis() - mFirstVideoStartTime;
                    mQosView.setFirstVideoStartTime(mFirstVideoStartTime);
                    break;
                case KSYMediaPlayer.MEDIA_INFO_SUGGEST_RELOAD:
                    if (mKsyMediaPlayer != null && !TextUtils.isEmpty(mPlayerUrl)) {
                        mKsyMediaPlayer.reload(mPlayerUrl, false);
                    }
                    break;
                case KSYMediaPlayer.MEDIA_INFO_RELOADED:
                    break;
            }
            return false;
        }
    };

    private IMediaPlayer.OnPreparedListener mOnPreparedListener = new IMediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(IMediaPlayer iMediaPlayer) {
            mVideoWidth = mKsyMediaPlayer.getVideoWidth();
            mVideoHeight = mKsyMediaPlayer.getVideoHeight();

            // Set Video Scaling Mode
            mKsyMediaPlayer.setVideoScalingMode(KSYMediaPlayer.VIDEO_SCALING_MODE_SCALE_TO_FIT_WITH_CROPPING);

            //start player
            if (mKsyMediaPlayer != null)
                mKsyMediaPlayer.start();

            if (mQosThread != null) {
                mQosThread.start();
            }

            if (mKsyMediaPlayer.getServerAddress() != null) {
                mQosView.setServerIp(mKsyMediaPlayer.getServerAddress());
            }

            //  get meta data
            Bundle bundle = mKsyMediaPlayer.getMediaMeta();
            KSYMediaMeta meta = KSYMediaMeta.parse(bundle);
            if (meta != null) {
                if (meta.mHttpConnectTime > 0) {
                    double http_connection_time = Double.valueOf(meta.mHttpConnectTime);
                    mQosView.setConnectTime(http_connection_time);
                }

                int dns_time = meta.mAnalyzeDnsTime;
                if (dns_time >= 0) {
                    mQosView.setDnsTime(dns_time);
                }
            }

            mQosView.setSdkVersion(mKsyMediaPlayer.getVersion());
            mQosView.setResolution(mKsyMediaPlayer.getVideoWidth(), mKsyMediaPlayer.getVideoHeight());
            mStartTime = System.currentTimeMillis();
        }
    };


    private IMediaPlayer.OnLogEventListener mOnLogErrorListener = new IMediaPlayer.OnLogEventListener() {
        @Override
        public void onLogEvent(IMediaPlayer iMediaPlayer, String s) {
        }
    };


    private class UIHandler extends Handler {
        PlayerKitFragment mPlayerFragment;

        public UIHandler(PlayerKitFragment playerFragment) {
            mPlayerFragment = playerFragment;
        }

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case UPDATE_QOS:
                    if (mPlayerFragment != null && msg.obj instanceof QosObject) {
                        mPlayerFragment.updateQosInfo((QosObject) msg.obj);
                    }
                    break;
            }
        }
    }

    private void updateQosInfo(QosObject obj) {
        if(mQosView == null) return;
        mQosView.setCpu(obj);
        if (mKsyMediaPlayer != null) {
            long bits = mKsyMediaPlayer.getDecodedDataSize() * 8 / (mPause ? mPauseStartTime - mPausedTime - mStartTime : System.currentTimeMillis() - mPausedTime - mStartTime);
            mQosView.setBitrate(bits);
            mQosView.setOther(mKsyMediaPlayer);
        }
    }


    public void release() {
        if (mKsyMediaPlayer != null) {
            mKsyMediaPlayer.release();
            mKsyMediaPlayer = null;
        }
        if (mQosThread != null) {
            mQosThread.stopThread();
            mQosThread = null;
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        release();
    }


}
