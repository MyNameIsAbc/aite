package com.example.jianancangku.bean;

import java.io.Serializable;
import java.util.List;

public class MsgCenterbean implements Serializable {

    /**
     * list : [{"title":"包裹出库提醒","content":"包裹2000000000109601还未出库，请尽快安排配送","add_time":"2019-10-17 11:12","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000107201还未出库，请尽快安排配送","add_time":"2019-10-14 14:37","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106801还未出库，请尽快安排配送","add_time":"2019-10-14 11:57","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106901还未出库，请尽快安排配送","add_time":"2019-10-14 11:53","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106601还未出库，请尽快安排配送","add_time":"2019-10-12 17:46","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106501还未出库，请尽快安排配送","add_time":"2019-10-12 17:32","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106401还未出库，请尽快安排配送","add_time":"2019-10-12 17:26","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106301还未出库，请尽快安排配送","add_time":"2019-10-12 17:15","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106201还未出库，请尽快安排配送","add_time":"2019-10-12 15:42","status":"1"},{"title":"包裹出库提醒","content":"包裹2000000000106001还未出库，请尽快安排配送","add_time":"2019-10-12 15:27","status":"1"}]
     * hasmore : 1
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
         * title : 包裹出库提醒
         * content : 包裹2000000000109601还未出库，请尽快安排配送
         * add_time : 2019-10-17 11:12
         * status : 1
         */

        private String title;
        private String content;
        private String add_time;
        private String status;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
