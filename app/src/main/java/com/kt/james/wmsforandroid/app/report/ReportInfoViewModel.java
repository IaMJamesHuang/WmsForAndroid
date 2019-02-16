package com.kt.james.wmsforandroid.app.report;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.kt.james.wmsforandroid.net.HttpClient;
import com.kt.james.wmsforandroid.utils.ToastUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ReportInfoViewModel extends AndroidViewModel {

    public ReportInfoViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<DaySaleItemBean>> getItemDaySale(int itemId) {
        MutableLiveData<List<DaySaleItemBean>> result = new MutableLiveData<>();
        if (itemId < 0) {
            result.setValue(null);
            return result;
        }
        HttpClient.Builder.getWmsService().getItemDaySale(itemId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DaySaleDto>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DaySaleDto daySaleDto) {
                        ToastUtil.showToast(daySaleDto.getResponseMsg());
                        if (daySaleDto.getResponseCode() == HttpClient.CODE_SUCCESS) {
                            result.setValue(daySaleDto.getResult().getDaySaleInfos());
                        }
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

}
