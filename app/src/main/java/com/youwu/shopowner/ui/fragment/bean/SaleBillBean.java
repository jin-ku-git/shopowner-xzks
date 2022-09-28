package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 销售单据表
 */
public class SaleBillBean implements Serializable {


    /**
     * id : 8
     * refund_type : 1
     * shipping_type_name : 外卖
     * order_sn : 100101010199981663642711
     * created_at : 2022-09-20 10:58:31
     * total_amount : 15
     * pay_amount : 10
     * order_status : 1
     * order_status_name : 已支付
     * order_count : 2
     * order_details : [{"goods_name":"火山石烤肠-规格：1根/份","goods_thumb":"https://images2.youwuu.com/avatar/452072763730040454.jpg","goods_quantity":1},{"goods_name":"蔬菜-规格：蔬菜","goods_thumb":"","goods_quantity":1}]
     */

    private int id;
    private int refund_type;
    private String shipping_type_name;
    private String order_sn;
    private String refund_sn;
    private String created_at;
    private int total_amount;
    private String pay_amount;
    private int order_status;
    private String order_status_name;
    private String order_taking_status_name;
    private int refund_order_status;
    private String refund_order_status_name;
    private int order_count;
    private List<OrderDetailsBean> order_details;

    public String getOrder_taking_status_name() {
        return order_taking_status_name;
    }

    public void setOrder_taking_status_name(String order_taking_status_name) {
        this.order_taking_status_name = order_taking_status_name;
    }

    public String getRefund_sn() {
        return refund_sn;
    }

    public void setRefund_sn(String refund_sn) {
        this.refund_sn = refund_sn;
    }

    public int getRefund_order_status() {
        return refund_order_status;
    }

    public void setRefund_order_status(int refund_order_status) {
        this.refund_order_status = refund_order_status;
    }

    public String getRefund_order_status_name() {
        return refund_order_status_name;
    }

    public void setRefund_order_status_name(String refund_order_status_name) {
        this.refund_order_status_name = refund_order_status_name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRefund_type() {
        return refund_type;
    }

    public void setRefund_type(int refund_type) {
        this.refund_type = refund_type;
    }

    public String getShipping_type_name() {
        return shipping_type_name;
    }

    public void setShipping_type_name(String shipping_type_name) {
        this.shipping_type_name = shipping_type_name;
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

    public int getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(int total_amount) {
        this.total_amount = total_amount;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getOrder_status_name() {
        return order_status_name;
    }

    public void setOrder_status_name(String order_status_name) {
        this.order_status_name = order_status_name;
    }

    public int getOrder_count() {
        return order_count;
    }

    public void setOrder_count(int order_count) {
        this.order_count = order_count;
    }

    public List<OrderDetailsBean> getOrder_details() {
        return order_details;
    }

    public void setOrder_details(List<OrderDetailsBean> order_details) {
        this.order_details = order_details;
    }

    public static class OrderDetailsBean {
        /**
         * goods_name : 火山石烤肠-规格：1根/份
         * goods_thumb : https://images2.youwuu.com/avatar/452072763730040454.jpg
         * goods_quantity : 1
         */

        private String goods_name;
        private String goods_thumb;
        private int goods_quantity;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
        }

        public int getGoods_quantity() {
            return goods_quantity;
        }

        public void setGoods_quantity(int goods_quantity) {
            this.goods_quantity = goods_quantity;
        }
    }
}
