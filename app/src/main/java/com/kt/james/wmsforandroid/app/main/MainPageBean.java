package com.kt.james.wmsforandroid.app.main;

import android.databinding.BaseObservable;

public class MainPageBean extends BaseObservable {

    private int iconImgId;

    private String title;

    public MainPageBean(int iconImgId, String title) {
        this.iconImgId = iconImgId;
        this.title = title;
    }

    public int getIconImgId() {
        return iconImgId;
    }

    public void setIconImgId(int iconImgId) {
        this.iconImgId = iconImgId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
