package com.example.jianancangku.bean;

import java.io.Serializable;
import java.util.List;

public class WorkerfixBean implements Serializable {

    /**
     * hasmore : 0
     * list : [{"account_number":"1861409738","name":"lzy","type":"入库员","warehouse_clerk_id":"17","allow_login":"1"},{"account_number":"3142535467@qq.com","name":"fyh","type":"入库员","warehouse_clerk_id":"16","allow_login":"1"},{"account_number":"123","name":"出库元","type":"出库员","warehouse_clerk_id":"7","allow_login":"1"},{"account_number":"18270281024","name":"泰国出库员","type":"出库员","warehouse_clerk_id":"3","allow_login":"1"},{"account_number":"18270281023","name":"泰国入库员","type":"入库员","warehouse_clerk_id":"2","allow_login":"1"}]
     */

    private int hasmore;
    private List<ListBean> list;

    public int getHasmore() {
        return hasmore;
    }

    public void setHasmore(int hasmore) {
        this.hasmore = hasmore;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * account_number : 1861409738
         * name : lzy
         * type : 入库员
         * warehouse_clerk_id : 17
         * allow_login : 1
         */

        private String account_number;
        private String name;
        private String type;
        private String warehouse_clerk_id;
        private String allow_login;

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

        public String getAllow_login() {
            return allow_login;
        }

        public void setAllow_login(String allow_login) {
            this.allow_login = allow_login;
        }
    }
}
