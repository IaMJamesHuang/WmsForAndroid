package com.kt.james.wmsforandroid.app.layout;

public class LayoutPoint {

    private int x;
    private int y;
    private int state;

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
}
