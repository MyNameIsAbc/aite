package com.example.jianancangku.utils;

public enum ROM {
    MIUI, // 小米
    Flyme, // 魅族
    EMUI, // 华为
    ColorOS, // OPPO
    FuntouchOS, // vivo
    SmartisanOS, // 锤子
    EUI, // 乐视
    Sense, // HTC
    AmigoOS, // 金立
    _360OS, // 奇酷360
    NubiaUI, // 努比亚
    H2OS, // 一加
    YunOS, // 阿里巴巴
    YuLong, // 酷派

    SamSung, // 三星
    Sony, // 索尼
    Lenovo, // 联想
    LG, // LG

    Google, // 原生

    Other; // CyanogenMod, Lewa OS, 百度云OS, Tencent OS, 深度OS, IUNI OS, Tapas OS, Mokee

    private int baseVersion = -1;
    private String version;

    public int getBaseVersion() {
        return baseVersion;
    }

    void setBaseVersion(int baseVersion) {
        this.baseVersion = baseVersion;
    }

    public String getVersion() {
        return version;
    }

    void setVersion(String version) {
        this.version = version;
    }
}