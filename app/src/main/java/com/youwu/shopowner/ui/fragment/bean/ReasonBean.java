package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;

public class ReasonBean implements Serializable {
    private String name;//名称
    private String number;//数量



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
