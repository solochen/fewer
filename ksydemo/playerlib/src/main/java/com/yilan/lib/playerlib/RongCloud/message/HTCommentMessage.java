package com.yilan.lib.playerlib.RongCloud.message;

import android.os.Parcel;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import io.rong.common.ParcelUtils;
import io.rong.common.RLog;
import io.rong.imlib.MessageTag;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;

/**
 * Created by chenshaolong on 18/1/16.
 */
@MessageTag(value = "HT:COMMENT", flag = MessageTag.NONE)
public class HTCommentMessage extends MessageContent {

    private List<Comment> comments;

    public HTCommentMessage(byte[] data) {
        String jsonStr = null;

        try {
            jsonStr = new String(data, "UTF-8");
        } catch (UnsupportedEncodingException e1) {

        }

        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            if(jsonObj.has("comments")) {
                setComments(parseJsonToAssists(jsonObj.getJSONArray("comments")));
            }
        } catch (JSONException e) {
            Log.e("JSONException", e.getMessage());
        }
    }


    public List<Comment> parseJsonToAssists(JSONArray jsonArray) {
        List<Comment> comments = new ArrayList<>();
        for(int i = 0; i < jsonArray.length(); i ++){
            try {
                Comment comment = new Comment();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String nickname = jsonObject.optString("nickname");
                String text = jsonObject.optString("text");

                comment.setNickname(nickname);
                comment.setText(text);

                comments.add(comment);
            }catch (Exception e){}
        }
        return comments;
    }

    @Override
    public byte[] encode() {
        JSONObject jsonObj = new JSONObject();
        try {

            jsonObj.putOpt("comments", getJSONAssists(comments));

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


    public JSONArray getJSONAssists(List<Comment> comments) {
        if(comments != null && comments.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for(Comment comment : comments) {
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("nickname", comment.getNickname());
                    jsonObject.put("text", comment.getText());
                    jsonArray.put(jsonObject);
                } catch (JSONException var3) {
                    RLog.e("MessageContent", "JSONException " + var3.getMessage());
                }
            }
            return jsonArray;
        } else {
            return null;
        }
    }


    /**
     * 构造函数。
     *
     * @param in 初始化传入的 Parcel。
     */
    public HTCommentMessage(Parcel in) {
        setComments(ParcelUtils.readListFromParcel(in, Comment.class));

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
        ParcelUtils.writeToParcel(dest, comments);
        ParcelUtils.writeToParcel(dest,getUserInfo());
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
