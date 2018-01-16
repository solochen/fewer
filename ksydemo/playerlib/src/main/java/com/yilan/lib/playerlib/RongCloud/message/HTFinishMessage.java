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
@MessageTag(value = "HT:FINISH", flag = MessageTag.NONE)
public class HTFinishMessage extends MessageContent {

    private String live_id;  //直播间ID，qa开头是答题直播室、pre开头是彩排直播室，纯数字直播间留给普通用户开直播使用

    public HTFinishMessage(String live_id) {
        this.live_id = live_id;
    }

    public HTFinishMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            setLive_id(jsonObj.getString("live_id"));
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

            jsonObj.put("live_id", live_id);

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
    public HTFinishMessage(Parcel in) {
        setLive_id(ParcelUtils.readFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in,UserInfo.class));
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<HTFinishMessage> CREATOR = new Creator<HTFinishMessage>() {

        @Override
        public HTFinishMessage createFromParcel(Parcel source) {
            return new HTFinishMessage(source);
        }

        @Override
        public HTFinishMessage[] newArray(int size) {
            return new HTFinishMessage[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, live_id);

        ParcelUtils.writeToParcel(dest,getUserInfo());
    }

    public String getLive_id() {
        return live_id;
    }

    public void setLive_id(String live_id) {
        this.live_id = live_id;
    }
}
