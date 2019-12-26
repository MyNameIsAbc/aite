package com.example.jianancangku.bean;

import java.io.Serializable;

public class WorkerAloneBean implements Serializable {

    /**
     * info : {"name":"去","type":"1","warehouse_clerk_id":"31","is_work":"1","account_number":"011"}
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
         * name : 去
         * type : 1
         * warehouse_clerk_id : 31
         * is_work : 1
         * account_number : 011
         */

        private String name;
        private String type;
        private String warehouse_clerk_id;
        private String is_work;
        private String account_number;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getWarehouse_clerk_id() {
            return warehouse_clerk_id;
        }

        public void setWarehouse_clerk_id(String warehouse_clerk_id) {
            this.warehouse_clerk_id = warehouse_clerk_id;
        }

        public String getIs_work() {
            return is_work;
        }

        public void setIs_work(String is_work) {
            this.is_work = is_work;
        }

        public String getAccount_number() {
            return account_number;
        }

        public void setAccount_number(String account_number) {
            this.account_number = account_number;
        }
    }
}
