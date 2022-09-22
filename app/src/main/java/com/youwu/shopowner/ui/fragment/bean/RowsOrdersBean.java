package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;
import java.util.ArrayList;


public class RowsOrdersBean implements Serializable {

    /**
     * rows : [{"goods_id":1123,"goods_sku":"123546","goods_name":"测试商品","goods_price":1,"goods_cost_price":0.52,"goods_img":"123","stock":87,"group_id":1,"group_name":"早餐","group_sort":100,"group_img":"11"}]
     * total : 1
     */

    private int total;
    private ArrayList<SaleBillBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<SaleBillBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<SaleBillBean> rows) {
        this.rows = rows;
    }
}
