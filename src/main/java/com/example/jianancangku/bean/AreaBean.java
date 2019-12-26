package com.example.jianancangku.bean;

import java.util.List;

public class AreaBean {
    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    private List<ListBean> list;

    /**
     * area_id : 85918
     * area_name : 金边
     * citylist : [{"area_id":"85919","area_name":"马卡拉区","arealist":[]},{"area_id":"85920","area_name":"堆谷区","arealist":[]},{"area_id":"85921","area_name":"隆边区","arealist":[]},{"area_id":"85922","area_name":"东阁区","arealist":[]},{"area_id":"85923","area_name":"棉芷区","arealist":[]},{"area_id":"85924","area_name":"亚西阁区","arealist":[]},{"area_id":"85925","area_name":"波森芷区","arealist":[]},{"area_id":"85926","area_name":"索森区","arealist":[]},{"area_id":"85927","area_name":"桑园区","arealist":[]},{"area_id":"85928","area_name":"铁桥头区","arealist":[]},{"area_id":"85929","area_name":"水浄华区","arealist":[]},{"area_id":"85930","area_name":"普诺夫溪区","arealist":[]}]
     */
    public static class ListBean {

        private String area_id;
        private String area_name;
        private List<CitylistBean> citylist;

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public List<CitylistBean> getCitylist() {
            return citylist;
        }

        public void setCitylist(List<CitylistBean> citylist) {
            this.citylist = citylist;
        }

        public static class CitylistBean {
            /**
             * area_id : 85919
             * area_name : 马卡拉区
             * arealist : []
             */

            private String area_id;
            private String area_name;
            private List<?> arealist;

            public String getArea_id() {
                return area_id;
            }

            public void setArea_id(String area_id) {
                this.area_id = area_id;
            }

            public String getArea_name() {
                return area_name;
            }

            public void setArea_name(String area_name) {
                this.area_name = area_name;
            }

            public List<?> getArealist() {
                return arealist;
            }

            public void setArealist(List<?> arealist) {
                this.arealist = arealist;
            }
        }
    }
}
