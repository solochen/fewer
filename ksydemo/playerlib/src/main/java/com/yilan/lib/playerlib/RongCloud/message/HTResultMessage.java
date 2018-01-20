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
@MessageTag(value = "HT:RESULT", flag = MessageTag.NONE)
public class HTResultMessage extends MessageContent {

    private Integer winners;  //最终通关人数
    private Integer bonus;    //人均奖金金额（单位分）
    private Integer sec;

    public HTResultMessage(int winners, int bonus, int sec) {
        this.winners = winners;
        this.bonus = bonus;
        this.sec = sec;
    }

    public HTResultMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            setWinners(jsonObj.getInt("winners"));
            setBonus(jsonObj.getInt("bonus"));
            setSec(jsonObj.getInt("sec"));
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

            jsonObj.put("winners", winners);
            jsonObj.put("bonus", bonus);
            jsonObj.put("sec", sec);

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
    public HTResultMessage(Parcel in) {
        setWinners(ParcelUtils.readIntFromParcel(in));
        setBonus(ParcelUtils.readIntFromParcel(in));
        setSec(ParcelUtils.readIntFromParcel(in));
        setUserInfo(ParcelUtils.readFromParcel(in,UserInfo.class));
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<HTResultMessage> CREATOR = new Creator<HTResultMessage>() {

        @Override
        public HTResultMessage createFromParcel(Parcel source) {
            return new HTResultMessage(source);
        }

        @Override
        public HTResultMessage[] newArray(int size) {
            return new HTResultMessage[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, winners);
        ParcelUtils.writeToParcel(dest, bonus);
        ParcelUtils.writeToParcel(dest, sec);

        ParcelUtils.writeToParcel(dest,getUserInfo());
    }

    public Integer getWinners() {
        return winners;
    }

    public void setWinners(Integer winners) {
        this.winners = winners;
    }

    public Integer getBonus() {
        return bonus;
    }

    public void setBonus(Integer bonus) {
        this.bonus = bonus;
    }

    public Integer getSec() {
        return sec;
    }

    public void setSec(Integer sec) {
        this.sec = sec;
    }
}
