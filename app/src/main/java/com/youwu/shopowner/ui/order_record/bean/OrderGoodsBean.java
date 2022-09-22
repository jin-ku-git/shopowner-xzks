package com.youwu.shopowner.ui.order_record.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 销售单据表
 */
public class OrderGoodsBean implements Serializable {


    /**
     * id : 1
     * store_id : 1
     * p_id : 0
     * store_name : 测试门店
     * cashier_id : 1
     * cashier_name : 收银员1
     * arrival_time : 2022-01-11 00:00:00
     * actual_arrival_time : 2022-08-09 11:23:47
     * status : 3
     * total_price : 50
     * created_at : 2022-08-09 10:39:02
     * updated_at : 2022-08-09 11:23:47
     * status_name : 已签收
     * details : [{"id":1,"store_id":1,"cargo_order_id":1,"goods_id":1133,"goods_sku":"2206051644232","goods_name":"鸡蛋鸡排手抓饼","order_price":5,"order_quantity":10,"total_price":50,"actual_quantity":5,"created_at":"2022-08-09 10:39:02","updated_at":"2022-08-09 11:23:47"}]
     */

    private int id;
    private String order_sn;
    private String cargo_order_sn;
    private int store_id;
    private int p_id;
    private String store_name;
    private int cashier_id;
    private String cashier_name;
    private String arrival_time;
    private String actual_total_price;
    private String actual_arrival_time;
    private int status;
    private double total_price;
    private double total_quantity;
    private int audit;
    private String audit_name;
    private String created_at;
    private String updated_at;
    private String status_name;
    private List<DetailsBean> details;

    public String getActual_total_price() {
        return actual_total_price;
    }

    public void setActual_total_price(String actual_total_price) {
        this.actual_total_price = actual_total_price;
    }

    public String getCargo_order_sn() {
        return cargo_order_sn;
    }

    public void setCargo_order_sn(String cargo_order_sn) {
        this.cargo_order_sn = cargo_order_sn;
    }

    public int getAudit() {
        return audit;
    }

    public void setAudit(int audit) {
        this.audit = audit;
    }

    public String getAudit_name() {
        return audit_name;
    }

    public void setAudit_name(String audit_name) {
        this.audit_name = audit_name;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public void setTotal_price(double total_price) {
        this.total_price = total_price;
    }

    public double getTotal_quantity() {
        return total_quantity;
    }

    public void setTotal_quantity(double total_quantity) {
        this.total_quantity = total_quantity;
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

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getStore_name() {
        return store_name;
    }

    public void setStore_name(String store_name) {
        this.store_name = store_name;
    }

    public int getCashier_id() {
        return cashier_id;
    }

    public void setCashier_id(int cashier_id) {
        this.cashier_id = cashier_id;
    }

    public String getCashier_name() {
        return cashier_name;
    }

    public void setCashier_name(String cashier_name) {
        this.cashier_name = cashier_name;
    }

    public String getArrival_time() {
        return arrival_time;
    }

    public void setArrival_time(String arrival_time) {
        this.arrival_time = arrival_time;
    }

    public String getActual_arrival_time() {
        return actual_arrival_time;
    }

    public void setActual_arrival_time(String actual_arrival_time) {
        this.actual_arrival_time = actual_arrival_time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public double getTotal_price() {
        return total_price;
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

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }

    public static class DetailsBean {
        /**
         * id : 1
         * store_id : 1
         * cargo_order_id : 1
         * goods_id : 1133
         * goods_sku : 2206051644232
         * goods_name : 鸡蛋鸡排手抓饼
         * order_price : 5
         * order_quantity : 10
         * total_price : 50
         * actual_quantity : 5
         * created_at : 2022-08-09 10:39:02
         * updated_at : 2022-08-09 11:23:47
         */

        private int id;
        private int store_id;
        private int cargo_order_id;
        private int goods_id;
        private String goods_sku;
        private String goods_name;
        private String order_price;
        private int order_quantity;//订货数量
        private double total_price;
        private String actual_quantity;//确认收货数量
        private int return_order_quantity;
        private int return_actual_quantity;
        private String created_at;
        private String updated_at;
        private String Actual;
        private String mark;

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

        public int getReturn_actual_quantity() {
            return return_actual_quantity;
        }

        public void setReturn_actual_quantity(int return_actual_quantity) {
            this.return_actual_quantity = return_actual_quantity;
        }

        public String getActual() {
            return Actual;
        }

        public void setActual(String actual) {
            Actual = actual;
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

        public String getOrder_price() {
            return order_price;
        }

        public void setOrder_price(String order_price) {
            this.order_price = order_price;
        }

        public int getQuantity() {
            return order_quantity;
        }

        public void setQuantity(int order_quantity) {
            this.order_quantity = order_quantity;
        }

        public double getTotal_price() {
            return total_price;
        }

        public void setTotal_price(double total_price) {
            this.total_price = total_price;
        }

        public String getActual_quantity() {
            return actual_quantity;
        }

        public void setActual_quantity(String actual_quantity) {
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
}
