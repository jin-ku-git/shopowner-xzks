package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;

public class ReasonBean implements Serializable {
    private int id;
    private String name;//名称
    private String number;//数量


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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
