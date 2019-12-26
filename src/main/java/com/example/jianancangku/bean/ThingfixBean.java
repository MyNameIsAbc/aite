package com.example.jianancangku.bean;

import java.io.Serializable;
import java.util.List;

public class ThingfixBean implements Serializable {

    /**
     * hasmore : 1
     * list : [{"create_time":"2019-10-12 10:09:51","business_address":"湖北省\t十堰市\t张湾区123","package_sn":"2000000000104001","type":"2"},{"create_time":"2019-10-11 19:28:40","business_address":"安徽省\t芜湖市\t三山区tgj","package_sn":"2000000000105201","type":"2"},{"create_time":"2019-10-11 19:27:50","business_address":"安徽省\t芜湖市\t三山区tgj","package_sn":"2000000000105201","type":"1"},{"create_time":"2019-10-11 19:08:32","business_address":"安徽省\t芜湖市\t三山区tgj","package_sn":"2000000000105001","type":"2"},{"create_time":"2019-10-11 19:08:21","business_address":"安徽省\t芜湖市\t三山区tgj","package_sn":"2000000000105001","type":"1"}]
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
         * create_time : 2019-10-12 10:09:51
         * business_address : 湖北省	十堰市	张湾区123
         * package_sn : 2000000000104001
         * type : 2
         */

        private String create_time;
        private String business_address;
        private String package_sn;
        private String type;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getBusiness_address() {
            return business_address;
        }

        public void setBusiness_address(String business_address) {
            this.business_address = business_address;
        }

        public String getPackage_sn() {
            return package_sn;
        }

        public void setPackage_sn(String package_sn) {
            this.package_sn = package_sn;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }
    }
}
