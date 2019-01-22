package com.kt.james.wmsforandroid.business.scan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

public class CommonScanViewModel extends AndroidViewModel {

    public final ObservableField<Boolean> isItem = new ObservableField<>();

    public final ObservableField<String> itemName = new ObservableField<>();

    public final ObservableField<String> itemSpec = new ObservableField<>();

    public final ObservableField<String> itemBrand = new ObservableField<>();

    public final ObservableField<String> locCode = new ObservableField<>();

    private boolean isReady = false;

    private ScanItemBean itemResult;

    private String locResult;

    public CommonScanViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ScanItemBean> requestItemCode(String code) {
        MutableLiveData<ScanItemBean> result = new MutableLiveData<>();
        //接口
        return result;
    }

    public MutableLiveData<String> requestLocCode(String code) {
        MutableLiveData<String> result = new MutableLiveData<>();
        //接口
        return result;
    }

    public boolean isReady() {
        return isReady;
    }

    public ScanItemBean getItemResult() {
        return itemResult;
    }

    public String getLocResult() {
        return locResult;
    }
}
