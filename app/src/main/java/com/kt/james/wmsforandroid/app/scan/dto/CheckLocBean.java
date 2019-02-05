package com.kt.james.wmsforandroid.app.scan.dto;

import java.io.Serializable;

public class CheckLocBean implements Serializable {

    private int id;

    private int company_id;

    private String name;

    private int loc_x;

    private int loc_y;

    private float available_num;

    private float total_num;

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLoc_x() {
        return loc_x;
    }

    public void setLoc_x(int loc_x) {
        this.loc_x = loc_x;
    }

    public int getLoc_y() {
        return loc_y;
    }

    public void setLoc_y(int loc_y) {
        this.loc_y = loc_y;
    }

    public float getAvailable_num() {
        return available_num;
    }

    public void setAvailable_num(float available_num) {
        this.available_num = available_num;
    }

    public float getTotal_num() {
        return total_num;
    }

    public void setTotal_num(float total_num) {
        this.total_num = total_num;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
