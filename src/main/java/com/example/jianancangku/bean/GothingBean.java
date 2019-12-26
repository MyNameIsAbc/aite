package com.example.jianancangku.bean;

import java.io.Serializable;
import java.util.List;

public class GothingBean implements Serializable {

    /**
     * list : [{"create_time":"2019-10-12 12:07:17","package_sn":"2000000000105801","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-12 11:14:07","package_sn":"2000000000105701","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-12 10:24:57","package_sn":"2000000000105401","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-12 10:03:24","package_sn":"2000000000104101","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-12 10:02:46","package_sn":"2000000000104801","business_address":"安徽省\t芜湖市\t三山区tgj","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-11 19:20:49","package_sn":"2000000000105101","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-11 14:42:09","package_sn":"2000000000104201","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-11 14:08:53","package_sn":"2000000000104001","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-11 11:59:41","package_sn":"2000000000103901","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-11 11:57:11","package_sn":"2000000000103801","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"},{"create_time":"2019-10-11 11:25:49","package_sn":"2000000000103701","business_address":"湖北省\t十堰市\t张湾区123","warehouse_clerk_name":"泰国入库员"}]
     * hasmore : 0
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
         * create_time : 2019-10-12 12:07:17
         * package_sn : 2000000000105801
         * business_address : 湖北省	十堰市	张湾区123
         * warehouse_clerk_name : 泰国入库员
         */

        private String create_time;
        private String package_sn;
        private String business_address;
        private String warehouse_clerk_name;

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getPackage_sn() {
            return package_sn;
        }

        public void setPackage_sn(String package_sn) {
            this.package_sn = package_sn;
        }

        public String getBusiness_address() {
            return business_address;
        }

        public void setBusiness_address(String business_address) {
            this.business_address = business_address;
        }

        public String getWarehouse_clerk_name() {
            return warehouse_clerk_name;
        }

        public void setWarehouse_clerk_name(String warehouse_clerk_name) {
            this.warehouse_clerk_name = warehouse_clerk_name;
        }
    }
}
