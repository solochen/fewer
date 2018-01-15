package com.yilan.lib.playerlib.data;

import java.io.Serializable;

/**
 * Created by chenshaolong on 2018/1/15.
 */

public class Self implements Serializable{

    private String session_key = "";
    private int should_bind_mobile = 0;
    private UserData data = new UserData();

    public String getSession_key() {
        return session_key;
    }

    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    public int getShould_bind_mobile() {
        return should_bind_mobile;
    }

    public void setShould_bind_mobile(int should_bind_mobile) {
        this.should_bind_mobile = should_bind_mobile;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    public class UserData implements Serializable{
        private long user_id = 0L;
        private String name = "";
        private String avatar_url = "";
        private boolean user_verified = false;
        private String verified_content = "";
        private String verified_agency = "";
        private String bg_img_url = "";
        private int gender = 0;
        private String mobile = "";
        private String session_key = "";

        public long getUser_id() {
            return user_id;
        }

        public void setUser_id(long user_id) {
            this.user_id = user_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public boolean isUser_verified() {
            return user_verified;
        }

        public void setUser_verified(boolean user_verified) {
            this.user_verified = user_verified;
        }

        public String getVerified_content() {
            return verified_content;
        }

        public void setVerified_content(String verified_content) {
            this.verified_content = verified_content;
        }

        public String getVerified_agency() {
            return verified_agency;
        }

        public void setVerified_agency(String verified_agency) {
            this.verified_agency = verified_agency;
        }

        public String getBg_img_url() {
            return bg_img_url;
        }

        public void setBg_img_url(String bg_img_url) {
            this.bg_img_url = bg_img_url;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getSession_key() {
            return session_key;
        }

        public void setSession_key(String session_key) {
            this.session_key = session_key;
        }
    }




}
