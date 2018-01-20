package com.yilan.lib.playerlib.RongCloud.message;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/20.
 */

public class Comment implements Serializable{

    private String nickname;
    private String text;

    public Comment(){
    }

    public Comment(String nickname, String text){
        this.nickname = nickname;
        this.text = text;
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
