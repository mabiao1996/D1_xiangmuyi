package com.bwie.text.bean;

/**
 * Created by mabiao on 2017/9/11.
 */

public class BanBean {
    private int versionCode=1;
    private String url;

    public BanBean() {
    }

    public BanBean(int versionCode, String url) {
        this.versionCode = versionCode;
        this.url = url;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "BanBean{" +
                "versionCode=" + versionCode +
                ", url='" + url + '\'' +
                '}';
    }
}
