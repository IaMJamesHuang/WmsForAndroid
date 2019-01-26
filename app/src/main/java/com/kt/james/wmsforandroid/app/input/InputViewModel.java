package com.kt.james.wmsforandroid.app.input;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.app.input.dto.AddItemDto;
import com.kt.james.wmsforandroid.app.utils.WmsSpManager;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.MathUtil;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
        HttpClient.Builder.getWmsService().addItem(barcode.get(), WmsSpManager.getCompanyId(),
                MathUtil.tryFormatFloat(amount.get(), -1f), loc.get())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddItemDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(AddItemDto addItemDto) {
                        if (addItemDto != null && addItemDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            data.setValue(true);
                        } else {
                            data.setValue(false);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        data.setValue(false);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
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
        return !(MathUtil.tryFormatFloat(amount.get(), 0f) == 0f);
    }

}
