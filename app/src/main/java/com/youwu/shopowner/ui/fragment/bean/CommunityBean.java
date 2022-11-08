package com.youwu.shopowner.ui.fragment.bean;


import java.io.Serializable;

public class CommunityBean  implements Serializable {

    private String sou_suo_text;//搜索的内容

    private int goods_number;//商品数量
    private int position;//第几个
    private int com_number_state;//当加入到购物车时显示

    public String attribute;

    private String goods_id_sku;
    private String goods_id;
    private String goods_sku;
    private String goods_name;//商品名称
    private String goods_price;//商品价格
    private String market_price;//商品市场价
    private int status;//状态 1上架 2下架
    private String goods_cost_price;//成本价格
    private String goods_img;//商品图片
    private int stock;//库存
    private int change_stock;//修改后库存
    private int group_id;//所属群组id
    private String group_name;//所属群组名称
    private int group_sort;//
    private String group_img;//所属群组图片
    private String returns_number;//退货数量
    private String Remarks;//备注
    private String details;//
    private int package_id;//套餐id
    private int type;// 1商品 2 套餐
    private int category_id;// 分类id
    private String category_name;// 分类名称

    public String getMarket_price() {
        return market_price;
    }

    public void setMarket_price(String market_price) {
        this.market_price = market_price;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSou_suo_text() {
        return sou_suo_text;
    }

    public void setSou_suo_text(String sou_suo_text) {
        this.sou_suo_text = sou_suo_text;
    }

    public int getGoods_number() {
        return goods_number;
    }

    public void setGoods_number(int goods_number) {
        this.goods_number = goods_number;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCom_number_state() {
        return com_number_state;
    }

    public void setCom_number_state(int com_number_state) {
        this.com_number_state = com_number_state;
    }

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getGoods_id_sku() {
        return goods_id_sku;
    }

    public void setGoods_id_sku(String goods_id_sku) {
        this.goods_id_sku = goods_id_sku;
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

    public String getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(String goods_price) {
        this.goods_price = goods_price;
    }

    public String getGoods_cost_price() {
        return goods_cost_price;
    }

    public void setGoods_cost_price(String goods_cost_price) {
        this.goods_cost_price = goods_cost_price;
    }

    public String getGoods_img() {
        return goods_img;
    }

    public void setGoods_img(String goods_img) {
        this.goods_img = goods_img;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getChange_stock() {
        return change_stock;
    }

    public void setChange_stock(int change_stock) {
        this.change_stock = change_stock;
    }

    public int getGroup_id() {
        return group_id;
    }

    public void setGroup_id(int group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public int getGroup_sort() {
        return group_sort;
    }

    public void setGroup_sort(int group_sort) {
        this.group_sort = group_sort;
    }

    public String getGroup_img() {
        return group_img;
    }

    public void setGroup_img(String group_img) {
        this.group_img = group_img;
    }

    public String getReturns_number() {
        return returns_number;
    }

    public void setReturns_number(String returns_number) {
        this.returns_number = returns_number;
    }

    public String getRemarks() {
        return Remarks;
    }

    public void setRemarks(String remarks) {
        Remarks = remarks;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
