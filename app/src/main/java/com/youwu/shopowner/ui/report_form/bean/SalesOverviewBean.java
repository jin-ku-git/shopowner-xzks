package com.youwu.shopowner.ui.report_form.bean;

import java.io.Serializable;
import java.util.List;

public class SalesOverviewBean implements Serializable {


    /**
     * turnover : {"pay_amount":7.33,"before_pay_amount":7.33,"percentage":"0%"}
     * order_quantity : {"quantity":1,"before_quantity":1,"percentage":"0%"}
     * goods_quantity : {"quantity":1,"before_quantity":1,"percentage":"0%"}
     * guest_unit_price : {"money":"7.33","before_money":"7.33","percentage":"0%"}
     * order_refund_quantity : {"quantity":0,"before_money":0,"percentage":"0%"}
     * delivery_method : [{"name":"堂食","quantity":1}]
     * time_list : {"morn":0,"noon":0,"night":0}
     * goods_details_quantity : [{"goods_id":1123,"goods_name":"商品名称","goods_quantity":2}]
     */

    public TurnoverBean turnover;
    public OrderQuantityBean order_quantity;
    public GoodsQuantityBean goods_quantity;
    public GuestUnitPriceBean guest_unit_price;
    public OrderRefundQuantityBean order_refund_quantity;
    public TimeListBean time_list;
    public List<DeliveryMethodBean> delivery_method;
    public List<GoodsDetailsQuantityBean> goods_details_quantity;

    public TurnoverBean getTurnover() {
        return turnover;
    }

    public void setTurnover(TurnoverBean turnover) {
        this.turnover = turnover;
    }

    public OrderQuantityBean getQuantity() {
        return order_quantity;
    }

    public void setQuantity(OrderQuantityBean order_quantity) {
        this.order_quantity = order_quantity;
    }

    public GoodsQuantityBean getGoods_quantity() {
        return goods_quantity;
    }

    public void setGoods_quantity(GoodsQuantityBean goods_quantity) {
        this.goods_quantity = goods_quantity;
    }

    public GuestUnitPriceBean getGuest_unit_price() {
        return guest_unit_price;
    }

    public void setGuest_unit_price(GuestUnitPriceBean guest_unit_price) {
        this.guest_unit_price = guest_unit_price;
    }

    public OrderRefundQuantityBean getOrder_refund_quantity() {
        return order_refund_quantity;
    }

    public void setOrder_refund_quantity(OrderRefundQuantityBean order_refund_quantity) {
        this.order_refund_quantity = order_refund_quantity;
    }

    public TimeListBean getTime_list() {
        return time_list;
    }

    public void setTime_list(TimeListBean time_list) {
        this.time_list = time_list;
    }

    public List<DeliveryMethodBean> getDelivery_method() {
        return delivery_method;
    }

    public void setDelivery_method(List<DeliveryMethodBean> delivery_method) {
        this.delivery_method = delivery_method;
    }

    public List<GoodsDetailsQuantityBean> getGoods_details_quantity() {
        return goods_details_quantity;
    }

    public void setGoods_details_quantity(List<GoodsDetailsQuantityBean> goods_details_quantity) {
        this.goods_details_quantity = goods_details_quantity;
    }

    public static class TurnoverBean {
        /**
         * pay_amount : 7.33
         * before_pay_amount : 7.33
         * percentage : 0%
         */

        private String pay_amount;
        private String before_pay_amount;
        private String percentage;

        public String getPay_amount() {
            return pay_amount;
        }

        public void setPay_amount(String pay_amount) {
            this.pay_amount = pay_amount;
        }

        public String getBefore_pay_amount() {
            return before_pay_amount;
        }

        public void setBefore_pay_amount(String before_pay_amount) {
            this.before_pay_amount = before_pay_amount;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }

    public static class OrderQuantityBean {
        /**
         * quantity : 1
         * before_quantity : 1
         * percentage : 0%
         */

        private String quantity;
        private String before_quantity;
        private String percentage;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getBefore_quantity() {
            return before_quantity;
        }

        public void setBefore_quantity(String before_quantity) {
            this.before_quantity = before_quantity;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }

    public static class GoodsQuantityBean {
        /**
         * quantity : 1
         * before_quantity : 1
         * percentage : 0%
         */

        private String quantity;
        private String before_quantity;
        private String percentage;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getBefore_quantity() {
            return before_quantity;
        }

        public void setBefore_quantity(String before_quantity) {
            this.before_quantity = before_quantity;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }

    public static class GuestUnitPriceBean {
        /**
         * money : 7.33
         * before_money : 7.33
         * percentage : 0%
         */

        private String money;
        private String before_money;
        private String percentage;

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getBefore_money() {
            return before_money;
        }

        public void setBefore_money(String before_money) {
            this.before_money = before_money;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }

    public static class OrderRefundQuantityBean {
        /**
         * quantity : 0
         * before_money : 0
         * percentage : 0%
         */

        private String quantity;
        private String before_money;
        private String percentage;

        public String getQuantity() {
            return quantity;
        }

        public void setQuantity(String quantity) {
            this.quantity = quantity;
        }

        public String getBefore_money() {
            return before_money;
        }

        public void setBefore_money(String before_money) {
            this.before_money = before_money;
        }

        public String getPercentage() {
            return percentage;
        }

        public void setPercentage(String percentage) {
            this.percentage = percentage;
        }
    }

    public static class TimeListBean {
        /**
         * morn : 0
         * noon : 0
         * night : 0
         */

        private int morn;
        private int noon;
        private int night;

        public int getMorn() {
            return morn;
        }

        public void setMorn(int morn) {
            this.morn = morn;
        }

        public int getNoon() {
            return noon;
        }

        public void setNoon(int noon) {
            this.noon = noon;
        }

        public int getNight() {
            return night;
        }

        public void setNight(int night) {
            this.night = night;
        }
    }

    public static class DeliveryMethodBean {
        /**
         * name : 堂食
         * quantity : 1
         */

        private String name;
        private int quantity;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
    }

    public static class GoodsDetailsQuantityBean {
        /**
         * goods_id : 1123
         * goods_name : 商品名称
         * goods_quantity : 2
         */

        private String goods_id;
        private String goods_name;
        private int goods_quantity;



        public String getGoods_name() {
            return goods_name;
        }

        public void setGoods_name(String goods_name) {
            this.goods_name = goods_name;
        }

        public String getGoods_id() {
            return goods_id;
        }

        public void setGoods_id(String goods_id) {
            this.goods_id = goods_id;
        }

        public int getGoods_quantity() {
            return goods_quantity;
        }

        public void setGoods_quantity(int goods_quantity) {
            this.goods_quantity = goods_quantity;
        }
    }
}
