package com.yilan.lib.playerlib.activity.live.playkit;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Debug;
import android.os.Handler;

import com.yilan.lib.playerlib.activity.live.ui.PlayerKitFragment;


/**
 * Created by QianYi-Xin on 2015/6/1.
 */
public class QosThread extends Thread {

    private Context mContext;
    private Handler mHandler;
    private Cpu mCpuStats;
    private ActivityManager mActivityManager;
    private Debug.MemoryInfo mi;
    private QosObject mQosObject;

    private boolean mRunning;

    public QosThread(ActivityManager manager, Handler handler) {
        mHandler = handler;
        mCpuStats = new Cpu();
        mActivityManager = manager;
        mi = new Debug.MemoryInfo();
        mRunning = true;
        mQosObject = new QosObject();
    }

    @Override
    public void run() {
        while(mRunning) {
            mCpuStats.parseTopResults();

            Debug.getMemoryInfo(mi);

            if(mHandler != null) {
                mQosObject.cpuUsage = mCpuStats.getProcessCpuUsage();
                mQosObject.pss = mi.getTotalPss();
                mQosObject.vss = mi.getTotalPrivateDirty();
                mHandler.obtainMessage(PlayerKitFragment.UPDATE_QOS, mQosObject).sendToTarget();
            }
            try {
                sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopThread() {
        mRunning = false;
    }
}
