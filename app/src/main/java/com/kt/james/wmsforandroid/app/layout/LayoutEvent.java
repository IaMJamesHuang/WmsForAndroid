package com.kt.james.wmsforandroid.app.layout;

public class LayoutEvent {

    public static final int EVENT_SAVE = 0x00;

    public static final int EVENT_CANCEL = 0x01;

    private int what;

    private LayoutPoint layoutPoint;

    public int getWhat() {
        return what;
    }

    public void setWhat(int what) {
        this.what = what;
    }

    public LayoutPoint getLayoutPoint() {
        return layoutPoint;
    }

    public void setLayoutPoint(LayoutPoint layoutPoint) {
        this.layoutPoint = layoutPoint;
    }
}
