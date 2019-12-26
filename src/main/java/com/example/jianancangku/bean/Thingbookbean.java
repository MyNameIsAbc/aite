package com.example.jianancangku.bean;

import java.io.Serializable;
import java.util.List;

public class Thingbookbean implements Serializable {

    /**
     * hasmore : 0
     * list : [{"create_time":"2019-10-14 11:54:13","business_address":"柬埔寨金边","bale_id":"13","package_sn":"530624369253018963"},{"create_time":"2019-10-12 10:28:19","business_address":"柬埔寨金边","bale_id":"12","package_sn":"590624191299800951"},{"create_time":"2019-10-12 10:21:22","business_address":"柬埔寨金边","bale_id":"11","package_sn":"690624190882972951"},{"create_time":"2019-10-11 18:19:29","business_address":"柬埔寨金边","bale_id":"10","package_sn":"170624133169440951"},{"create_time":"2019-10-11 18:19:26","business_address":"柬埔寨金边","bale_id":"9","package_sn":"400624133166643951"},{"create_time":"2019-10-11 18:19:24","business_address":"柬埔寨金边","bale_id":"8","package_sn":"480624133164206951"},{"create_time":"2019-10-11 17:10:17","business_address":"柬埔寨金边","bale_id":"7","package_sn":"950624129017737951"},{"create_time":"2019-10-11 17:10:00","business_address":"柬埔寨金边","bale_id":"6","package_sn":"700624129000956951"},{"create_time":"2019-10-11 17:09:11","business_address":"柬埔寨金边","bale_id":"5","package_sn":"690624128951409951"},{"create_time":"2019-10-11 17:08:43","business_address":"柬埔寨金边","bale_id":"4","package_sn":"710624128923925951"},{"create_time":"2019-10-11 17:08:41","business_address":"柬埔寨金边","bale_id":"3","package_sn":"720624128921643951"},{"create_time":"2019-10-11 17:08:38","business_address":"柬埔寨金边","bale_id":"2","package_sn":"610624128918456951"},{"create_time":"2019-10-11 17:08:35","business_address":"柬埔寨金边","bale_id":"1","package_sn":"230624128915972951"}]
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
         * create_time : 2019-10-14 11:54:13
         * business_address : 柬埔寨金边
         * bale_id : 13
         * package_sn : 530624369253018963
         */

        private String create_time;
        private String business_address;
        private String bale_id;
        private String package_sn;
        private String warehouse_order_id;

        public String getWarehouse_order_id() {
            return warehouse_order_id;
        }

        public void setWarehouse_order_id(String warehouse_order_id) {
            this.warehouse_order_id = warehouse_order_id;
        }

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

        public String getBale_id() {
            return bale_id;
        }

        public void setBale_id(String bale_id) {
            this.bale_id = bale_id;
        }

        public String getPackage_sn() {
            return package_sn;
        }

        public void setPackage_sn(String package_sn) {
            this.package_sn = package_sn;
        }
    }
}
