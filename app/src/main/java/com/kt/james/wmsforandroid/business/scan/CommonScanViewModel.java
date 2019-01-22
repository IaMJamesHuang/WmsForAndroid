package com.kt.james.wmsforandroid.business.scan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

public class CommonScanViewModel extends AndroidViewModel {

    public final ObservableField<Boolean> isItem = new ObservableField<>();

    public CommonScanViewModel(@NonNull Application application) {
        super(application);
    }
}
