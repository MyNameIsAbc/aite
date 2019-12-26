package com.example.jianancangku.bean;

import java.io.Serializable;

public class LogInBean implements Serializable {

    /**
     * username : 18270281021
     * KEY : 856b57a29d20f7d753a1571f39a6af33
     * config : {"friend_valid":0,"member_id":"1951"}
     */

    private String username;
    private String key;
    private ConfigBean config;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public ConfigBean getConfig() {
        return config;
    }

    public void setConfig(ConfigBean config) {
        this.config = config;
    }

    public static class ConfigBean {
        /**
         * friend_valid : 0
         * member_id : 1951
         */

        private int friend_valid;
        private String member_id;

        public int getFriend_valid() {
            return friend_valid;
        }

        public void setFriend_valid(int friend_valid) {
            this.friend_valid = friend_valid;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }
    }
}
