package com.youwu.shopowner.ui.fragment.bean;

import com.chad.library.adapter.base.entity.SectionEntity;

import java.io.Serializable;

/**
 * Created by Raul_lsj on 2018/3/22.
 */

public class ScrollBean extends SectionEntity<ScrollBean.SAASOrderBean> {

    public String id;
    public ScrollBean(boolean isHeader, String header,String id) {
        super(isHeader, header);
        this.id=id;
    }

    public ScrollBean(ScrollBean.SAASOrderBean bean) {
        super(bean);
    }

    public static class SAASOrderBean implements Serializable {


        /**
         * id : 1
         * goods_id : 1133
         * goods_sku : 2206051644232
         * goods_name : 鸡蛋鸡排手抓饼
         * order_price : 5
         */

        private int id;
        private String goods_id;
        private String goods_sku;
        private String goods_image;
        private String goods_name;
        private String store_name;
        private String order_price;
        private int change_stock;
        private int quantity;
        private int order_quantity;
        private int return_order_quantity;
        private int type;
        private String reason_name;
        private int stock;

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        public int getReturn_order_quantity() {
            return return_order_quantity;
        }

        public void setReturn_order_quantity(int return_order_quantity) {
            this.return_order_quantity = return_order_quantity;
        }

        public int getOrder_quantity() {
            return order_quantity;
        }

        public void setOrder_quantity(int order_quantity) {
            this.order_quantity = order_quantity;
        }

        public int getChange_stock() {
            return change_stock;
        }

        public void setChange_stock(int change_stock) {
            this.change_stock = change_stock;
        }

        public String getReason_name() {
            return reason_name;
        }

        public void setReason_name(String reason_name) {
            this.reason_name = reason_name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getGoods_image() {
            return goods_image;
        }

        public void setGoods_image(String goods_image) {
            this.goods_image = goods_image;
        }

        public String getStore_name() {
            return store_name;
        }

        public void setStore_name(String store_name) {
            this.store_name = store_name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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
    }

}
