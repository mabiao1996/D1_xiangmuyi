package com.bwie.text.bean;

/**
 * Created by mabiao on 2017/9/6.
 */

public class SBean {

    /**
     * name : 头条
     * isSelect : true
     */

    private String name;
    private boolean isSelect;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsSelect() {
        return isSelect;
    }

    public void setIsSelect(boolean isSelect) {
        this.isSelect = isSelect;
    }

    @Override
    public String toString() {
        return "SBean{" +
                "name='" + name + '\'' +
                ", isSelect=" + isSelect +
                '}';
    }
}
