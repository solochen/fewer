package com.yilan.lib.playerlib.RongCloud.message;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by chenshaolong on 18/1/16.
 */
@MessageTag(value = "HT:COMMENT", flag = MessageTag.NONE)
public class HTCommentMessage extends MessageContent {

    private String nickname;
    private String text;

    public HTCommentMessage(String nickname, String content) {
        this.nickname = nickname;
        this.text = content;
    }

    public HTCommentMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            setNickname(jsonObj.getString("nickname"));
            setText(jsonObj.getString("text"));
            if(jsonObj.has("user")){
                setUserInfo(parseJsonToUserInfo(jsonObj.getJSONObject("user")));
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.put("nickname", nickname);
            jsonObj.put("text", text);

            if(getJSONUserInfo() != null)
                jsonObj.putOpt("user",getJSONUserInfo());

        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }

        try {
            return jsonObj.toString().getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 构造函数。
     *
     * @param in 初始化传入的 Parcel。
     */
    public HTCommentMessage(Parcel in) {
        setNickname(ParcelUtils.readFromParcel(in));
        setText(ParcelUtils.readFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in,UserInfo.class));
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<HTCommentMessage> CREATOR = new Creator<HTCommentMessage>() {

        @Override
        public HTCommentMessage createFromParcel(Parcel source) {
            return new HTCommentMessage(source);
        }

        @Override
        public HTCommentMessage[] newArray(int size) {
            return new HTCommentMessage[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, nickname);
        ParcelUtils.writeToParcel(dest, text);

        ParcelUtils.writeToParcel(dest,getUserInfo());
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
