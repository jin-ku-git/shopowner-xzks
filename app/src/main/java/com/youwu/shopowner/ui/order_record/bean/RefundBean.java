package com.youwu.shopowner.ui.order_record.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2022/9/23
 */
public class RefundBean implements Serializable {

    /**
     * rows : [{"id":1,"order_sn":"101919010199991663749452","created_at":"2022-09-21 16:37:32","status":2,"total_quantity":1,"status_name":"已通过"},{"id":2,"order_sn":"101919010199991663925527","created_at":"2022-09-23 17:32:07","status":1,"total_quantity":1,"status_name":"待审核"}]
     * total : 2
     */

    private int total;
    private List<RowsBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<RowsBean> getRows() {
        return rows;
    }

    public void setRows(List<RowsBean> rows) {
        this.rows = rows;
    }

    public static class RowsBean {
        /**
         * id : 1
         * order_sn : 101919010199991663749452
         * created_at : 2022-09-21 16:37:32
         * status : 2
         * total_quantity : 1
         * status_name : 已通过
         */

        private int id;
        private String order_sn;
        private String created_at;
        private int status;
        private int total_quantity;
        private String status_name;

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

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getTotal_quantity() {
            return total_quantity;
        }

        public void setTotal_quantity(int total_quantity) {
            this.total_quantity = total_quantity;
        }

        public String getStatus_name() {
            return status_name;
        }

        public void setStatus_name(String status_name) {
            this.status_name = status_name;
        }
    }
}
