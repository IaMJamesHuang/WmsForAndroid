package com.kt.james.wmsforandroid.app.scan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.app.scan.dto.CheckItemBarcodeDto;
import com.kt.james.wmsforandroid.app.scan.dto.CheckLocBean;
import com.kt.james.wmsforandroid.app.scan.dto.CheckLocDto;
import com.kt.james.wmsforandroid.app.scan.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.app.utils.WmsSpManager;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class CommonScanViewModel extends AndroidViewModel {

    public final ObservableField<Integer> which = new ObservableField<>();

    public final ObservableField<String> itemName = new ObservableField<>();

    public final ObservableField<String> itemSpec = new ObservableField<>();

    public final ObservableField<String> itemBrand = new ObservableField<>();

    public final ObservableField<String> locCode = new ObservableField<>();

    public final ObservableField<String> stringCode = new ObservableField<>();

    private boolean isReady = false;

    private ItemBarcodeBean itemResult;

    private CheckLocBean locResult;

    public CommonScanViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<ItemBarcodeBean> requestItemCode(String code) {
        MutableLiveData<ItemBarcodeBean> result = new MutableLiveData<>();
        if (TextUtils.isEmpty(code)) {
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().checkItemBarcode(code, WmsSpManager.getCompanyId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CheckItemBarcodeDto, ItemBarcodeBean>() {
                    @Override
                    public ItemBarcodeBean apply(CheckItemBarcodeDto checkItemBarcodeDto) throws Exception {
                        if (checkItemBarcodeDto == null || checkItemBarcodeDto.getResponseCode() != HttpClient.CODE_SUCCESS) {
                            if (checkItemBarcodeDto != null) {
                                ToastUtil.showToast(checkItemBarcodeDto.getResponseMsg());
                            }
                            return null;
                        }
                        return checkItemBarcodeDto.getResult();
                    }
                }).subscribe(new Observer<ItemBarcodeBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ItemBarcodeBean scanItemBean) {
                isReady = scanItemBean != null;
                itemResult = scanItemBean;
                result.setValue(scanItemBean);
            }

            @Override
            public void onError(Throwable e) {
                result.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        });
        return result;
    }

    public MutableLiveData<CheckLocBean> requestLocCode(String code) {
        MutableLiveData<CheckLocBean> result = new MutableLiveData<>();
        if (TextUtils.isEmpty(code)) {
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().checkLoc(code, WmsSpManager.getCompanyId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CheckLocDto, CheckLocBean>() {
                    @Override
                    public CheckLocBean apply(CheckLocDto checkLocDto) throws Exception {
                        if (checkLocDto.getResponseCode() != HttpClient.CODE_SUCCESS) {
                            ToastUtil.showToast(checkLocDto.getResponseMsg());
                            return null;
                        }
                        return checkLocDto.getLocation();
                    }
                }).subscribe(new Observer<CheckLocBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(CheckLocBean s) {
                locResult = s;
                isReady = locResult != null;
                result.setValue(s);
            }

            @Override
            public void onError(Throwable e) {
                result.setValue(null);
            }

            @Override
            public void onComplete() {

            }
        });
        return result;
    }

    public boolean isReady() {
        if (CommonScanActivity.STRING == which.get()) {
            return true;
        }
        return isReady;
    }

    public ItemBarcodeBean getItemResult() {
        return itemResult;
    }

    public CheckLocBean getLocResult() {
        return locResult;
    }

    public String getStringResult() {
        return stringCode.get();
    }
}
