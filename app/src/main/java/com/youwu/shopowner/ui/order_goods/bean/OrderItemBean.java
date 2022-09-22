package com.youwu.shopowner.ui.order_goods.bean;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderItemBean implements Serializable {

    /**
     * template_id : 1
     * item_id : 401367210750252765
     * item_name : 手抓1
     * item_sku : 401367211329065171:401367211710747972
     * sku_name : 长箱
     * item_sku_name : 手抓1长箱
     * item_price : 3.22
     * moq : 1
     * is_standard : 1
     */



    private String goods_id;
    private String goods_sku;
    private String goods_name;
    private String order_price;

    private int  quantity;//订货数量
    private int  type;
    /**
     * id : 1
     * store_id : 1
     * cargo_order_id : 1
     * order_sn : 100101010199991660119538
     * goods_id : 1133
     * order_price : 5
     * order_quantity : 10
     * total_price : 50
     * actual_quantity : 0
     * created_at : 2022-08-10 16:18:58
     * updated_at : 2022-08-11 12:03:43
     */

    private int id;
    private int store_id;
    private int cargo_order_id;
    private String order_sn;
    private int total_price;
    private int actual_quantity;
    private int return_order_quantity;
    private String mark;
    private String created_at;
    private String updated_at;

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getReturn_order_quantity() {
        return return_order_quantity;
    }

    public void setReturn_order_quantity(int return_order_quantity) {
        this.return_order_quantity = return_order_quantity;
    }

    public String getGoods_id() {
        return goods_id;
    }

    public void setGoods_id(String goods_id) {
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

    public String getOrder_price() {
        return order_price;
    }

    public void setOrder_price(String order_price) {
        this.order_price = order_price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStore_id() {
        return store_id;
    }

    public void setStore_id(int store_id) {
        this.store_id = store_id;
    }

    public int getCargo_order_id() {
        return cargo_order_id;
    }

    public void setCargo_order_id(int cargo_order_id) {
        this.cargo_order_id = cargo_order_id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public int getActual_quantity() {
        return actual_quantity;
    }

    public void setActual_quantity(int actual_quantity) {
        this.actual_quantity = actual_quantity;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
