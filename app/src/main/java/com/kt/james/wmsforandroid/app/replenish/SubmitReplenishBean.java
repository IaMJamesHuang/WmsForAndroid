package com.kt.james.wmsforandroid.app.replenish;

import java.util.List;

public class SubmitReplenishBean {

    private List<SubmitItem> submitInfos;

    public List<SubmitItem> getSubmitInfos() {
        return submitInfos;
    }

    public void setSubmitInfos(List<SubmitItem> submitInfos) {
        this.submitInfos = submitInfos;
    }

    public static class SubmitItem {
        private int itemId;

        private float replenishCount;

        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public float getReplenishCount() {
            return replenishCount;
        }

        public void setReplenishCount(float replenishCount) {
            this.replenishCount = replenishCount;
        }
    }


}
