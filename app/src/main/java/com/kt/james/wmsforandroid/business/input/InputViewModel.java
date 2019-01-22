package com.kt.james.wmsforandroid.business.input;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.utils.MathUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

public class InputViewModel extends AndroidViewModel {

    public final ObservableField<String> barcode = new ObservableField<>();
    public final ObservableField<String> name = new ObservableField<>();
    public final ObservableField<String> spec = new ObservableField<>();
    public final ObservableField<String> brand = new ObservableField<>();
    public final ObservableField<String> loc = new ObservableField<>();
    public final ObservableField<String> amount = new ObservableField<>();

    public InputViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> submit() {
        final MutableLiveData<Boolean> data = new MutableLiveData<>();
        if (!verifyData()) {
            data.setValue(false);
            return data;
        }
        //接口
        return data;
    }

    private boolean verifyData() {
        if (TextUtils.isEmpty(barcode.get())) {
            ToastUtil.showToast("请扫描商品条码！");
            return false;
        }
        if (TextUtils.isEmpty(loc.get())) {
            ToastUtil.showToast("请扫描库位！");
            return false;
        }
        return !(MathUtil.tryFormatDouble(amount.get(), 0.0) == 0.0);
    }

}
