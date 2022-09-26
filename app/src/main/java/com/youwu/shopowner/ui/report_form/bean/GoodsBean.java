package com.youwu.shopowner.ui.report_form.bean;

/**
 * @author: Administrator
 * @date: 2022/9/26
 */
public class GoodsBean {

    /**
     * goods_quantity : 4
     * goods_id : 16
     * goods_sku : 452515980703309207
     * goods_name : 汉斯牛肉包-规格：1个/份
     */

    private int goods_quantity;
    private int goods_id;
    private String goods_sku;
    private String goods_name;

    public int getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(int goods_quantity) {
        this.goods_quantity = goods_quantity;
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
}
