package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2022/9/20
 */
public class RefundDetailsBean implements Serializable {


    /**
     * refund_sn : 100100030199991663656278
     * order_sn : 100101010199981663642711
     * total_price : 10
     * reviewer_at : 2022-09-20 14:44:38
     * details : [{"goods_name":"火山石烤肠-规格：1根/份","goods_thumb":"https://images2.youwuu.com/avatar/452072763730040454.jpg","goods_number":1,"goods_price":3},{"goods_name":"蔬菜-规格：蔬菜","goods_thumb":"","goods_number":1,"goods_price":12}]
     * member_info : {"user_name":"13468214148","nick_name":"餘生"}
     * refund_type_name : 全部退款
     * refund_status_name : 已退款
     * refund_reason : 不想要了
     * image : []
     * mark :
     * actual_refund_money : 10
     * created_at : 2022-09-20 14:44:38
     */

    private String refund_sn;
    private String order_sn;
    private int total_price;
    private String reviewer_at;
    private MemberInfoBean member_info;
    private String refund_type_name;
    private String refund_status_name;
    private String refund_reason;
    private String mark;
    private String actual_refund_money;
    private String created_at;
    private List<DetailsBean> details;
    private List<String> image;

    public List<String> getImage() {
        return image;
    }

    public void setImage(List<String> image) {
        this.image = image;
    }

    public String getRefund_sn() {
        return refund_sn;
    }

    public void setRefund_sn(String refund_sn) {
        this.refund_sn = refund_sn;
    }

    public String getOrder_sn() {
        return order_sn;
    }

    public void setOrder_sn(String order_sn) {
        this.order_sn = order_sn;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public String getReviewer_at() {
        return reviewer_at;
    }

    public void setReviewer_at(String reviewer_at) {
        this.reviewer_at = reviewer_at;
    }

    public MemberInfoBean getMember_info() {
        return member_info;
    }

    public void setMember_info(MemberInfoBean member_info) {
        this.member_info = member_info;
    }

    public String getRefund_type_name() {
        return refund_type_name;
    }

    public void setRefund_type_name(String refund_type_name) {
        this.refund_type_name = refund_type_name;
    }

    public String getRefund_status_name() {
        return refund_status_name;
    }

    public void setRefund_status_name(String refund_status_name) {
        this.refund_status_name = refund_status_name;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public String getMark() {
        return mark;
    }

    public void setMark(String mark) {
        this.mark = mark;
    }

    public String getActual_refund_money() {
        return actual_refund_money;
    }

    public void setActual_refund_money(String actual_refund_money) {
        this.actual_refund_money = actual_refund_money;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public List<DetailsBean> getDetails() {
        return details;
    }

    public void setDetails(List<DetailsBean> details) {
        this.details = details;
    }


    public static class MemberInfoBean {
        /**
         * user_name : 13468214148
         * nick_name : 餘生
         */

        private String user_name;
        private String nick_name;

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
        }
    }

    public static class DetailsBean {
        /**
         * goods_name : 火山石烤肠-规格：1根/份
         * goods_thumb : https://images2.youwuu.com/avatar/452072763730040454.jpg
         * goods_number : 1
         * goods_price : 3
         */

        private String goods_name;
        private String goods_thumb;
        private int goods_number;
        private String goods_price;

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
    }
}
