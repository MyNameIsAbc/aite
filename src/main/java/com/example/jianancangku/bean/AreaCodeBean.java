package com.example.jianancangku.bean;

import java.io.Serializable;
import java.util.List;

public class AreaCodeBean implements Serializable {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * area_code : 00855
         * code : +855
         * icon : https://piikmall.aitecc.com/data/upload/aitemanger/icon/06278176746942420.png
         * area_name : 柬埔寨
         * area_id : 85917
         */

        private String area_code;
        private String code;
        private String icon;
        private String area_name;
        private String area_id;

        public String getArea_code() {
            return area_code;
        }

        public void setArea_code(String area_code) {
            this.area_code = area_code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }
    }
}
