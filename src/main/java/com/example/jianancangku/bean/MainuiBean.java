package com.example.jianancangku.bean;

public class MainuiBean {
    /**
     * info : {"account_number":"0","name":"泰国管理员","position":"主管","avatar":"https://aitecc.com/data/upload/shop/avatar/06243116531757524.jpg"}
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
         * account_number : 0
         * name : 泰国管理员
         * position : 主管
         * avatar : https://aitecc.com/data/upload/shop/avatar/06243116531757524.jpg
         */

        private String account_number;
        private String name;
        private String position;
        private String avatar;

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
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
    }
}
