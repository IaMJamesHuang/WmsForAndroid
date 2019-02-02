package com.kt.james.wmsforandroid.app.layout;

import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.kt.james.wmsforandroid.R;
import com.kt.james.wmsforandroid.databinding.LayoutDialogBinding;
import com.kt.james.wmsforandroid.utils.MathUtil;
import com.kt.james.wmsforandroid.utils.ResourceUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;
import com.kt.james.wmsforandroid.utils.rx.RxBus;

public class LayoutCustomDialog extends Dialog implements View.OnClickListener {

    private LayoutDialogBinding mBinding;

    private LayoutPoint mLayoutPoint;

    private Context mContext;

    private int state;

    public LayoutCustomDialog(Context context, LayoutPoint layoutPoint) {
        super(context);
        this.mContext = context;
        this.mLayoutPoint = layoutPoint;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.layout_dialog, null, false);
        setContentView(mBinding.getRoot());
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        state = mLayoutPoint.getState();
        if (state == LayoutSurfaceView.STATE_FILL) {
            mBinding.layoutTitle.setText("库位信息");
            mBinding.etLocName.setText(mLayoutPoint.getName());
            mBinding.etLocName.setSelection(mLayoutPoint.getName().length());
            mBinding.etLocPos.setText(mLayoutPoint.getX() + ", " + mLayoutPoint.getY());
            mBinding.etLocPos.setEnabled(false);
            mBinding.etLayoutAva.setText(String.valueOf(mLayoutPoint.getAvailable_num()));
            mBinding.etLayoutAva.setEnabled(false);
            mBinding.etLayoutTotalCount.setText(String.valueOf(mLayoutPoint.getTotal_num()));
            mBinding.etLayoutTotalCount.setEnabled(false);
        } else {
            mBinding.layoutTitle.setText("新增库位");
            mBinding.etLocPos.setText(mLayoutPoint.getX() + ", " + mLayoutPoint.getY());
            mBinding.etLocPos.setEnabled(false);
            mBinding.etLayoutAva.setEnabled(false);
            mBinding.etLayoutTotalCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    mBinding.etLayoutAva.setText(s);
                }
            });
        }
        mBinding.btCancel.setOnClickListener(this);
        mBinding.btSave.setOnClickListener(this);
    }

    public static void showLayout(Context context, LayoutPoint layoutPoint) {
        new LayoutCustomDialog(context, layoutPoint).show();
    }

    @Override
    public void show() {
        super.show();
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        int width = (int) (dm.widthPixels * 0.75);
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
        layoutParams.width = width;
        getWindow().setAttributes(layoutParams);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.bt_save) {
            doSave();
        } else if (v.getId() == R.id.bt_cancel) {
            doCancel();
        }
    }

    private void doSave() {
        if (!checkData()) {
            return;
        }
        mLayoutPoint.setName(mBinding.etLocName.getText().toString());
        mLayoutPoint.setState(LayoutSurfaceView.STATE_FILL);
        mLayoutPoint.setAvailable_num(Float.valueOf(mBinding.etLayoutAva.getText().toString()));
        mLayoutPoint.setTotal_num(Float.valueOf(mBinding.etLayoutTotalCount.getText().toString()));
        LayoutEvent event = new LayoutEvent();
        event.setWhat(LayoutEvent.EVENT_SAVE);
        event.setLayoutPoint(mLayoutPoint);
        RxBus.get().post(event);
        dismiss();
    }

    private void doCancel() {
        LayoutEvent event = new LayoutEvent();
        event.setWhat(LayoutEvent.EVENT_CANCEL);
        event.setLayoutPoint(mLayoutPoint);
        RxBus.get().post(event);
        dismiss();
    }

    private boolean checkData() {
        if (TextUtils.isEmpty(mBinding.etLocName.getText())) {
            ToastUtil.showToast(String.format(ResourceUtil.getString(R.string.layout_dialog_toast), "库位名称"));
            return false;
        }
        if (TextUtils.isEmpty(mBinding.etLayoutAva.getText()) || !MathUtil.isFloatRight(mBinding.etLayoutAva.getText().toString())) {
            ToastUtil.showToast(String.format(ResourceUtil.getString(R.string.layout_dialog_toast), "可用库存"));
            return false;
        }
        if (TextUtils.isEmpty(mBinding.etLayoutTotalCount.getText()) || !MathUtil.isFloatRight(mBinding.etLayoutTotalCount.getText().toString())) {
            ToastUtil.showToast(String.format(ResourceUtil.getString(R.string.layout_dialog_toast), "库存总量"));
            return false;
        }
        return true;
    }

}
