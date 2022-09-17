package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;
import java.util.List;

public class XXCOrderBean implements Serializable {

    /**
     * id : 4
     * order_sn : 10299961653793052
     * order_sn_name : 堂食订单：10299961653793052
     * member_info : 哟哟哟哟哟哟(16619912724)
     * goods_list : [{"goods_name":"商品名称","goods_price":1.99,"goods_quantity":2}]
     * total_amount : 7.33
     * pay_amount : 7.33
     * order_refund_data : {"order_refund_id":1,"refund_sn":"1","order_refund_status":1,"order_refund_type":0,"order_refund_type_name":"部分退款","order_refund_reason":""}
     */

    private int id;
    private String order_sn;
    private String pay_sn;
    private String order_sn_name;
    private String member_info;
    private double total_amount;
    private double pay_amount;
    private int order_status;//订单状态
    private String time_msg;//下单时间
    private List<GoodsListBean> goods_list;


    private OrderRefundDataBean order_refund_data;

    public String getPay_sn() {
        return pay_sn;
    }

    public void setPay_sn(String pay_sn) {
        this.pay_sn = pay_sn;
    }

    public int getOrder_status() {
        return order_status;
    }

    public void setOrder_status(int order_status) {
        this.order_status = order_status;
    }

    public String getTime_msg() {
        return time_msg;
    }

    public void setTime_msg(String time_msg) {
        this.time_msg = time_msg;
    }

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

    public String getOrder_sn_name() {
        return order_sn_name;
    }

    public void setOrder_sn_name(String order_sn_name) {
        this.order_sn_name = order_sn_name;
    }

    public String getMember_info() {
        return member_info;
    }

    public void setMember_info(String member_info) {
        this.member_info = member_info;
    }

    public double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(double total_amount) {
        this.total_amount = total_amount;
    }

    public double getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(double pay_amount) {
        this.pay_amount = pay_amount;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }


    public OrderRefundDataBean getOrder_refund_data() {
        return order_refund_data;
    }

    public void setOrder_refund_data(OrderRefundDataBean order_refund_data) {
        this.order_refund_data = order_refund_data;
    }


    public static class GoodsListBean {
        /**
         * goods_name : 商品名称
         * goods_price : 1.99
         * goods_quantity : 2
         */

        private String goods_name;
        private double goods_price;
        private int goods_quantity;

        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public double getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(double goods_price) {
            this.goods_price = goods_price;
        }

        public int getGoods_quantity() {
            return goods_quantity;
        }

        public void setGoods_quantity(int goods_quantity) {
            this.goods_quantity = goods_quantity;
        }
    }

    public static class OrderRefundDataBean {
        /**
         * order_refund_id : 1
         * refund_sn : 1
         * order_refund_status : 1
         * order_refund_type : 0
         * order_refund_type_name : 部分退款
         * order_refund_reason :
         */

        private int order_refund_id;
        private String refund_sn;
        private int order_refund_status;
        private int order_refund_type;
        private String order_refund_type_name;
        private String order_refund_mark   ;//退款描述
        private String order_refund_reason;//退款原因
        private List<String> order_refund_img;//退款图片

        public String getOrder_refund_mark() {
            return order_refund_mark;
        }

        public void setOrder_refund_mark(String order_refund_mark) {
            this.order_refund_mark = order_refund_mark;
        }

        public List<String> getOrder_refund_img() {
            return order_refund_img;
        }

        public void setOrder_refund_img(List<String> order_refund_img) {
            this.order_refund_img = order_refund_img;
        }

        public int getOrder_refund_id() {
            return order_refund_id;
        }

        public void setOrder_refund_id(int order_refund_id) {
            this.order_refund_id = order_refund_id;
        }

        public String getRefund_sn() {
            return refund_sn;
        }

        public void setRefund_sn(String refund_sn) {
            this.refund_sn = refund_sn;
        }

        public int getOrder_refund_status() {
            return order_refund_status;
        }

        public void setOrder_refund_status(int order_refund_status) {
            this.order_refund_status = order_refund_status;
        }

        public int getOrder_refund_type() {
            return order_refund_type;
        }

        public void setOrder_refund_type(int order_refund_type) {
            this.order_refund_type = order_refund_type;
        }

        public String getOrder_refund_type_name() {
            return order_refund_type_name;
        }

        public void setOrder_refund_type_name(String order_refund_type_name) {
            this.order_refund_type_name = order_refund_type_name;
        }

        public String getOrder_refund_reason() {
            return order_refund_reason;
        }

        public void setOrder_refund_reason(String order_refund_reason) {
            this.order_refund_reason = order_refund_reason;
        }
    }

}
