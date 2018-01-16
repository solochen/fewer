package com.yilan.lib.playerlib.data;

/**
 * Created by chenshaolong on 2018/1/16.
 */

public class Comment {

    private String nickname;
    private String text;

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
