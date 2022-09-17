package com.youwu.shopowner.ui.fragment.bean;

import java.io.Serializable;

public class GroupBean implements Serializable {


    /**
     * id : 1
     * name : 新鲜蔬菜
     * sort : 100
     * image : 11
     * status : 1
     * desc : 1
     * company_id : 0
     * created_at : 2022-05-25T19:29:00.000000Z
     * updated_at : null
     */

    private String id;
    private String name;
    private int sort;
    private String image;
    private int status;
    private String desc;
    private int company_id;
    private String created_at;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

}
