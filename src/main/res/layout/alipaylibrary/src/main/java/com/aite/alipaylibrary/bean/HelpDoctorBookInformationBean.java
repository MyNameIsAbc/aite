package com.aite.alipaylibrary.bean;

import com.lzy.basemodule.bean.ErrorBean;

import java.io.Serializable;

/**
 * @Auther: liziyang
 * @datetime: 2019-11-23
 * @desc:
 */
public class HelpDoctorBookInformationBean extends ErrorBean implements Serializable {


    /**
     * advs : {"adv_title":"左侧广告","adv_img":"http://zhongbyi.aitecc.com/data/upload/shop/adv/06269860002434105.jpg","adv_url":"http://"}
     * info : {"id":"5","member_id":"7","title":"医生","doctor_name":"内科","department":"内科","class_id":"1","realname":"","mobile":"18614079728","province_id":"1","city_id":"2","area_id":"3","address":"北京天津河北娜娜山","start_time":"2019-11-27 12:00:00","end_time":"2020-01-01 12:00:00","duration":"840.0","remarks":"无","credit":"0.00","addtime":"1574733416","status":"0","order_status":"0","class_name":"陪伴","time_hi":"09:56","time_ymd":"2019-11-26","order_start_time":"","order_end_time":"","start_thumb":"","end_thumb":""}
     */

    private AdvsBean advs;
    private InfoBean info;

    public AdvsBean getAdvs() {
        return advs;
    }

    public void setAdvs(AdvsBean advs) {
        this.advs = advs;
    }

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class AdvsBean {
        /**
         * adv_title : 左侧广告
         * adv_img : http://zhongbyi.aitecc.com/data/upload/shop/adv/06269860002434105.jpg
         * adv_url : http://
         */

        private String adv_title;
        private String adv_img;
        private String adv_url;

        public String getAdv_title() {
            return adv_title;
        }

        public void setAdv_title(String adv_title) {
            this.adv_title = adv_title;
        }

        public String getAdv_img() {
            return adv_img;
        }

        public void setAdv_img(String adv_img) {
            this.adv_img = adv_img;
        }

        public String getAdv_url() {
            return adv_url;
        }

        public void setAdv_url(String adv_url) {
            this.adv_url = adv_url;
        }
    }

    public static class InfoBean {
        /**
         * id : 5
         * member_id : 7
         * title : 医生
         * doctor_name : 内科
         * department : 内科
         * class_id : 1
         * realname :
         * mobile : 18614079728
         * province_id : 1
         * city_id : 2
         * area_id : 3
         * address : 北京天津河北娜娜山
         * start_time : 2019-11-27 12:00:00
         * end_time : 2020-01-01 12:00:00
         * duration : 840.0
         * remarks : 无
         * credit : 0.00
         * addtime : 1574733416
         * status : 0
         * order_status : 0
         * class_name : 陪伴
         * time_hi : 09:56
         * time_ymd : 2019-11-26
         * order_start_time :
         * order_end_time :
         * start_thumb :
         * end_thumb :
         */

        private String id;
        private String member_id;
        private String title;
        private String doctor_name;
        private String department;
        private String class_id;
        private String realname;
        private String mobile;
        private String province_id;
        private String city_id;
        private String area_id;
        private String address;
        private String start_time;
        private String end_time;
        private String duration;
        private String remarks;
        private String credit;
        private String addtime;
        private String status;
        private String order_status;
        private String class_name;
        private String time_hi;
        private String time_ymd;
        private String order_start_time;
        private String order_end_time;
        private String start_thumb;
        private String end_thumb;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMember_id() {
            return member_id;
        }

        public void setMember_id(String member_id) {
            this.member_id = member_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDoctor_name() {
            return doctor_name;
        }

        public void setDoctor_name(String doctor_name) {
            this.doctor_name = doctor_name;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public String getClass_id() {
            return class_id;
        }

        public void setClass_id(String class_id) {
            this.class_id = class_id;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getProvince_id() {
            return province_id;
        }

        public void setProvince_id(String province_id) {
            this.province_id = province_id;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getArea_id() {
            return area_id;
        }

        public void setArea_id(String area_id) {
            this.area_id = area_id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getDuration() {
            return duration;
        }

        public void setDuration(String duration) {
            this.duration = duration;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }

        public String getCredit() {
            return credit;
        }

        public void setCredit(String credit) {
            this.credit = credit;
        }

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }

        public String getTime_hi() {
            return time_hi;
        }

        public void setTime_hi(String time_hi) {
            this.time_hi = time_hi;
        }

        public String getTime_ymd() {
            return time_ymd;
        }

        public void setTime_ymd(String time_ymd) {
            this.time_ymd = time_ymd;
        }

        public String getOrder_start_time() {
            return order_start_time;
        }

        public void setOrder_start_time(String order_start_time) {
            this.order_start_time = order_start_time;
        }

        public String getOrder_end_time() {
            return order_end_time;
        }

        public void setOrder_end_time(String order_end_time) {
            this.order_end_time = order_end_time;
        }

        public String getStart_thumb() {
            return start_thumb;
        }

        public void setStart_thumb(String start_thumb) {
            this.start_thumb = start_thumb;
        }

        public String getEnd_thumb() {
            return end_thumb;
        }

        public void setEnd_thumb(String end_thumb) {
            this.end_thumb = end_thumb;
        }
    }
}
