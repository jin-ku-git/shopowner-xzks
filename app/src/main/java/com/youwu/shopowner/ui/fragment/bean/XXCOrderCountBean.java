package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;

public class XXCOrderCountBean implements Serializable {
    private String order_count;//订单数量
    private int order_wait_count;//待接单数量
    private int order_mak_count;//待出餐数量
    private int order_refund_count;//退款数量

    public String getOrder_count() {
        return order_count;
    }

    public void setOrder_count(String order_count) {
        this.order_count = order_count;
    }

    public int getOrder_wait_count() {
        return order_wait_count;
    }

    public void setOrder_wait_count(int order_wait_count) {
        this.order_wait_count = order_wait_count;
    }

    public int getOrder_mak_count() {
        return order_mak_count;
    }

    public void setOrder_mak_count(int order_mak_count) {
        this.order_mak_count = order_mak_count;
    }

    public int getOrder_refund_count() {
        return order_refund_count;
    }

    public void setOrder_refund_count(int order_refund_count) {
        this.order_refund_count = order_refund_count;
    }
}
