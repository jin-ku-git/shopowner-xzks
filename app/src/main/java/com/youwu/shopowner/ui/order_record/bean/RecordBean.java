package com.youwu.shopowner.ui.order_record.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2022/9/22
 */
public class RecordBean {

    /**
     * rows : [{"id":3,"store_id":19,"cashier_id":11,"cashier_name":"上级门店收银员","total_quantity":1,"total_kind_quantity":1,"total_amount":1,"mark":"备注","created_at":"2022-09-21 14:18:14","updated_at":"2022-09-21 14:18:14"},{"id":2,"store_id":19,"cashier_id":11,"cashier_name":"上级门店收银员","total_quantity":1,"total_kind_quantity":1,"total_amount":1,"mark":"备注","created_at":"2022-09-21 14:05:45","updated_at":"2022-09-21 14:05:45"},{"id":1,"store_id":19,"cashier_id":1,"cashier_name":"G","total_quantity":1,"total_kind_quantity":1,"total_amount":1,"mark":"报损备注","created_at":"2022-09-21 11:57:16","updated_at":"2022-09-21 11:57:16"}]
     * total : 3
     */

    private int total;
    private ArrayList<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<RowsBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 3
         * store_id : 19
         * cashier_id : 11
         * cashier_name : 上级门店收银员
         * total_quantity : 1
         * total_kind_quantity : 1
         * total_amount : 1
         * mark : 备注
         * created_at : 2022-09-21 14:18:14
         * updated_at : 2022-09-21 14:18:14
         */

        private int id;
        private int store_id;
        private int cashier_id;
        private String cashier_name;
        private int total_quantity;
        private int total_kind_quantity;
        private int total_amount;
        private int total;
        private String mark;
        private String created_at;
        private String updated_at;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
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

        public int getTotal_quantity() {
            return total_quantity;
        }

        public void setTotal_quantity(int total_quantity) {
            this.total_quantity = total_quantity;
        }

        public int getTotal_kind_quantity() {
            return total_kind_quantity;
        }

        public void setTotal_kind_quantity(int total_kind_quantity) {
            this.total_kind_quantity = total_kind_quantity;
        }

        public int getTotal_amount() {
            return total_amount;
        }

        public void setTotal_amount(int total_amount) {
            this.total_amount = total_amount;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
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
