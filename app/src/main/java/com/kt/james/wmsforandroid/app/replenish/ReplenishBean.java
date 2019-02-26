package com.kt.james.wmsforandroid.app.replenish;

import android.databinding.ObservableField;

import java.util.List;

public class ReplenishBean {

    private List<ReplenishItem> items;

    public List<ReplenishItem> getItems() {
        return items;
    }

    public void setItems(List<ReplenishItem> items) {
        this.items = items;
    }

    public static class ReplenishItem {

        public ObservableField<String> replenishNum = new ObservableField<>();

        private int itemId;

        private String itemName;

        private String recentlySale;

        private String recommend;

        private String stock;

        public ObservableField<String> getReplenishNum() {
            return replenishNum;
        }

        public void setReplenishNum(ObservableField<String> replenishNum) {
            this.replenishNum = replenishNum;
        }

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getRecentlySale() {
            return recentlySale;
        }

        public void setRecentlySale(String recentlySale) {
            this.recentlySale = recentlySale;
        }

        public String getRecommend() {
            return recommend;
        }

        public void setRecommend(String recommend) {
            this.recommend = recommend;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }
    }

}
