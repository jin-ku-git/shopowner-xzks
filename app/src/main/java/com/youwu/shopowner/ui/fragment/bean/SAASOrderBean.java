package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;

public class SAASOrderBean implements Serializable {


    /**
     * id : 1
     * goods_id : 1133
     * goods_sku : 2206051644232
     * goods_name : 鸡蛋鸡排手抓饼
     * order_price : 5
     */

    private int id;
    private int goods_id;
    private String goods_sku;
    private String goods_name;
    private double order_price;
    private String order_quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(int goods_id) {
        this.goods_id = goods_id;
    }

    public String getGoods_sku() {
        return goods_sku;
    }

    public void setGoods_sku(String goods_sku) {
        this.goods_sku = goods_sku;
    }

    public String getGoods_name() {
        return goods_name;
    }

    public void setGoods_name(String goods_name) {
        this.goods_name = goods_name;
    }

    public double getOrder_price() {
        return order_price;
    }

    public void setOrder_price(double order_price) {
        this.order_price = order_price;
    }

    public String getQuantity() {
        return order_quantity;
    }

    public void setQuantity(String order_quantity) {
        this.order_quantity = order_quantity;
    }
}
