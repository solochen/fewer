package com.yilan.lib.playerlib.RongCloud;

import android.content.Context;
import android.text.TextUtils;


import com.yilan.lib.playerlib.RongCloud.message.HTAnswerMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTCommentMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTFinishMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTOnlookerMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTQuestionMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTResultMessage;
import com.yilan.lib.playerlib.RongCloud.message.HTStartMessage;

import io.rong.imlib.RongIMClient;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class RcSingleton implements RongIMClient.ConnectionStatusListener{

    private static final String TAG = RcSingleton.class.getSimpleName();
    private LooperExecutor mExecutor;
    private String mRcToken;
    private static class SingletonHolder {
        private static RcSingleton instance = new RcSingleton();
    }

    private RcSingleton() {
        mExecutor = new LooperExecutor();
        mExecutor.requestStart();
    }

    public static RcSingleton getInstance() {
        return SingletonHolder.instance;
    }

    public void init(Context context) {
        RongIMClient.init(context);
        RongCloudReceiver.init(context);
        msgRegister();
        //RongClundReceiver处理消息接收
        RongCloudReceiver.getInstance().setOtherListener();
        RongIMClient.setConnectionStatusListener(this);
    }

    private void msgRegister(){
        try {
            //注册自定义的消息类型
            RongIMClient.registerMessageType(HTAnswerMessage.class);
            RongIMClient.registerMessageType(HTCommentMessage.class);
            RongIMClient.registerMessageType(HTFinishMessage.class);
            RongIMClient.registerMessageType(HTOnlookerMessage.class);
            RongIMClient.registerMessageType(HTResultMessage.class);
            RongIMClient.registerMessageType(HTQuestionMessage.class);
            RongIMClient.registerMessageType(HTStartMessage.class);
        } catch (Exception e) {
        }
    }

    public void connect(String rctoken) {
        if(TextUtils.isEmpty(rctoken)) {
            return;
        }
        mRcToken = rctoken;
        mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                RongIMClient.connect(mRcToken, new RongIMClient.ConnectCallback() {

                    @Override
                    public void onSuccess(String userId) {
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode code) {
                    }

                    @Override
                    public void onTokenIncorrect() {
                    }
                });
            }
        });
    }

    @Override
    public void onChanged(ConnectionStatus connectionStatus) {
        // 当出现TOKEN_INCORRECT 错误时，需要重新获取token，并且connect
        if (connectionStatus == ConnectionStatus.TOKEN_INCORRECT) {
            if(!TextUtils.isEmpty(mRcToken)) {
                connect(mRcToken);
            }
        }
    }
}
