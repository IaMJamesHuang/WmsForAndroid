package com.kt.james.wmsforandroid.app.upshelf;

import java.io.Serializable;

public class UpShelfBean implements Serializable {

    private int shelfId;

    private int shelfListId;

    private int state; // 0：未开始 1:已完成

    private int shelfListState;

    private int company_id;

    private float num;

    private int itemId;

    private String itemBarcode;

    private String itemName;

    private String itemSpec;

    private String img_path;

    private String itemFactory;

    private int locId;

    private String locName;

    private int loc_x;

    private int loc_y;

    public int getShelfId() {
        return shelfId;
    }

    public void setShelfId(int shelfId) {
        this.shelfId = shelfId;
    }

    public int getShelfListId() {
        return shelfListId;
    }

    public void setShelfListId(int shelfListId) {
        this.shelfListId = shelfListId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getShelfListState() {
        return shelfListState;
    }

    public void setShelfListState(int shelfListState) {
        this.shelfListState = shelfListState;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public float getNum() {
        return num;
    }

    public void setNum(float num) {
        this.num = num;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemBarcode() {
        return itemBarcode;
    }

    public void setItemBarcode(String itemBarcode) {
        this.itemBarcode = itemBarcode;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemSpec() {
        return itemSpec;
    }

    public void setItemSpec(String itemSpec) {
        this.itemSpec = itemSpec;
    }

    public String getImg_path() {
        return img_path;
    }

    public void setImg_path(String img_path) {
        this.img_path = img_path;
    }

    public String getItemFactory() {
        return itemFactory;
    }

    public void setItemFactory(String itemFactory) {
        this.itemFactory = itemFactory;
    }

    public int getLocId() {
        return locId;
    }

    public void setLocId(int locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
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
}
