package com.yilan.lib.playerlib.RongCloud;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.yilan.lib.playerlib.event.EBus;
import com.yilan.lib.playerlib.event.RongEvent;
import com.yilan.lib.playerlib.utils.AppUtils;

import io.rong.imlib.RongIMClient;
import io.rong.push.notification.PushMessageReceiver;
import io.rong.push.notification.PushNotificationMessage;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class RongCloudReceiver extends PushMessageReceiver implements RongIMClient.OnReceiveMessageListener,
        Handler.Callback {

    private static final String TAG = RongCloudReceiver.class.getSimpleName();

    private static RongCloudReceiver mRongCloudInstance;
    private Context mContext;

    /**
     * 初始化 RongCloud.
     *
     * @param context 上下文。
     */
    public static void init(Context context) {

        if (mRongCloudInstance == null) {

            synchronized (RongCloudReceiver.class) {

                if (mRongCloudInstance == null) {
                    mRongCloudInstance = new RongCloudReceiver(context);
                }
            }
        }
    }

    public RongCloudReceiver() {

    }

    /**
     * 获取RongCloud 实例。
     *
     * @return RongCloud。
     */
    public static RongCloudReceiver getInstance() {
        return mRongCloudInstance;
    }

    private RongCloudReceiver(Context context) {
        mContext = context;
    }


    /**
     * 连接成功注册。
     * <p/>
     * 在RongIM-connect-onSuccess后调用。
     */
    public void setOtherListener() {
        RongIMClient.getInstance().setOnReceiveMessageListener(this);//设置消息接收监听器。
    }

    @Override
    public boolean handleMessage(Message msg) {
        return false;
    }


    /**
     * 接收消息的监听器：OnReceiveMessageListener 的回调方法，接收到消息后执行。
     *
     * @param message 接收到的消息的实体信息。
     * @param left    剩余未拉取消息数目。
     */
    @Override
    public boolean onReceived(io.rong.imlib.model.Message message, int left) {

        /**
         * 判断APP是否在前台运行
         * 是
         * 否 显示通知
         */
        EBus.send(new RongEvent(message));
//        if (!AppUtils.isRunningForeground(mContext) && message != null) {
//            switch (message.getConversationType()) {
//                case PRIVATE:
//                case CHATROOM:
//                    EBus.send(new RongEvent(message));
//                    break;
//            }
//        } else {
//            switch (message.getConversationType()) {
//                case PRIVATE:
//                case CHATROOM:
//                    EBus.send(new RongEvent(message));
//                    break;
//            }
//        }
        return false;

    }


    /**
     * 自定义 push 通知。
     *
     * @param msg
     * @return
     */
    @Override
    public boolean onNotificationMessageArrived(Context context, PushNotificationMessage msg) {
        return false;  // 返回 false, 会弹出融云 SDK 默认通知; 返回 true, 融云 SDK 不会弹通知, 通知需要由您自定义。
    }

    @Override
    public boolean onNotificationMessageClicked(Context context, PushNotificationMessage pushNotificationMessage) {
        return false;// 返回 false, 会走融云 SDK 默认处理逻辑, 即点击该通知会打开会话列表或会话界面; 返回 true, 则由您自定义处理逻辑。
    }


}
