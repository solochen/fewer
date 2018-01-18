package com.yilan.lib.playerlib.RongCloud.message;

import android.os.Parcel;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.List;

import io.rong.common.ParcelUtils;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by chenshaolong on 18/1/16.
 */
@MessageTag(value = "HT:QUESTION", flag = MessageTag.NONE)
public class HTQuestionMessage extends MessageContent {

    private String id;
    private String text;
    private List<String> options;
    private Integer count;
    private Integer number;
    private Integer sec;

    public HTQuestionMessage(){

    }

    public HTQuestionMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            setId(jsonObj.getString("id"));
            setText(jsonObj.getString("text"));
            if(jsonObj.has("options")) {
                setOptions(JSON.parseArray(jsonObj.getString("options"), String.class));
            }
            setCount(jsonObj.getInt("count"));
            setNumber(jsonObj.getInt("number"));
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

            jsonObj.put("id", id);
            jsonObj.put("text", text);

            jsonObj.putOpt("options",  com.alibaba.fastjson.JSONArray.parseArray(JSON.toJSONString(options)));

            jsonObj.put("count", count);
            jsonObj.put("number", number);
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
    public HTQuestionMessage(Parcel in) {

        setId(ParcelUtils.readFromParcel(in));
        setText(ParcelUtils.readFromParcel(in));

        setOptions(ParcelUtils.readListFromParcel(in, String.class));
        setCount(ParcelUtils.readIntFromParcel(in));
        setNumber(ParcelUtils.readIntFromParcel(in));
        setSec(ParcelUtils.readIntFromParcel(in));

        setUserInfo(ParcelUtils.readFromParcel(in,UserInfo.class));
    }

    /**
     * 读取接口，目的是要从Parcel中构造一个实现了Parcelable的类的实例处理。
     */
    public static final Creator<HTQuestionMessage> CREATOR = new Creator<HTQuestionMessage>() {

        @Override
        public HTQuestionMessage createFromParcel(Parcel source) {
            return new HTQuestionMessage(source);
        }

        @Override
        public HTQuestionMessage[] newArray(int size) {
            return new HTQuestionMessage[size];
        }
    };
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtils.writeToParcel(dest, id);
        ParcelUtils.writeToParcel(dest, text);
        ParcelUtils.writeToParcel(dest, options);
        ParcelUtils.writeToParcel(dest, count);
        ParcelUtils.writeToParcel(dest, number);
        ParcelUtils.writeToParcel(dest, sec);

        ParcelUtils.writeToParcel(dest, getUserInfo());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String> getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSec() {
        return sec;
    }

    public void setSec(Integer sec) {
        this.sec = sec;
    }
}
