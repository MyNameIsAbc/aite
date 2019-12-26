package com.example.jianancangku.bean;

import java.io.Serializable;

public class KeyBean implements Serializable {

    /**
     * error : 两次密码不一致
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
