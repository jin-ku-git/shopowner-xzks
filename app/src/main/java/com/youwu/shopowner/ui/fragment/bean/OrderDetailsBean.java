package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2022/9/20
 */
public class OrderDetailsBean implements Serializable {


    /**
     * order_sn : 100101010199981663642711
     * member_name : 餘生(13468214148)
     * created_at : 2022-09-20 10:58:31
     * shipping_type : 小程序
     * delivery_method : 外卖
     * order_status : 已支付
     * pickup_time :
     * pickup_status : 0
     * mark : null
     * goods_list : [{"goods_id":5,"goods_sku":"452073127850154836","goods_name":"火山石烤肠-规格：1根/份","goods_price":3,"goods_number":1,"goods_amount":3},{"goods_id":62,"goods_sku":"453218150004169812","goods_name":"蔬菜-规格：蔬菜","goods_price":12,"goods_number":1,"goods_amount":12}]
     * goods_amount : 15
     * packing_fee : 0
     * total_amount : 15
     * reduced_amount : 5
     * mal : 0
     * amount : 10
     * pay_type : 余额
     * pick_code : B015
     * pickup_address : 河东区山东先每餐饮管理有限公司(凤凰大街北)
     * tableware_number : 0
     */

    private String order_sn;
    private String member_name;
    private String member_tel;
    private String created_at;//创建时间
    private String payment_time;//支付时间
    private String shipping_type;
    private String delivery_method;
    private String order_status;
    private String pickup_time;
    private String appointment_time;//预计取餐时间
    private int pickup_status;
    private String mark;
    private String goods_amount;
    private int packing_fee;
    private String total_amount;
    private String reduced_amount;
    private int mal;
    private String amount;
    private String pay_type;
    private String pick_code;
    private String pickup_address;
    private int tableware_number;
    private List<GoodsListBean> goods_list;

    public String getMember_tel() {
        return member_tel;
    }

    public void setMember_tel(String member_tel) {
        this.member_tel = member_tel;
    }

    public String getPayment_time() {
        return payment_time;
    }

    public void setPayment_time(String payment_time) {
        this.payment_time = payment_time;
    }

    public String getAppointment_time() {
        return appointment_time;
    }

    public void setAppointment_time(String appointment_time) {
        this.appointment_time = appointment_time;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getShipping_type() {
        return shipping_type;
    }

    public void setShipping_type(String shipping_type) {
        this.shipping_type = shipping_type;
    }

    public String getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(String delivery_method) {
        this.delivery_method = delivery_method;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getPickup_time() {
        return pickup_time;
    }

    public void setPickup_time(String pickup_time) {
        this.pickup_time = pickup_time;
    }

    public int getPickup_status() {
        return pickup_status;
    }

    public void setPickup_status(int pickup_status) {
        this.pickup_status = pickup_status;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getGoods_amount() {
        return goods_amount;
    }

    public void setGoods_amount(String goods_amount) {
        this.goods_amount = goods_amount;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getReduced_amount() {
        return reduced_amount;
    }

    public void setReduced_amount(String reduced_amount) {
        this.reduced_amount = reduced_amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public int getPacking_fee() {
        return packing_fee;
    }

    public void setPacking_fee(int packing_fee) {
        this.packing_fee = packing_fee;
    }



    public int getMal() {
        return mal;
    }

    public void setMal(int mal) {
        this.mal = mal;
    }


    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public String getPick_code() {
        return pick_code;
    }

    public void setPick_code(String pick_code) {
        this.pick_code = pick_code;
    }

    public String getPickup_address() {
        return pickup_address;
    }

    public void setPickup_address(String pickup_address) {
        this.pickup_address = pickup_address;
    }

    public int getTableware_number() {
        return tableware_number;
    }

    public void setTableware_number(int tableware_number) {
        this.tableware_number = tableware_number;
    }

    public List<GoodsListBean> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<GoodsListBean> goods_list) {
        this.goods_list = goods_list;
    }

    public static class GoodsListBean {
        /**
         * goods_id : 5
         * goods_sku : 452073127850154836
         * goods_name : 火山石烤肠-规格：1根/份
         * goods_price : 3
         * goods_number : 1
         * goods_amount : 3
         */

        private int goods_id;
        private String goods_sku;
        private String goods_name;
        private String goods_price;
        private int goods_number;
        private String goods_amount;
        private String goods_thumb;

        public String getGoods_thumb() {
            return goods_thumb;
        }

        public void setGoods_thumb(String goods_thumb) {
            this.goods_thumb = goods_thumb;
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


        public int getGoods_number() {
            return goods_number;
        }

        public void setGoods_number(int goods_number) {
            this.goods_number = goods_number;
        }

        public String getGoods_price() {
            return goods_price;
        }

        public void setGoods_price(String goods_price) {
            this.goods_price = goods_price;
        }

        public String getGoods_amount() {
            return goods_amount;
        }

        public void setGoods_amount(String goods_amount) {
            this.goods_amount = goods_amount;
        }
    }
}
