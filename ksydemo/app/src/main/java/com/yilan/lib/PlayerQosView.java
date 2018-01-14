package com.yilan.lib;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yilan.lib.Qos.QosObject;
import com.ksyun.media.player.KSYMediaPlayer;
import com.ksyun.media.player.misc.KSYQosInfo;

/**
 * Created by chenshaolong on 2018/1/12.
 */

public class PlayerQosView extends FrameLayout {

    private static final String TAG = PlayerQosView.class.getSimpleName();

    private Context mContext;
    private LayoutInflater mInflater;

    TextView mCpu;
    TextView mMemInfo;
    TextView mVideoResolution;
    TextView mVideoBitrate;
    TextView mFrameRate;
    TextView mCodecType;
    TextView mServerIp;
    TextView mSdkVersion;
    TextView mDNSTime;
    TextView mHttpConnectionTime;
    LinearLayout mPlayerQos;
    TextView mFVST;
    TextView mBufferEmptyCount;
    TextView mBufferEmptyDuration;
    TextView mBufferEmptyTimeMax;
    TextView mQosInfoAll;
    TextView mPlayerUrl;


    public PlayerQosView(Context context) {
        super(context);
        init(context);
    }

    public PlayerQosView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public PlayerQosView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    void init(Context context){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.view_player_qos, this);

        mCpu = (TextView) findViewById(R.id.player_cpu);
        mMemInfo = (TextView) findViewById(R.id.player_mem);
        mVideoResolution = (TextView) findViewById(R.id.player_re);
        mVideoBitrate = (TextView) findViewById(R.id.player_br);
        mFrameRate = (TextView) findViewById(R.id.player_fr);
        mCodecType = (TextView) findViewById(R.id.player_codec);
        mServerIp = (TextView) findViewById(R.id.player_ip);
        mPlayerUrl = (TextView) findViewById(R.id.player_url);
        mSdkVersion = (TextView) findViewById(R.id.player_sdk_version);
        mDNSTime = (TextView) findViewById(R.id.player_dns_time);
        mHttpConnectionTime = (TextView) findViewById(R.id.player_http_connection_time);
        mPlayerQos = (LinearLayout) findViewById(R.id.player_qos);
        mFVST = (TextView) findViewById(R.id.firstVideoStartTime);
        mBufferEmptyCount = (TextView) findViewById(R.id.buffer_empty_count);
        mBufferEmptyDuration = (TextView) findViewById(R.id.buffer_empty_duration);
        mBufferEmptyTimeMax = (TextView) findViewById(R.id.buffer_empty_time_max);
        mQosInfoAll = (TextView) findViewById(R.id.qos_info_all);

        setOnClickListener();
    }

    private void setOnClickListener() {
    }


    /**
     * 首屏时间
     *
     * @param firstStartTime
     */
    public void setFirstVideoStartTime(long firstStartTime) {
        mFVST.setText("首屏时间:" + firstStartTime);
    }

    /**
     * 服务器IP
     *
     * @param ipAddress
     */
    public void setServerIp(String ipAddress) {
        mServerIp.setText("ServerIP: " + ipAddress);
    }

    /**
     * 连接时间
     *
     * @param http_connection_time
     */
    public void setConnectTime(double http_connection_time) {
        mHttpConnectionTime.setText("HTTP Connection Time: " + (int) http_connection_time);
    }

    public void setDnsTime(int dns_time) {
        mDNSTime.setText("DNS time: " + dns_time);
    }

    public void setSdkVersion(String sdkVersion) {
        mSdkVersion.setText("SDK version: " + sdkVersion);
    }

    public void setResolution(int w, int h) {
        mVideoResolution.setText("Resolution:" + w + "x" + h);
    }

    public void setCpu(QosObject obj) {
        mCpu.setText("Cpu usage:" + obj.cpuUsage);
        mMemInfo.setText("Memory:" + obj.pss + " KB");
    }

    public void setBitrate(long bits) {
        mVideoBitrate.setText("Bitrate: " + bits + " kb/s");
    }

    public void setUrl(String url) {
        mPlayerUrl.setText("stream url:" + url);
    }

    public void setOther(KSYMediaPlayer mKsyMediaPlayer) {
        mBufferEmptyCount.setText("卡顿次数:" + mKsyMediaPlayer.bufferEmptyCount());
        mBufferEmptyDuration.setText("卡顿总时长:" + mKsyMediaPlayer.bufferEmptyDuration() + "s");
        mBufferEmptyTimeMax.setText("最大缓冲时长:" + mKsyMediaPlayer.getBufferTimeMax() + "s");
        KSYQosInfo qosInfo = mKsyMediaPlayer.getStreamQosInfo();
        StringBuffer qosStrInfo = new StringBuffer();
        qosStrInfo.append("音频缓冲数据量大小：" + qosInfo.audioBufferByteLength + "Byte \n");
        qosStrInfo.append("音频缓存数据时长：" + qosInfo.audioBufferTimeLength + "ms \n");
        qosStrInfo.append("开播后音频总数据量：" + qosInfo.audioTotalDataSize + "Byte \n");
        qosStrInfo.append("视频缓存数据量大小：" + qosInfo.videoBufferByteLength + "Byte \n");
        qosStrInfo.append("视频缓存时长：" + qosInfo.videoBufferTimeLength + "ms \n");
        qosStrInfo.append("开播后视频总数据量：" + qosInfo.videoTotalDataSize + "Byte \n");
        qosStrInfo.append("开播后音视频总数据量：" + qosInfo.totalDataSize + "Byte \n");
        qosStrInfo.append("开播后下载数据总大小：" + mKsyMediaPlayer.getDownloadDataSize() + "KB \n");
        mQosInfoAll.setText(qosStrInfo.toString());
    }
}