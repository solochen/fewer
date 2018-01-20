package com.yilan.lib.playerlib.data;

import java.io.Serializable;
import java.util.List;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class WinnerInfo extends BaseData implements Serializable {

    private int winners;
    private int avg_bouns;
    private List<WinnerList> winner_list;

    public int getWinners() {
        return winners;
    }

    public void setWinners(int winners) {
        this.winners = winners;
    }

    public int getAvg_bouns() {
        return avg_bouns;
    }

    public void setAvg_bouns(int avg_bouns) {
        this.avg_bouns = avg_bouns;
    }

    public List<WinnerList> getWinner_list() {
        return winner_list;
    }

    public void setWinner_list(List<WinnerList> winner_list) {
        this.winner_list = winner_list;
    }

    public class WinnerList implements Serializable{

        private String avatar;
        private String nickname;
        private int bouns;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public int getBouns() {
            return bouns;
        }

        public void setBouns(int bouns) {
            this.bouns = bouns;
        }
    }

}
