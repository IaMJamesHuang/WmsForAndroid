package com.kt.james.wmsforandroid.app.layout;

import java.util.ArrayList;
import java.util.List;

public class LayoutAdapter<T extends LayoutSurfaceView> implements LayoutSurfaceView.OnItemClickListener{

    private T mView;

    private List<LayoutPoint> mData;

    public LayoutAdapter(T view) {
        mData = new ArrayList<>();
        mView = view;
        mView.setItemClickListener(this);
    }

    public void addData(LayoutPoint v) {
        if (mData.contains(v)) {
            int index = mData.indexOf(v);
            LayoutPoint point = mData.get(index);
            point.setState(v.getState());
        } else {
            mData.add(v);
        }
        mView.setState(v.getX(), v.getY(), v.getState());
    }

    public void addAll(List<LayoutPoint> list) {
        mData.clear();
        mData.addAll(list);
        for (int i=0; i<mData.size(); i++) {
            LayoutPoint v = mData.get(i);
            mView.setState(v.getX(), v.getY(), v.getState());
        }
    }

    @Override
    public void onClick(int x, int y, int state) {
        if (state == LayoutSurfaceView.STATE_EMPTY) {
            addData(new LayoutPoint(x, y, LayoutSurfaceView.STATE_EDIT));
        } else if (state == LayoutSurfaceView.STATE_FILL) {
            //删除弹窗
        } else {
            //正在编辑
        }
    }
}
