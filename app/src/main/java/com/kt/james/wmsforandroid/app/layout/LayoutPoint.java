package com.kt.james.wmsforandroid.app.layout;

import com.google.gson.annotations.SerializedName;

public class LayoutPoint {

    @SerializedName("loc_x")
    private int x;

    @SerializedName("loc_y")
    private int y;
    private int state;

    private int id;
    private int company_id;
    private String name;
    private float available_num;
    private float total_num;

    public LayoutPoint() {
    }

    public LayoutPoint(int x, int y, int state) {
        this.x = x;
        this.y = y;
        this.state = state;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof  LayoutPoint)) {
            return false;
        }
        LayoutPoint point = (LayoutPoint) obj;
        if (x == point.getX() && y == point.getY()) {
            return true;
        }
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAvailable_num(float available_num) {
        this.available_num = available_num;
    }

    public void setTotal_num(float total_num) {
        this.total_num = total_num;
    }

    public int getId() {
        return id;
    }

    public int getCompany_id() {
        return company_id;
    }

    public String getName() {
        return name;
    }

    public float getAvailable_num() {
        return available_num;
    }

    public float getTotal_num() {
        return total_num;
    }
}
