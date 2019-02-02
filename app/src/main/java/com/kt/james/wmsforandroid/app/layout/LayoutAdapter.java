package com.kt.james.wmsforandroid.app.layout;

import com.kt.james.wmsforandroid.app.utils.WmsSpManager;

import java.util.ArrayList;
import java.util.List;

import static com.kt.james.wmsforandroid.app.layout.LayoutSurfaceView.STATE_FILL;

public class LayoutAdapter<T extends LayoutSurfaceView> implements LayoutSurfaceView.OnItemClickListener{

    private T mView;

    private List<LayoutPoint> mData;

    private OnLayoutChangeListener mListener;

    public LayoutAdapter(T view) {
        mData = new ArrayList<>();
        mView = view;
        mView.setItemClickListener(this);
    }

    public void setListener(OnLayoutChangeListener mListener) {
        this.mListener = mListener;
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
            v.setState(STATE_FILL);
            mView.setState(v.getX(), v.getY(), v.getState());
        }
    }

    public List<LayoutPoint> getData() {
        return mData;
    }

    public LayoutPoint findLayoutPointByPos(int x, int y) {
        for (int i=0; i<mData.size(); i++) {
            LayoutPoint point = mData.get(i);
            if (x == point.getX() && y == point.getY()) {
                return point;
            }
        }
        return null;
    }

    @Override
    public void onClick(int x, int y, int state) {
        if (state == LayoutSurfaceView.STATE_EMPTY) {
            LayoutPoint layoutPoint = new LayoutPoint(x, y, LayoutSurfaceView.STATE_EDIT);
            layoutPoint.setCompany_id(Integer.parseInt(WmsSpManager.getCompanyId()));
            addData(layoutPoint);
            if (mListener != null) {
                mListener.onEmpty(layoutPoint);
            }
        } else if (state == STATE_FILL) {
            //已经存在的点
            LayoutPoint layoutPoint = findLayoutPointByPos(x, y);
            //异常
            if (layoutPoint.getState() != STATE_FILL) {
                layoutPoint.setState(STATE_FILL);
            }
            if (mListener != null) {
                mListener.onFill(layoutPoint);
            }
        } else {
            //正在编辑,异常情况
            if (mListener != null) {
                mListener.onEditing(null);
            }
        }
    }

    public interface OnLayoutChangeListener {

        void onEmpty(LayoutPoint layoutPoint);

        void onEditing(LayoutPoint layoutPoint);

        void onFill(LayoutPoint layoutPoint);
    }

}
