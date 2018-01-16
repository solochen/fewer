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
@MessageTag(value = "HT:ONLOOKER", flag = MessageTag.NONE)
public class HTOnlookerMessage extends MessageContent {

    private Integer onlookers;

    public HTOnlookerMessage(int onlookers) {
        this.onlookers = onlookers;
    }

    public HTOnlookerMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            setOnlookers(jsonObj.getInt("onlookers"));
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

            jsonObj.put("onlookers", onlookers);

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
    public HTOnlookerMessage(Parcel in) {
        setOnlookers(ParcelUtils.readIntFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in,UserInfo.class));
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<HTOnlookerMessage> CREATOR = new Creator<HTOnlookerMessage>() {

        @Override
        public HTOnlookerMessage createFromParcel(Parcel source) {
            return new HTOnlookerMessage(source);
        }

        @Override
        public HTOnlookerMessage[] newArray(int size) {
            return new HTOnlookerMessage[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, onlookers);

        ParcelUtils.writeToParcel(dest,getUserInfo());
    }

    public Integer getOnlookers() {
        return onlookers;
    }

    public void setOnlookers(Integer onlookers) {
        this.onlookers = onlookers;
    }
}
