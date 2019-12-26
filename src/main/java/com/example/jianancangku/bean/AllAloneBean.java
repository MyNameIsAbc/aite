package com.example.jianancangku.bean;

import java.io.Serializable;

public class AllAloneBean implements Serializable {

    /**
     * info : {"count":"0","name":"泰国管理员","position":"主管","avatar":"https://aitecc.com/data/upload/shop/avatar/06251578489477403.jpg","type":4}
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * count : 0
         * name : 泰国管理员
         * position : 主管
         * avatar : https://aitecc.com/data/upload/shop/avatar/06251578489477403.jpg
         * type : 4
         */

        private String count;
        private String name;
        private String position;
        private String avatar;
        private String type;

        public String getCount() {
            return count;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPosition() {
            return position;
        }

        public void setPosition(String position) {
            this.position = position;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
