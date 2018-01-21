package com.yilan.lib.playerlib.data;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class GameInfo extends BaseData implements Serializable {

    private int status = -1; //关闭-1,开放0, 答题中1
    private int bonus = 0;
    private int countdown = 0;   //倒计时，单位 s
    private String game_date = "";
    private String game_time = "";
    private String ad_image = "";
    private Live live = new Live();

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getBonus() {
        return bonus;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public String getGame_date() {
        return game_date;
    }

    public void setGame_date(String game_date) {
        this.game_date = game_date;
    }

    public String getGame_time() {
        return game_time;
    }

    public void setGame_time(String game_time) {
        this.game_time = game_time;
    }

    public String getAd_image() {
        return ad_image;
    }

    public void setAd_image(String ad_image) {
        this.ad_image = ad_image;
    }

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public int getCountdown() {
        return countdown;
    }

    public void setCountdown(int countdown) {
        this.countdown = countdown;
    }
}
