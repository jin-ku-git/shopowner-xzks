package com.youwu.shopowner.ui.main;

import java.io.Serializable;

/**
 * @author: Administrator
 * @date: 2022/9/19
 */
public class EventBusBean implements Serializable {

    private int type;
    private int state;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public EventBusBean(int type, int state) {
        this.type = type;
        this.state = state;
    }
}
