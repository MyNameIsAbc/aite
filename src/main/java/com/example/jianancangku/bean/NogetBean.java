package com.example.jianancangku.bean;

import java.io.Serializable;
import java.util.List;

public class NogetBean implements Serializable {

    /**
     * list : [{"warehouse_order_id":"139","package_sn":"2000000000122701","business_address":"柬埔寨金边马卡拉区A","create_time":"2019-10-22 16:38:45"},{"warehouse_order_id":"124","package_sn":"2000000000121901","business_address":"柬埔寨金边马卡拉区柬埔寨金边","create_time":"2019-10-22 10:04:37"},{"warehouse_order_id":"122","package_sn":"2000000000121601","business_address":"柬埔寨金边马卡拉区柬埔寨金边","create_time":"2019-10-21 15:49:00"},{"warehouse_order_id":"58","package_sn":"2000000000117101","business_address":"柬埔寨金边马卡拉区A","create_time":"2019-10-18 14:08:25"},{"warehouse_order_id":"51","package_sn":"2000000000116501","business_address":"柬埔寨金边马卡拉区A","create_time":"2019-10-18 12:13:00"},{"warehouse_order_id":"50","package_sn":"2000000000116401","business_address":"柬埔寨金边马卡拉区B","create_time":"2019-10-18 12:06:31"}]
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
         * warehouse_order_id : 139
         * package_sn : 2000000000122701
         * business_address : 柬埔寨金边马卡拉区A
         * create_time : 2019-10-22 16:38:45
         */

        private String warehouse_order_id;
        private String package_sn;
        private String business_address;
        private String create_time;
        private String delivery_time;

        public String getDelivery_time() {
            return delivery_time;
        }

        public void setDelivery_time(String delivery_time) {
            this.delivery_time = delivery_time;
        }

        public String getDelivery_name() {
            return delivery_name;
        }

        public void setDelivery_name(String delivery_name) {
            this.delivery_name = delivery_name;
        }

        private String delivery_name;



        public String getWarehouse_order_id() {
            return warehouse_order_id;
        }

        public void setWarehouse_order_id(String warehouse_order_id) {
            this.warehouse_order_id = warehouse_order_id;
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

        public String getCreate_time() {
            return create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }
    }
}
