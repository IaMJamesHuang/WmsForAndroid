package com.kt.james.wmsforandroid.app.report;

import java.util.List;

public class DaySaleBean {

    private int item_id;

    private List<DaySaleItemBean> daySaleInfos;

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

    public List<DaySaleItemBean> getDaySaleInfos() {
        return daySaleInfos;
    }

    public void setDaySaleInfos(List<DaySaleItemBean> daySaleInfos) {
        this.daySaleInfos = daySaleInfos;
    }

}
