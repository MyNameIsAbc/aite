package com.example.jianancangku.bean;

import java.io.Serializable;

public class QrBean implements Serializable {


    /**
     * error : 此订单已被泰国管理员操作入库!
     */

    private String error;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    private String message;



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
