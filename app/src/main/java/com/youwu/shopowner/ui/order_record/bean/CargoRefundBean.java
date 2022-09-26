package com.youwu.shopowner.ui.order_record.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2022/9/23
 */
public class CargoRefundBean implements Serializable {

    /**
     * id : 1
     * order_sn : 101919010199991663749452
     * created_at : 2022-09-21 16:37:32
     * total_price : 1.00
     * status : 2
     * status_name : 已通过
     * mark : 备注
     * kind_quantity : 1
     * total_quantity : 1
     * details : [{"goods_name":"糯米鸡卷饼","order_price":1,"quantity":1}]
     */

    private int id;
    private String order_sn;
    private String created_at;
    private String total_price;
    private int status;
    private String status_name;
    private String mark;
    private int kind_quantity;
    private int total_quantity;
    private List<DetailsBean> details;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTotal_price() {
        return total_price;
    }

    public void setTotal_price(String total_price) {
        this.total_price = total_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public int getKind_quantity() {
        return kind_quantity;
    }

    public void setKind_quantity(int kind_quantity) {
        this.kind_quantity = kind_quantity;
    }

    public int getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(int total_quantity) {
        this.total_quantity = total_quantity;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * goods_name : 糯米鸡卷饼
         * order_price : 1
         * quantity : 1
         */

        private String goods_name;
        private int order_price;
        private int quantity;
        private String image;

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public int getOrder_price() {
            return order_price;
        }

        public void setOrder_price(int order_price) {
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
