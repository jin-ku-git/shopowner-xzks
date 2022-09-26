package com.youwu.shopowner.ui.goods_operate.bean;

import com.youwu.shopowner.ui.fragment.bean.CommunityBean;

import java.io.Serializable;
import java.util.ArrayList;


public class RowsBean implements Serializable {

    /**
     * rows : [{"goods_id":1123,"goods_sku":"123546","goods_name":"测试商品","goods_price":1,"goods_cost_price":0.52,"goods_img":"123","stock":87,"group_id":1,"group_name":"早餐","group_sort":100,"group_img":"11"}]
     * total : 1
     */

    private int total;
    private ArrayList<CommunityBean> rows;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public ArrayList<CommunityBean> getRows() {
        return rows;
    }

    public void setRows(ArrayList<CommunityBean> rows) {
        this.rows = rows;
    }
}
