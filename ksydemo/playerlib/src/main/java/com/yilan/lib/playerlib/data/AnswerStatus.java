package com.yilan.lib.playerlib.data;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/16.
 */

public class AnswerStatus implements Serializable{

    private int code = -1;      //0 成功
    private String message;     //报错信息
    private int in_play = -1;   //0结束， 1进行中
    private int is_player = -1; //0 观战，1 答题者

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getIn_play() {
        return in_play;
    }

    public void setIn_play(int in_play) {
        this.in_play = in_play;
    }

    public int getIs_player() {
        return is_player;
    }

    public void setIs_player(int is_player) {
        this.is_player = is_player;
    }
}
