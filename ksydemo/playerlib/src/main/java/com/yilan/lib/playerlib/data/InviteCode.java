package com.yilan.lib.playerlib.data;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/14.
 */

public class InviteCode extends BaseData implements Serializable {

    private String invite_code = "";  //邀请码
    private int revive_count = 0;    //复活卡数量
    private int can_be_invited = 0;  //是否可以被邀请 0 不可以， 1可以

    public String getInvite_code() {
        return invite_code;
    }

    public void setInvite_code(String invite_code) {
        this.invite_code = invite_code;
    }

    public int getRevive_count() {
        return revive_count;
    }

    public void setRevive_count(int revive_count) {
        this.revive_count = revive_count;
    }

    public boolean getCan_be_invited() {
        return can_be_invited == 1;
    }

    public void setCan_be_invited(int can_be_invited) {
        this.can_be_invited = can_be_invited;
    }
}
