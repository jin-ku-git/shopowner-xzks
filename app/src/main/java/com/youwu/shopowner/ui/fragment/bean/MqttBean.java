package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;

/**
 * @author: Administrator
 * @date: 2022/8/12
 */
public class MqttBean implements Serializable {
    private String pay_sn;
    private int type;// 1 自动接单
    private int shipping_type;// 1外卖  2门店自提 3取餐点自提 4堂食5即食6预约
    private int order_status;// 1下单 2申请退款

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public int getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(int shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getPay_sn() {
        return pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
