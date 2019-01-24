package com.kt.james.wmsforandroid.business.scan;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.kt.james.wmsforandroid.business.input.dto.CheckItemBarcodeDto;
import com.kt.james.wmsforandroid.business.input.dto.CheckLocDto;
import com.kt.james.wmsforandroid.business.input.dto.ItemBarcodeBean;
import com.kt.james.wmsforandroid.business.utils.WmsSpManager;
import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

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
        if (TextUtils.isEmpty(code)) {
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().checkItemBarcode(code, WmsSpManager.getCompanyId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CheckItemBarcodeDto, ScanItemBean>() {
                    @Override
                    public ScanItemBean apply(CheckItemBarcodeDto checkItemBarcodeDto) throws Exception {
                        if (checkItemBarcodeDto.getResponseCode() != HttpClient.CODE_SUCCESS) {
                            ToastUtil.showToast(checkItemBarcodeDto.getResponseMsg());
                            return null;
                        }
                        ScanItemBean bean = new ScanItemBean();
                        ItemBarcodeBean item = checkItemBarcodeDto.getResult();
                        bean.setBarcode(item.getBarcode());
                        bean.setFactory(item.getFactory());
                        bean.setSpec(item.getSpec());
                        bean.setImg_path(item.getImg_path());
                        bean.setName(item.getName());
                        return bean;
                    }
                }).subscribe(new Observer<ScanItemBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ScanItemBean scanItemBean) {
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

    public MutableLiveData<String> requestLocCode(String code) {
        MutableLiveData<String> result = new MutableLiveData<>();
        if (TextUtils.isEmpty(code)) {
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().checkLoc(code, WmsSpManager.getCompanyId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<CheckLocDto, String>() {
                    @Override
                    public String apply(CheckLocDto checkLocDto) throws Exception {
                        if (checkLocDto.getResponseCode() != HttpClient.CODE_SUCCESS) {
                            ToastUtil.showToast(checkLocDto.getResponseMsg());
                            return null;
                        }
                        return checkLocDto.getLocation().getName();
                    }
                }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String s) {
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
        return isReady;
    }

    public ScanItemBean getItemResult() {
        return itemResult;
    }

    public String getLocResult() {
        return locResult;
    }
}
