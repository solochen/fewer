package com.yilan.lib.playerlib.data;

/**
 * Created by chenshaolong on 2018/1/16.
 */

public class LiveEnterInfo {

    private int code = -1;         //返回码（0:成功）
    private String message = "";   //报错信息
    private int in_play = -1;      //比赛进行中（1:进行中，0:已结束）
    private int is_player = -1;    //是否参与答题（1:答题者，0:观战者）

    public boolean success() {
        return (code == 0) ? true : false;
    }

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
